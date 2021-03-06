package com.xyd.student.xydexamanalysis.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.adapter.WeikeRecordAdapter;
import com.xyd.student.xydexamanalysis.constant.Constant;
import com.xyd.student.xydexamanalysis.constant.Constants;
import com.xyd.student.xydexamanalysis.entity.weike.WeiRecord;
import com.xyd.student.xydexamanalysis.ui.weike.DelwkSelectActivity;
import com.xyd.student.xydexamanalysis.ui.weike.ScanRecordActivity;
import com.xyd.student.xydexamanalysis.ui.weike.SelectWeikeActivity;
import com.xyd.student.xydexamanalysis.ui.weike.UpdateWeikeActivity;
import com.xyd.student.xydexamanalysis.utils.NetWorkUtils;
import com.xyd.student.xydexamanalysis.view.FullListView;
import com.xyd.student.xydexamanalysis.view.LoadingHelper;
import com.xyd.student.xydexamanalysis.view.LoadingListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

//import android.widget.Toast;

public class MySaveActivity extends Activity implements OnClickListener,
		LoadingListener {
	private LinearLayout linear_container;
	private TextView tv_edit;
	private ImageView img_close;
	public static final int FAIL = 1;
	public static final int SUSS = 2;
	private WeiRecord wr;
	private List<WeiRecord> list;
	private List<List<WeiRecord>> listAll;
	private List<FullListView> listViews;
	private WeikeRecordAdapter adapter;
	private List<WeikeRecordAdapter> listAdapter;
	private FullListView listview;
	private static final int REQUES_CODE = 1;
	private static final int REQUES_DEL = 2;
	private static final int NO_DATA = 3;
	private int cur_list, cur_position;
	private String reStr;
	private LoadingHelper loadingHelper;
	private ProgressBar progressBar;
	private ImageView imgage_no_data, img_no_data2;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case FAIL:
				String result = (String) msg.obj;
				// Toast.makeText(MySaveActivity.this, "FAILLURE" + result,
				// Toast.LENGTH_LONG).show();
				showNoData("加载失败，请重试！");
				break;
			case NO_DATA:
				showNoData("暂无数据！");
				break;
			case SUSS:
				loadingHelper.HideLoading(View.INVISIBLE);
				listAll.clear();
				listAdapter.clear();
				listViews.clear();
				linear_container.removeAllViews();

				result = (String) msg.obj;
				WeiRecord wr = new WeiRecord();
				JSONArray array = null;
				try {
					JSONObject object = new JSONObject(result);
					array = object.getJSONArray("wkRecords");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				wr.parserJson(array);
				list = wr.getList();

				List<WeiRecord> item = null;
				int k = 0;
				if (list == null || list.size() < 1) {
					return;
				}
				for (int i = 0; i < list.size(); i++) {
					String time = list.get(i).getIndbtime();
					time = time.substring(0, time.indexOf(" "));
					WeiRecord wr2 = list.get(i);
					wr2.setIndbtime(time);
					if (wr2.getIsCollected().equals("0")) {
						wr2.setClickLike(false);
					} else {
						wr2.setClickLike(true);
					}
					list.set(i, wr2);
					if (i == 0) {
						item = new ArrayList<WeiRecord>();
						item.add(list.get(i));
						k = 0;
					} else if (item.get(k).getIndbtime().contains(time)) {
						item.add(list.get(i));
						k++;
					} else if (!item.get(k).getIndbtime().contains(time)) {
						listAll.add(item);
						item = null;
						item = new ArrayList<WeiRecord>();
						item.add(list.get(i));
						k = 0;
					}
				}
				listAll.add(item);
				showNoData("暂无数据！");
				for (int i = 0; i < listAll.size(); i++) {
					List<WeiRecord> item2 = listAll.get(i);
					adapter = new WeikeRecordAdapter(MySaveActivity.this, item2);
					listAdapter.add(adapter);
					View v = LayoutInflater.from(getApplicationContext())
							.inflate(R.layout.weike_scan_record_listview, null);
					listview = (FullListView) v
							.findViewById(R.id.weike_full_listview);
					listview.setAdapter(adapter);
					listViews.add(listview);
					linear_container.addView(v);

					adapter.setEditClick(new OnClickListener() {
						@Override
						public void onClick(View v) {
							String url = (String) v.getTag();
							for (int i = 0; i < listAll.size(); i++) {
								for (int j = 0; j < listAll.get(i).size(); j++) {
									WeiRecord wr = listAll.get(i).get(j);
									if (wr.getWkUrl().equals(url)) {
										wr.setClickEdit(true);
										listAll.get(i).set(j, wr);
										adapter = listAdapter.get(i);
										FullListView listView = listViews
												.get(i);
										adapter.notifyDataSetChanged();
										Intent intent = new Intent(
												MySaveActivity.this,
												UpdateWeikeActivity.class);
										intent.putExtra("name", wr.getWkName());
										intent.putExtra("id", wr.getId());
										startActivityForResult(intent,
												REQUES_CODE);
										cur_list = i;
										cur_position = j;
										break;
									}
								}
							}
						}
					});
					adapter.setLikeClick(new OnClickListener() {
						@Override
						public void onClick(View v) {
							WeiRecord w = (WeiRecord) v.getTag();
							String url = w.getWkUrl();
							for (int i = 0; i < listAll.size(); i++) {
								for (int j = 0; j < listAll.get(i).size(); j++) {
									WeiRecord wr = listAll.get(i).get(j);
									if (wr.getWkUrl().equals(url)) {
										cur_list = i;
										cur_position = j;
										boolean flag = !wr.isClickLike();
										if (flag) {
											wr.setClickLike(flag);
											listAll.get(i).set(j, wr);
											adapter = listAdapter.get(i);
											adapter.notifyDataSetChanged();
											addLike(w, wr.isClickLike());
										} else {
											alert();
										}
										break;
									}
								}
							}
						}
					});
				}
				break;
			}
		};
	};

	private void alert() {
		Intent intent = new Intent(MySaveActivity.this,
				DelwkSelectActivity.class);
		intent.putExtra("title", "确认取消收藏？");
		intent.putExtra("left", "否");
		intent.putExtra("right", "是");
		startActivityForResult(intent, REQUES_DEL);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mysave);
		init();
		loadingHelper = new LoadingHelper(
				findViewById(R.id.loading_prompt_linear),
				findViewById(R.id.loading_empty_prompt_linear));
		loadingHelper.ShowLoading();
		loadingHelper.SetListener(this);
		progressBar = (ProgressBar) findViewById(R.id.loading_prompt_progress);
		imgage_no_data = (ImageView) findViewById(R.id.prompt_refresh);
		if (!NetWorkUtils.isNetworkAvailable(this)) {
			loadingHelper.ShowError("请检查您的网络连接！");
			return;
		}
		loadData();
	}

	private void init() {
		linear_container = (LinearLayout) findViewById(R.id.weike_record_container);
		tv_edit = (TextView) findViewById(R.id.weke_tv_edit);
		img_close = (ImageView) findViewById(R.id.weike_img_close);

		tv_edit.setOnClickListener(this);
		img_close.setOnClickListener(this);

		listAll = new ArrayList<List<WeiRecord>>();
		listAdapter = new ArrayList<WeikeRecordAdapter>();
		listViews = new ArrayList<FullListView>();

		img_no_data2 = (ImageView) findViewById(R.id.img_no_data);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.weke_tv_edit:
			Intent intent = new Intent(MySaveActivity.this,
					SelectWeikeActivity.class);
			startActivity(intent);
			break;
		case R.id.weike_img_close:
			this.finish();
			overridePendingTransition(R.anim.in_anim2, R.anim.out_anim);
			break;
		}

	}

	// 点击手机的返回按钮
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.in_anim2, R.anim.out_anim);
	}

	private void loadData() {
		SharedPreferences share = getSharedPreferences(
				Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
		String userId = share.getString("userId", "");
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.configCurrentHttpCacheExpiry(2000);
		// Toast.makeText(this, userId, Toast.LENGTH_LONG).show();
		System.out.print("userId=" + userId);
		httpUtils.send(HttpMethod.GET,
				String.format(Constant.GET_WEIKE, userId, "1"),
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Message msg = new Message();
						msg.what = FAIL;
						msg.obj = arg1;
						mHandler.sendMessage(msg);
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						Message msg = new Message();
						String result = arg0.result;
						try {
							JSONObject object = new JSONObject(result);
							int resultCode = object.getInt("resultCode");
							if (resultCode != 0) {
								msg.what = NO_DATA;
								mHandler.sendMessage(msg);
								return;
							}
							msg.obj = result;
							msg.what = SUSS;
							mHandler.sendMessage(msg);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUES_CODE:
				Bundle bundle = data.getExtras();
				String name = bundle.getString("name");
				WeiRecord w = listAll.get(cur_list).get(cur_position);
				w.setWkName(name);
				w.setClickEdit(false);
				listAll.get(cur_list).set(cur_position, w);
				adapter = listAdapter.get(cur_list);
				adapter.notifyDataSetChanged();
				break;
			case REQUES_DEL:
				WeiRecord wr = listAll.get(cur_list).get(cur_position);
				wr.setClickLike(false);
				listAll.get(cur_list).set(cur_position, wr);
				adapter = listAdapter.get(cur_list);
				adapter.notifyDataSetChanged();
				addLike(wr, wr.isClickLike());
				listAll.get(cur_list).remove(cur_position);
				showNoData("暂无数据！");
				break;
			default:
				break;
			}
		} else {
			WeiRecord w = listAll.get(cur_list).get(cur_position);
			w.setClickEdit(false);
			listAll.get(cur_list).set(cur_position, w);
			adapter = listAdapter.get(cur_list);
			adapter.notifyDataSetChanged();
		}
	};

	private void addLike(final WeiRecord w, final boolean isLike) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				JSONArray array = new JSONArray();
				JSONObject object = new JSONObject();
				try {
					object.put("id", w.getId());
					if (isLike) {
						object.put("optionType", "1");
						object.put("content", "1");
					} else {
						object.put("optionType", "2");
						object.put("content", "0");
					}

					array.put(object);
					JSONObject json = new JSONObject();
					json.put("modifyWKInfos", array.toString());
					String jsonstr = json.toString();

					if (jsonstr.contains("\\")) {
						jsonstr = jsonstr.replace("\\", "");
						jsonstr = jsonstr.replace("\"[", "[");
						jsonstr = jsonstr.replace("]\"", "]");
					}
					URL url = new URL(Constant.UPDATE_WEIKE);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setDoInput(true);
					conn.setDoOutput(true);
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type",
							"application/json;charset=UTF-8");
					OutputStreamWriter osw = new OutputStreamWriter(conn
							.getOutputStream(), "utf-8");
					osw.write(jsonstr);
					osw.flush();

					conn.setConnectTimeout(1000 * 100);
					conn.setReadTimeout(1000 * 100);
					if (conn.getResponseCode() == 200) {
						BufferedReader br = new BufferedReader(
								new InputStreamReader(conn.getInputStream(),
										"utf-8"));
						String str = "";
						while ((str = br.readLine()) != null) {
							reStr += str;
						}
						br.close();
						conn.disconnect();
					}
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							// Toast.makeText(MySaveActivity.this,
							// "数据发送成功" + ",result=" + reStr,
							// Toast.LENGTH_LONG).show();
						}
					});
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		loadData();
	}

	private void showNoData(String str) {
		int count = 0;
		if (listAll == null || listAll.size() == 0) {
			loadingHelper.ShowError(str);
			tv_edit.setVisibility(View.INVISIBLE);
			tv_edit.setClickable(false);
			progressBar.setVisibility(View.GONE);
			imgage_no_data.setVisibility(View.GONE);
			img_no_data2.setVisibility(View.VISIBLE);
			return;
		}
		for (int i = 0; i < listAll.size(); i++) {
			for (int j = 0; j < listAll.get(i).size(); j++) {
				count++;
			}
		}
		if (count <= 0) {
			loadingHelper.ShowError(str);
			tv_edit.setVisibility(View.INVISIBLE);
			tv_edit.setClickable(false);
			progressBar.setVisibility(View.GONE);
			imgage_no_data.setVisibility(View.GONE);
			img_no_data2.setVisibility(View.VISIBLE);
			return;
		}
	}

	@Override
	public void OnRetryClick() {
		// TODO Auto-generated method stub

	};
}
