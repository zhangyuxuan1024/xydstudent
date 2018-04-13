package com.xyd.student.xydexamanalysis.Fragment;

/**
 * Created by Lichg.
 */

import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import cn.jpush.android.util.ad;

import com.umeng.analytics.MobclickAgent;
import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.adapter.MyNoticeAdapter;
import com.xyd.student.xydexamanalysis.constant.Constants;
import com.xyd.student.xydexamanalysis.entity.Login;
import com.xyd.student.xydexamanalysis.entity.Notice_Con;
import com.xyd.student.xydexamanalysis.entity.PayInfo;
import com.xyd.student.xydexamanalysis.entity.SingleNotice;
import com.xyd.student.xydexamanalysis.pullrefreshview.PullToRefreshBase;
import com.xyd.student.xydexamanalysis.pullrefreshview.PullToRefreshListView;
import com.xyd.student.xydexamanalysis.ui.ExplainQuestionActivity;
import com.xyd.student.xydexamanalysis.ui.GradeReportActivity;
import com.xyd.student.xydexamanalysis.ui.MainActivity;
import com.xyd.student.xydexamanalysis.ui.PayDialog2Activity;
import com.xyd.student.xydexamanalysis.ui.PayDialogActivity;
import com.xyd.student.xydexamanalysis.ui.SingReportActivity;
import com.xyd.student.xydexamanalysis.utils.GsonUtil;
import com.xyd.student.xydexamanalysis.utils.JsonUtils;
import com.xyd.student.xydexamanalysis.utils.LoginUtils;
import com.xyd.student.xydexamanalysis.utils.MyHttpUtil;
import com.xyd.student.xydexamanalysis.utils.NetWorkUtils;
import com.xyd.student.xydexamanalysis.utils.ToastUtils;
import com.xyd.student.xydexamanalysis.utils.UIUtils;
import com.xyd.student.xydexamanalysis.view.LoadingHelper;
import com.xyd.student.xydexamanalysis.view.LoadingListener;
import com.xyd.student.xydexamanalysis.view.MyListView;

public class NoticeFragment extends Fragment implements LoadingListener {
	public static final int NOTICE_START_ACTIVITY_CODE = 100;
	public static final int NOTICE_CODE_TOL_ACTIVITY_RESULT = 101;
	public static final int NOTICE_CODE_SIN_ACTIVITY_RESULT = 102;
	public static final int NOTICE_CODE_SHITIJIANG_ACTIVITY_RESULT = 103;
	public static final int NOTICE_CODE_YOUXIU_ACTIVITY_RESULT = 104;
	public static final int NOTICE_CODE_SHIFENTI_ACTIVITY_RESULT = 105;

	private LoadingHelper loadingHelper;
	private PullToRefreshListView mPullRefreshListView;
	private boolean isFirst = true;
	private boolean isEnd = false;
	private boolean loading = false;
	private int currentPage = 1;
	private MyNoticeAdapter adapter;
	private Notice_Con notice_con;
	private List<SingleNotice> notice_list;
	private MainActivity mainactivity;
	private Handler mHandler;
	private JSONObject param;
	private Context context;
	private Login login;
	private boolean firstin;
	// private SharedPreferences msharedPreferences;
	private String Noticeserverurl;

	public NoticeFragment() {
		// TODO Auto-generated constructor stub
		mHandler = new Handler();
		login = new Login();
		this.firstin = true;
	}

