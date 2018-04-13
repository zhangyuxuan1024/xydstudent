package com.xyd.student.xydexamanalysis.ui;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;
import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.Fragment.NoticeFragment;
import com.xyd.student.xydexamanalysis.adapter.ExplainqustionAdapter;
import com.xyd.student.xydexamanalysis.constant.Constants;
import com.xyd.student.xydexamanalysis.entity.ExplainInfo;
import com.xyd.student.xydexamanalysis.entity.Explain_single_info;
import com.xyd.student.xydexamanalysis.utils.JsonUtils;
import com.xyd.student.xydexamanalysis.utils.LoginUtils;
import com.xyd.student.xydexamanalysis.utils.MyHttpUtil;
import com.xyd.student.xydexamanalysis.utils.ToastUtils;
import com.xyd.student.xydexamanalysis.utils.UIUtils;
import com.xyd.student.xydexamanalysis.view.LoadingHelper;
import com.xyd.student.xydexamanalysis.view.LoadingListener;
import com.xyd.student.xydexamanalysis.view.TitleBar;
import com.xyd.student.xydexamanalysis.view.TitleBar.TitleOnClickListener;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ExplainQuestionActivity extends Activity implements
		TitleOnClickListener, LoadingListener {

	private LoadingHelper loadingHelper;
	private ListView listView;
	private View headview;
	private Handler mhanHandler;
	private Context mContext;
	private TextView examtitle_tx;
	private TextView examdate_tx;
	private Bundle bundle;
	private JSONObject param;
	private int UP_SUCCESS = 1;
	private int UP_ERROR = 2;
	private String courseName;
	private ArrayList<Explain_single_info> explain_single_infosList;
	private String fullScore;
	private String maxScore;
	private String meDate;
	private String studentCount;
	private String meName;
	private TextView noinfoTextView;
	private LoginUtils login;
	private String Query_Msg_detail;

	private ArrayList<Explain_single_info> datalist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exameanalydetail_layout);
		login = new LoginUtils();
		mContext = this;
		mhanHandler = new Handler();
		datalist = new ArrayList<Explain_single_info>();
		this.bundle = this.getIntent().getExtras();
		initView();
		SharedPreferences msharedPreferences = this.getSharedPreferences(
				Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
		Query_Msg_detail = msharedPreferences.getString("ReportServUrl", "");
		if (Query_Msg_detail.equals("")) {
			ToastUtils.show(this, "获取服务器地址错误，请重新登录");
			finish();
			return;
		} else {
			Query_Msg_detail = Query_Msg_detail + "sac/querymsgdetail";
		}
		readData();
	}

	private void initView() {
		getActionBar().hide();
		TitleBar titlebar = (TitleBar) findViewById(R.id.title_bar);
		titlebar.setLeftIcon(R.drawable.x);
		type = bundle.getInt("msgType");
		if (type == 2) {
			titlebar.setTitle(getResources().getString(
					R.string.explain_question));
		} else if (type == 3) {
			titlebar.setTitle(getResources().getString(R.string.youxiu));
		} else if (type == 4) {
			titlebar.setTitle(getResources().getString(R.string.shifen));
		}
		titlebar.setTitleClickListener(this);

		loadingHelper = new LoadingHelper(
				findViewById(R.id.loading_prompt_linear),
				findViewById(R.id.loading_empty_prompt_linear));
		loadingHelper.ShowLoading();
		loadingHelper.SetListener(this);

		listView = (ListView) findViewById(R.id.my_noticee_frame);
		examtitle_tx = (TextView) findViewById(R.id.examtitle_tx);
		examdate_tx = (TextView) findViewById(R.id.examdate_tx);
		noinfoTextView = (TextView) findViewById(R.id.tv);

	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 1:
				loadingHelper.HideLoading(View.INVISIBLE);
				noticeListUpdate();
				Bundle bundle = msg.getData();
				String result = (String) bundle.get("result");
				Log.i("miss", "result===" + result);
				System.out.println(result);
				getInfoSuccess(result);

				break;
			case 2:
				Bundle bundle2 = msg.getData();
				String errorMsg = (String) bundle2.get("errorMsg");
				loadingHelper.ShowError(errorMsg);
				break;
			default:
				break;
			}

		}
	};
	private int type;

	private void readData() {
		// TODO Auto-generated method stub
		MyHttpUtil httpUtil = MyHttpUtil.getInstance(UIUtils.getContext());
		try {
			param = new JSONObject();
			param.put("type", "QueryMsgDetailReqInfo");
			param.put("studentId", bundle.getString("studentId"));
			param.put("schoolId", bundle.getString("schoolId"));
			param.put("seId", bundle.getInt("seId"));
			param.put("meId", bundle.getInt("meId"));
			param.put("msgId", bundle.getInt("msgId"));
			param.put("isReaded", bundle.getInt("isReaded"));
			param.put("msgType", bundle.getInt("msgType"));
			param.put("userId", login.getLoginUserId());
			System.out.println(param);
			httpUtil.request(Query_Msg_detail, param,
					new MyHttpUtil.HttpCallback() {
						@Override
						public void success(String result) {
							// TODO Auto-generated method stub
							System.out.println(result);
							Message message = new Message();
							message.what = UP_SUCCESS;
							Bundle bundle = new Bundle();
							bundle.putString("result", result);
							message.setData(bundle);
							handler.sendMessage(message);
						}

						@Override
						public void error(int state, String errorMsg) {
							// TODO Auto-generated method stub
							Message message = new Message();
							message.what = UP_ERROR;
							Bundle bundle = new Bundle();
							bundle.putString("errorMsg", errorMsg);
							message.setData(bundle);
							handler.sendMessage(message);
						}
					});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getInfoSuccess(String result) {
		Log.i("getInfoSuccess", result);
		ExplainInfo explainInfo = JsonUtils.jsontoExplainList(result);
		courseName = explainInfo.getCourseName();
		explain_single_infosList = explainInfo.getExplain_single_infosList();
		Log.i("测试专用点", explain_single_infosList + "");
		fullScore = explainInfo.getFullScore();
		maxScore = explainInfo.getMaxScore();
		meDate = explainInfo.getMeDate();
		studentCount = explainInfo.getStudentCount();
		meName = explainInfo.getMeName();
		initData();

	}

	private void initData() {
		// TODO Auto-generated method stub
		examtitle_tx.setText(meName + "(" + courseName + ")");
		examdate_tx.setText(meDate.substring(0, 10));

		datalist = explain_single_infosList;
		if (datalist != null) {
			ExplainqustionAdapter exlainquestionadapter = new ExplainqustionAdapter(
					mContext, 0, datalist);
			listView.setAdapter(exlainquestionadapter);
		} else {
			listView.setVisibility(View.GONE);
			noinfoTextView.setVisibility(View.VISIBLE);
			String info = null;
			if (type == 2) {
				info = "试题讲解";
			} else if (type == 3) {
				info = "优秀试题";
			} else if (type == 4) {
				info = "失分题";
			}
			noinfoTextView.setText("该科目您没有" + info + "!");
		}
	}

	@Override
	public void leftClick() {
		// TODO Auto-generated method stub
		// Intent intent = new Intent(this, MainActivity.class);
		// startActivity(intent);
		this.finish();
	}

	@Override
	public void rightClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void titleClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnRetryClick() {
		// TODO Auto-generated method stub
		loadingHelper.ShowLoading();
		readData();
	}

	private void noticeListUpdate() {
		// TODO Auto-generated method stub
		Intent it = new Intent(this, MainActivity.class);
		Bundle v_it = new Bundle();
		v_it.putInt("currentindex", bundle.getInt("currentindex"));
		v_it.putInt("isReaded", bundle.getInt("isReaded"));
		it.putExtras(v_it);
		Log.i("setResult", bundle.getInt("currentindex") + "/////////////"
				+ bundle.getInt("isReaded"));
		setResult(NoticeFragment.NOTICE_CODE_SHIFENTI_ACTIVITY_RESULT, it);
	}

	/**
	 * 友盟统计
	 */
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("ExplainQuestionActivity");
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("ExplainQuestionActivity");
		MobclickAgent.onPause(this);
	}

}