	public NoticeFragment(Context context) {
		mHandler = new Handler();
		this.context = context;
		login = new Login();
		this.firstin = true;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		SharedPreferences msharedPreferences = activity.getSharedPreferences(
				Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
		Noticeserverurl = msharedPreferences.getString("ReportServUrl", "");
		if (Noticeserverurl.equals("")) {
			ToastUtils.show(context, "获取服务器地址错误，请重新登录");
			activity.finish();
			return;
		} else {
			Noticeserverurl = Noticeserverurl + "sac/querymessage";
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("lxw", "NoticeFragment");
		View view = View.inflate(UIUtils.getContext(),
				R.layout.main_notice_frame, null);
		context = getActivity();
		mainactivity = (MainActivity) getActivity();
		loadingHelper = new LoadingHelper(
				view.findViewById(R.id.loading_prompt_linear),
				view.findViewById(R.id.loading_empty_prompt_linear));
		loadingHelper.ShowLoading();
		loadingHelper.SetListener(this);

		mPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.my_notice_frame);
		// 下拉加载的事件不支持
		mPullRefreshListView.setPullLoadEnabled(false);
		// 上拉加载下拉刷新支持
		mPullRefreshListView.setScrollLoadEnabled(true);
		// mPullRefreshListView.setOnScrollListener(new OnScrollListener() {
		// @Override
		// public void onScrollStateChanged(AbsListView view, int scrollState) {
		// if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
		//
		// } else {
		// }
		// }
		//
		// @Override
		// public void onScroll(AbsListView view, int firstVisibleItem,
		// int visibleItemCount, int totalItemCount) {
		// }
		// });

		mPullRefreshListView
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<MyListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<MyListView> refreshView) {
						// 下拉刷新,请求网络,url(一样),清除之前数据,再次加载一遍
						if (!loading) {
							mHandler.postDelayed(new Runnable() {
								@Override
								public void run() {
									if (!NetWorkUtils
											.isNetworkAvailable(context)) {
										ToastUtils.show(UIUtils.getContext(),
												"请检查网络");
									} else {
										ToastUtils.show(UIUtils.getContext(),
												"没有新数据");
									}

									// mPullRefreshListView
									// // .onPullUpRefreshComplete();
									// //
									mPullRefreshListView
											.onPullDownRefreshComplete();
									// mPullRefreshListView.onPullUpRefreshComplete();
								}
							}, 1500);
							return;
						}
						readData(true);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<MyListView> refreshView) {
						// 上拉加载,请求网络,url(moreUrl),原有数据的基础上,再添加一部分
						if (isEnd) {
							mHandler.postDelayed(new Runnable() {
								@Override
								public void run() {
									ToastUtils.show(UIUtils.getContext(),
											"没有更多数据了");
									mPullRefreshListView
											.onPullUpRefreshComplete();
									//
									// mPullRefreshListView.onPullDownRefreshComplete();
									// mPullRefreshListView.onPullUpRefreshComplete();
								}
							}, 1500);
							return;
						}
						isFirst = false;
						currentPage++;
						readData(false);
					}
				});
		mPullRefreshListView.getRefreshableView().setOnItemClickListener(
				new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String noticetype = notice_list.get(position)
								.getN_type();
						if (noticetype.equals("总成绩报告")) {
							SingleNotice singleNotice = notice_list
									.get(position);
							// 判断是否支付？
							int payStatus = singleNotice.getPayInfo()
									.getPayStatus();
							if (payStatus == 1) {
								Intent g_intent = new Intent(UIUtils
										.getContext(),
										GradeReportActivity.class);
								Bundle grade_bundle = new Bundle();

								grade_bundle.putString("name",
										singleNotice.getN_exam_name());
								grade_bundle.putString("time",
										singleNotice.getN_meDate());
								grade_bundle.putInt("meId",
										singleNotice.getN_meid());
								grade_bundle.putInt("schoolId",
										singleNotice.getN_schoolId());
								grade_bundle.putString("studentId",
										singleNotice.getN_studentId());
								grade_bundle.putInt("msgId",
										notice_list.get(position).getN_id());
								grade_bundle.putInt("isReaded", notice_list
										.get(position).getN_isread());
								grade_bundle.putInt("currentindex", position);
								g_intent.putExtras(grade_bundle);

								startActivityForResult(g_intent,
										NOTICE_START_ACTIVITY_CODE);
							} else {
								int payType = singleNotice.getPayInfo()
										.getChargeInfo().getPayType();
								if (payType == 1) {
									Intent p_intent = new Intent(UIUtils
											.getContext(),
											PayDialogActivity.class);
									Bundle p_bundle = new Bundle();
									p_bundle.putInt("meId",
											singleNotice.getN_meid());
									p_bundle.putInt("seId",
											singleNotice.getN_seId());
									p_bundle.putString("chargeName",
											singleNotice.getPayInfo()
													.getChargeInfo()
													.getChargeName());
									p_bundle.putDouble("amount", singleNotice
											.getPayInfo().getChargeInfo()
											.getAmount());
									p_bundle.putString("meName",
											singleNotice.getN_exam_name());
									p_bundle.putString("courseName",
											singleNotice.getN_subject());
									p_intent.putExtras(p_bundle);

									p_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									// getActivity().finish();
									startActivity(p_intent);
								} else if (payType == 2) {
									Intent p_intent = new Intent(UIUtils
											.getContext(),
											PayDialog2Activity.class);
									Bundle p_bundle = new Bundle();
									p_bundle.putInt("meId",
											singleNotice.getN_meid());
									p_bundle.putInt("seId",
											singleNotice.getN_seId());
									p_bundle.putString("chargeName",
											singleNotice.getPayInfo()
													.getChargeInfo()
													.getChargeName());
									p_bundle.putDouble("amount", singleNotice
											.getPayInfo().getChargeInfo()
											.getAmount());
									p_bundle.putString("meName",
											singleNotice.getN_exam_name());
									p_bundle.putString("courseName",
											singleNotice.getN_subject());
									p_bundle.putString("freeTime", singleNotice
											.getPayInfo().getChargeInfo()
											.getFreeTime());
									p_intent.putExtras(p_bundle);
									p_intent.putExtra("singleNotice",
											singleNotice);
									p_intent.putExtra("noticetype", noticetype);
									p_intent.putExtra("position", position);
									p_intent.putExtra("fragmentType", "Notice");

									p_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									// getActivity().finish();
									startActivity(p_intent);
								}

							}

						} else if (noticetype.equals("单科成绩报告")) {

							SingleNotice singleNotice = notice_list
									.get(position);

							// 判断是否支付？
							int payStatus = singleNotice.getPayInfo()
									.getPayStatus();

							if (payStatus == 1) {
								Intent intent = new Intent(
										UIUtils.getContext(),
										SingReportActivity.class);
								Bundle v_intent = new Bundle();
								v_intent.putString("studentName",
										singleNotice.getN_studentName());
								v_intent.putString("studentId",
										singleNotice.getN_studentId());
								v_intent.putString("classId",
										singleNotice.getN_classId());
								v_intent.putInt("schoolId",
										singleNotice.getN_schoolId());
								v_intent.putInt("seId",
										singleNotice.getN_seId());
								v_intent.putInt("meId",
										singleNotice.getN_meid());
								v_intent.putInt("currentindex", position);
								v_intent.putString("examname",
										singleNotice.getN_exam_name());
								v_intent.putString("subject",
										singleNotice.getN_subject());
								v_intent.putString("time",
										singleNotice.getN_meDate());
								v_intent.putInt("msgId", singleNotice.getN_id());
								v_intent.putInt("isReaded",
										singleNotice.getN_isread());

								intent.putExtras(v_intent);
								startActivityForResult(intent,
										NOTICE_START_ACTIVITY_CODE);
							} else {
								int payType = notice_list.get(position)
										.getPayInfo().getChargeInfo()
										.getPayType();
								if (payType == 1) {
									Intent p_intent = new Intent(UIUtils
											.getContext(),
											PayDialogActivity.class);
									Bundle p_bundle = new Bundle();
									p_bundle.putInt("meId",
											singleNotice.getN_meid());
									p_bundle.putInt("seId",
											singleNotice.getN_seId());
									p_bundle.putString("chargeName",
											notice_list.get(position)
													.getPayInfo()
													.getChargeInfo()
													.getChargeName());
									p_bundle.putDouble("amount", singleNotice
											.getPayInfo().getChargeInfo()
											.getAmount());
									p_bundle.putString("meName",
											singleNotice.getN_exam_name());
									p_bundle.putString("courseName",
											notice_list.get(position)
													.getN_subject());
									p_intent.putExtras(p_bundle);

									p_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									// getActivity().finish();
									startActivity(p_intent);
								} else if (payType == 2) {
									Intent p_intent = new Intent(UIUtils
											.getContext(),
											PayDialog2Activity.class);
									Bundle p_bundle = new Bundle();
									p_bundle.putInt("meId",
											singleNotice.getN_meid());
									p_bundle.putInt("seId",
											singleNotice.getN_seId());
									p_bundle.putString("chargeName",
											notice_list.get(position)
													.getPayInfo()
													.getChargeInfo()
													.getChargeName());
									p_bundle.putDouble("amount", singleNotice
											.getPayInfo().getChargeInfo()
											.getAmount());
									p_bundle.putString("meName",
											singleNotice.getN_exam_name());
									p_bundle.putString("courseName",
											notice_list.get(position)
													.getN_subject());
									p_bundle.putString("freeTime", singleNotice
											.getPayInfo().getChargeInfo()
											.getFreeTime());
									p_intent.putExtras(p_bundle);
									p_intent.putExtra("singleNotice",
											singleNotice);
									p_intent.putExtra("noticetype", noticetype);
									p_intent.putExtra("position", position);
									p_intent.putExtra("fragmentType", "Notice");

									p_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									// getActivity().finish();
									startActivity(p_intent);
								}

							}

						} else if (noticetype.equals("试题讲解")) {

							SingleNotice singleNotice = notice_list
									.get(position);

							// 判断是否支付？
							int payStatus = singleNotice.getPayInfo()
									.getPayStatus();
							if (payStatus == 1) {
								Intent e_intent = new Intent(UIUtils
										.getContext(),
										ExplainQuestionActivity.class);
								Bundle ex_bundle = new Bundle();

								ex_bundle.putString("name",
										singleNotice.getN_exam_name());
								ex_bundle.putString("time",
										singleNotice.getN_meDate());
								ex_bundle.putInt("meId",
										singleNotice.getN_meid());
								ex_bundle.putInt("seId",
										singleNotice.getN_seId());
								ex_bundle.putString("schoolId",
										singleNotice.getN_schoolId() + "");
								ex_bundle.putString("studentId",
										singleNotice.getN_studentId());
								ex_bundle.putInt("msgType", 2);
								ex_bundle.putInt("msgId",
										singleNotice.getN_id());
								ex_bundle.putInt("isReaded",
										singleNotice.getN_isread());
								ex_bundle.putInt("currentindex", position);
								e_intent.putExtras(ex_bundle);

								startActivityForResult(e_intent,
										NOTICE_START_ACTIVITY_CODE);
							} else {
								int payType = singleNotice.getPayInfo()
										.getChargeInfo().getPayType();
								if (payType == 1) {
									Intent p_intent = new Intent(UIUtils
											.getContext(),
											PayDialogActivity.class);
									Bundle p_bundle = new Bundle();
									p_bundle.putInt("meId",
											singleNotice.getN_meid());
									p_bundle.putInt("seId",
											singleNotice.getN_seId());
									p_bundle.putString("chargeName",
											singleNotice.getPayInfo()
													.getChargeInfo()
													.getChargeName());
									p_bundle.putDouble("amount", singleNotice
											.getPayInfo().getChargeInfo()
											.getAmount());
									p_bundle.putString("meName",
											singleNotice.getN_exam_name());
									p_bundle.putString("courseName",
											singleNotice.getN_subject());
									p_intent.putExtras(p_bundle);

									p_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									// getActivity().finish();
									startActivity(p_intent);
								} else if (payType == 2) {
									Intent p_intent = new Intent(UIUtils
											.getContext(),
											PayDialog2Activity.class);
									Bundle p_bundle = new Bundle();
									p_bundle.putInt("meId",
											singleNotice.getN_meid());
									p_bundle.putInt("seId",
											singleNotice.getN_seId());
									p_bundle.putString("chargeName",
											singleNotice.getPayInfo()
													.getChargeInfo()
													.getChargeName());
									p_bundle.putDouble("amount", singleNotice
											.getPayInfo().getChargeInfo()
											.getAmount());
									p_bundle.putString("meName",
											singleNotice.getN_exam_name());
									p_bundle.putString("courseName",
											singleNotice.getN_subject());
									p_bundle.putString("freeTime", singleNotice
											.getPayInfo().getChargeInfo()
											.getFreeTime());
									p_intent.putExtras(p_bundle);
									p_intent.putExtra("singleNotice",
											singleNotice);
									p_intent.putExtra("noticetype", noticetype);
									p_intent.putExtra("position", position);
									p_intent.putExtra("fragmentType", "Notice");

									p_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									// getActivity().finish();
									startActivity(p_intent);
								}
							}

						} else if (noticetype.equals("优秀答案")) {

							SingleNotice singleNotice = notice_list
									.get(position);
							// 判断是否支付？
							int payStatus = singleNotice.getPayInfo()
									.getPayStatus();
							if (payStatus == 1) {
								Intent e_intent = new Intent(UIUtils
										.getContext(),
										ExplainQuestionActivity.class);
								Bundle ex_bundle = new Bundle();

								ex_bundle.putString("name",
										singleNotice.getN_exam_name());
								ex_bundle.putString("time",
										singleNotice.getN_meDate());
								ex_bundle.putInt("meId",
										singleNotice.getN_meid());
								ex_bundle.putInt("seId",
										singleNotice.getN_seId());
								ex_bundle.putString("schoolId",
										singleNotice.getN_schoolId() + "");
								ex_bundle.putString("studentId",
										singleNotice.getN_studentId());
								ex_bundle.putInt("msgType", 3);
								ex_bundle.putInt("msgId",
										singleNotice.getN_id());
								ex_bundle.putInt("isReaded",
										singleNotice.getN_isread());
								ex_bundle.putInt("currentindex", position);
								e_intent.putExtras(ex_bundle);

								startActivityForResult(e_intent,
										NOTICE_START_ACTIVITY_CODE);
							} else {
								int payType = singleNotice.getPayInfo()
										.getChargeInfo().getPayType();
								if (payType == 1) {
									Intent p_intent = new Intent(UIUtils
											.getContext(),
											PayDialogActivity.class);
									Bundle p_bundle = new Bundle();
									p_bundle.putInt("meId",
											singleNotice.getN_meid());
									p_bundle.putInt("seId",
											singleNotice.getN_seId());
									p_bundle.putString("chargeName",
											singleNotice.getPayInfo()
													.getChargeInfo()
													.getChargeName());
									p_bundle.putDouble("amount", singleNotice
											.getPayInfo().getChargeInfo()
											.getAmount());
									p_bundle.putString("meName",
											singleNotice.getN_exam_name());
									p_bundle.putString("courseName",
											singleNotice.getN_subject());
									p_intent.putExtras(p_bundle);

									p_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									// getActivity().finish();
									startActivity(p_intent);
								} else if (payType == 2) {
									Intent p_intent = new Intent(UIUtils
											.getContext(),
											PayDialog2Activity.class);
									Bundle p_bundle = new Bundle();
									p_bundle.putInt("meId",
											singleNotice.getN_meid());
									p_bundle.putInt("seId",
											singleNotice.getN_seId());
									p_bundle.putString("chargeName",
											singleNotice.getPayInfo()
													.getChargeInfo()
													.getChargeName());
									p_bundle.putDouble("amount", singleNotice
											.getPayInfo().getChargeInfo()
											.getAmount());
									p_bundle.putString("meName",
											singleNotice.getN_exam_name());
									p_bundle.putString("courseName",
											singleNotice.getN_subject());
									p_bundle.putString("freeTime", singleNotice
											.getPayInfo().getChargeInfo()
											.getFreeTime());
									p_intent.putExtras(p_bundle);
									p_intent.putExtra("singleNotice",
											singleNotice);
									p_intent.putExtra("noticetype", noticetype);
									p_intent.putExtra("position", position);
									p_intent.putExtra("fragmentType", "Notice");

									p_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									// getActivity().finish();
									startActivity(p_intent);
								}
							}

						} else if (noticetype.equals("失分题")) {

							SingleNotice singleNotice = notice_list
									.get(position);
							// 判断是否支付？
							int payStatus = singleNotice.getPayInfo()
									.getPayStatus();
							if (payStatus == 1) {
								Intent e_intent = new Intent(UIUtils
										.getContext(),
										ExplainQuestionActivity.class);
								Bundle ex_bundle = new Bundle();

								ex_bundle.putString("name",
										singleNotice.getN_exam_name());
								ex_bundle.putString("time",
										singleNotice.getN_meDate());
								ex_bundle.putInt("meId",
										singleNotice.getN_meid());
								ex_bundle.putInt("seId",
										singleNotice.getN_seId());
								ex_bundle.putString("schoolId",
										singleNotice.getN_schoolId() + "");
								ex_bundle.putString("studentId",
										singleNotice.getN_studentId());
								ex_bundle.putInt("msgType", 4);
								ex_bundle.putInt("msgId",
										singleNotice.getN_id());
								ex_bundle.putInt("isReaded",
										singleNotice.getN_isread());
								ex_bundle.putInt("currentindex", position);
								e_intent.putExtras(ex_bundle);

								startActivityForResult(e_intent,
										NOTICE_START_ACTIVITY_CODE);
							} else {
								int payType = singleNotice.getPayInfo()
										.getChargeInfo().getPayType();
								if (payType == 1) {
									Intent p_intent = new Intent(UIUtils
											.getContext(),
											PayDialogActivity.class);
									Bundle p_bundle = new Bundle();
									p_bundle.putInt("meId",
											singleNotice.getN_meid());
									p_bundle.putInt("seId",
											singleNotice.getN_seId());
									p_bundle.putString("chargeName",
											singleNotice.getPayInfo()
													.getChargeInfo()
													.getChargeName());
									p_bundle.putDouble("amount", singleNotice
											.getPayInfo().getChargeInfo()
											.getAmount());
									p_bundle.putString("meName",
											singleNotice.getN_exam_name());
									p_bundle.putString("courseName",
											singleNotice.getN_subject());
									p_intent.putExtras(p_bundle);

									p_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									// getActivity().finish();
									startActivity(p_intent);
								} else if (payType == 2) {
									Intent p_intent = new Intent(UIUtils
											.getContext(),
											PayDialog2Activity.class);
									Bundle p_bundle = new Bundle();
									p_bundle.putInt("meId",
											singleNotice.getN_meid());
									p_bundle.putInt("seId",
											singleNotice.getN_seId());
									p_bundle.putString("chargeName",
											singleNotice.getPayInfo()
													.getChargeInfo()
													.getChargeName());
									p_bundle.putDouble("amount", singleNotice
											.getPayInfo().getChargeInfo()
											.getAmount());
									p_bundle.putString("meName",
											singleNotice.getN_exam_name());
									p_bundle.putString("courseName",
											singleNotice.getN_subject());
									p_bundle.putString("freeTime", singleNotice
											.getPayInfo().getChargeInfo()
											.getFreeTime());
									p_intent.putExtras(p_bundle);
									p_intent.putExtra("singleNotice",
											singleNotice);
									p_intent.putExtra("noticetype", noticetype);
									p_intent.putExtra("position", position);
									p_intent.putExtra("fragmentType", "Notice");

									p_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									// getActivity().finish();
									startActivity(p_intent);
								}
							}
						} else {

						}

						// ToastUtils.show(UIUtils.getContext(),
						// "you selcect the " + position + "item");
					}
				});
		readData(true);
		return view;
	}

	private void readData(final boolean refrehing) {

		SharedPreferences share = getActivity().getSharedPreferences(
				Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);

		isFirst = false;
		if (refrehing) {
			currentPage = 1;
			isEnd = false;
		}

		param = new JSONObject();

		try {
			param.put("type", "QueryMsgReqInfo");

			Log.i("--------------", share.getBoolean("isphone", false) + "");

			if (share.getBoolean("isphone", false)) {
				param.put("studentId", share.getString("login_phone", ""));
				param.put("studentName", share.getString("login_password", ""));
			} else {
				param.put("studentId", share.getString("userCode", ""));
				param.put("studentName", share.getString("userName", ""));
			}

			param.put("schoolId", share.getInt("schoolId", 0));
			param.put("pageIndex", currentPage + "");
			param.put("limitCount", Constants.ROWS_NOTICE_LIMIT + "");
			param.put("userId", share.getString("userId", ""));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("lichg", "param=" + param);

		Log.d("serverurl", "--------serverurl:" + Noticeserverurl);
		final long currentTime1 = System.currentTimeMillis();
		Log.i("lxw", "请求之前的时间" + currentTime1);
		MyHttpUtil httpUtil = MyHttpUtil.getInstance(context);
		loading = true;
		httpUtil.request(Noticeserverurl, param, new MyHttpUtil.HttpCallback() {
			@Override
			public void success(String result) {
				long currentTime2 = System.currentTimeMillis();
				Log.i("lxw", "请求拿到数据的时间：" + (currentTime2 - currentTime1));
				Log.i("Lichg", "notice sucessfully!!!!!!!");
				System.out.println(result);
				// 去除顶部(刷新)或者底部(加载)对应的头和尾
				mPullRefreshListView.onPullDownRefreshComplete();
				mPullRefreshListView.onPullUpRefreshComplete();
				getInfoSuccess(result, refrehing);
				loading = false;
			}

			@Override
			public void error(int state, String errorMsg) {
				Log.i("Lichg", "notice faild!!!!!!!");
				loadingHelper.ShowError(errorMsg);
				loading = false;
			}
		});
	}

	private void getInfoSuccess(String backString, boolean refreshing) {
		if (GsonUtil.checkJson(backString)) {
			loadingHelper.HideLoading(View.INVISIBLE);
			if (refreshing || isFirst) {
				notice_con = JsonUtils.jsontoNoticeList(backString);
				if (notice_con != null) {
					notice_list = notice_con.getNoticelist();
				}
			} else {
				Notice_Con t_singlenotice_t = JsonUtils
						.jsontoNoticeList(backString);
				if (t_singlenotice_t != null
						&& t_singlenotice_t.getNoticelist() != null) {
					notice_list.addAll(t_singlenotice_t.getNoticelist());
				}
				if (t_singlenotice_t == null
						|| t_singlenotice_t.getNoticelist().size() < Constants.ROWS_EXAM_LIMIT) {
					isEnd = true;
				}
			}
			if (notice_list == null || notice_list.size() == 0) {
				loadingHelper.ShowEmptyData();
			}
			flushListview(notice_list);
		} else {
			loadingHelper.ShowError(this.getString(R.string.s_loading_no_data));
		}
	}

	private void flushListview(final List<SingleNotice> list) {
		mPullRefreshListView.getFooterLoadingLayout().setVisibility(View.GONE);
		mPullRefreshListView.setTotalCount(notice_con.getTotal());
		mainactivity.setBadge(notice_con.getUnread());
		if (adapter == null) {
			adapter = new MyNoticeAdapter(UIUtils.getContext(), list);
			mPullRefreshListView.getRefreshableView().setAdapter(adapter);
		} else {
			adapter.setList(list);
		}
	}

	@Override
	public void OnRetryClick() {
		// TODO Auto-generated method stub
		loadingHelper.ShowLoading();
		readData(true);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		if (requestCode == NOTICE_START_ACTIVITY_CODE) {

			switch (resultCode) {
			case NOTICE_CODE_SHIFENTI_ACTIVITY_RESULT:
				int position = intent.getExtras().getInt("currentindex");
				int isRead = intent.getExtras().getInt("isReaded");
				Log.i("onActivityResult", position + "------" + isRead);
				notice_list.get(position).setN_isread(1);
				if (isRead != 1) {
					adapter.notifyDataSetChanged();
					int t = notice_con.getUnread() - 1;
					notice_con.setUnread(t);
					mainactivity.setBadge(t);
				}
				// readData(true);
				// adapter.notifyDataSetChanged();

				break;
			default:
				break;
			}
		}
	}

	public void update() {
		if (notice_list != null && notice_list.size() != 0 && firstin == true) {
			flushListview(notice_list);

			mPullRefreshListView.onPullDownRefreshComplete();
			mPullRefreshListView.onPullUpRefreshComplete();
			Log.i("Lichg", "notice update");
			firstin = false;
		}
		Log.i("Lichg", "notice update");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("NoticeFragment");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("NoticeFragment");
	}

}
