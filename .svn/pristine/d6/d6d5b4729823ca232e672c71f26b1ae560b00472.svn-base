package com.xyd.student.xydexamanalysis.ui.weike;

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

import cn.jpush.im.proto.v;

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
import com.xyd.student.xydexamanalysis.entity.weike.Weike;
import com.xyd.student.xydexamanalysis.ui.WebViewActivity;
import com.xyd.student.xydexamanalysis.utils.NetWorkUtils;
import com.xyd.student.xydexamanalysis.view.FullListView;
import com.xyd.student.xydexamanalysis.view.LoadingHelper;
import com.xyd.student.xydexamanalysis.view.LoadingListener;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SelectWeikeActivity extends Activity implements OnClickListener,
		LoadingListener {
	private LinearLayout linear_container;
	private TextView tv_edit;
	private ImageView img_close;
	public static final int FAIL = 1;
	public static final int SUSS = 2;
	public static final int NO_DATA = 3;
	private WeiRecord wr;
	private List<WeiRecord> list;
	private List<List<WeiRecord>> listAll;
	private List<FullListView> listViews;
	private WeikeRecordAdapter adapter;
	private List<WeikeRecordAdapter> listAdapter;
	private FullListView listview;
	private static final int REQUES_CODE = 1;
	private ImageView img_save, img_del;
	private String reStr;
	private List<WeiRecord> listSelct;
	private boolean selectAll, select;
	public static final int REQUEST = 1;
	private LoadingHelper loadingHelper;
	private ProgressBar progressBar;
	private ImageView imgage_no_data,img_no_data2;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case FAIL:
				String result = (String) msg.obj;
				// Toast.makeText(SelectWeikeActivity.this, "FAILLURE" + result,
				// Toast.LENGTH_LONG).show();
				showNoData("加载失败，请重试！");
				break;
			case NO_DATA:
				showNoData("暂无数据！");
				break;
			case SUSS:
				loadingHelper.HideLoading(View.INVISIBLE);
				listAll.clear();
				listViews.clear();
				listAdapter.clear();
				listSelct.clear();
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
					adapter = new WeikeRecordAdapter(SelectWeikeActivity.this,
							item2);
					adapter.setFlag(true);
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
										wr.setSave(!wr.isSave());
										listAll.get(i).set(j, wr);
										adapter = listAdapter.get(i);
										FullListView listView = listViews
												.get(i);
										adapter.notifyDataSetChanged();
										break;
									}
								}
							}
							int count = -1;
							for (int i = 0; i < listAll.size(); i++) {
								for (int j = 0; j < listAll.get(i).size(); j++) {
									WeiRecord wr = listAll.get(i).get(j);
									if (wr.isSave()) {
										count++;
										break;
									}
								}
							}
							if (count != -1) {
								select = true;
							} else {
								select = false;
							}
							setImage();
						}
					});
				}
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_weike);
		init();
		loadingHelper = new LoadingHelper(
				findViewById(R.id.loading_prompt_linear),
				findViewById(R.id.loading_empty_prompt_linear));
		loadingHelper.ShowLoading();
		loadingHelper.SetListener(this);
		progressBar = (ProgressBar) findViewById(R.id.loading_prompt_progress);
		imgage_no_data = (ImageView) findViewById(R.id.prompt_refresh);
		img_no_data2 = (ImageView) findViewById(R.id.img_no_data);
		if (!NetWorkUtils.isNetworkAvailable(this)) {
			loadingHelper.ShowError("请检查您的网络连接！");
			return;
		}
		loadData();
	}

	private void init() {
		overridePendingTransition(R.anim.up_anim, R.anim.in_start_anim);

		linear_container = (LinearLayout) findViewById(R.id.weike_record_container);
		tv_edit = (TextView) findViewById(R.id.weke_tv_edit_all);
		img_close = (ImageView) findViewById(R.id.weike_img_close);
		img_save = (ImageView) findViewById(R.id.weke_save);
		img_del = (ImageView) findViewById(R.id.weke_del);
		img_save.setVisibility(View.VISIBLE);
		img_del.setVisibility(View.VISIBLE);
		
		tv_edit.setOnClickListener(this);
		img_close.setOnClickListener(this);
		img_save.setOnClickListener(this);
		img_del.setOnClickListener(this);

		listAll = new ArrayList<List<WeiRecord>>();
		listAdapter = new ArrayList<WeikeRecordAdapter>();
		listViews = new ArrayList<FullListView>();
		listSelct = new ArrayList<WeiRecord>();
		selectAll = false;
		select = false;
		setImage();
	}

	private void showNoData(String str) {
		int count = 0;
		if (listAll == null || listAll.size() == 0) {
			loadingHelper.ShowError(str);
			tv_edit.setVisibility(View.INVISIBLE);
			tv_edit.setClickable(false);
			progressBar.setVisibility(View.GONE);
			imgage_no_data.setVisibility(View.GONE);
			
			loadingHelper.HideLoading(View.GONE);
			img_no_data2.setVisibility(View.VISIBLE);
			img_save.setVisibility(View.INVISIBLE);
			img_del.setVisibility(View.INVISIBLE);
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
			
			loadingHelper.HideLoading(View.GONE);
			img_no_data2.setVisibility(View.VISIBLE);
			img_save.setVisibility(View.INVISIBLE);
			img_del.setVisibility(View.INVISIBLE);
			return;
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.weke_tv_edit_all:
			selectAll = !selectAll;
			if (selectAll) {
				tv_edit.setText("取消全选");
			} else {
				tv_edit.setText("全选");
			}
			for (int i = 0; i < listAll.size(); i++) {
				for (int j = 0; j < listAll.get(i).size(); j++) {
					WeiRecord w = listAll.get(i).get(j);
					w.setSave(selectAll);
					listAll.get(i).set(j, w);
				}
				adapter = listAdapter.get(i);
				adapter.notifyDataSetChanged();
			}
			select = selectAll;
			setImage();
			break;
		case R.id.weike_img_close:
			close();
			break;
		case R.id.weke_save:
			if (select || selectAll) {
				save(true);
			}
			break;
		case R.id.weke_del:
			if (select || selectAll) {
				Intent intent = new Intent(SelectWeikeActivity.this,
						DelwkSelectActivity.class);
				intent.putExtra("title", "确认删除选中的微课？");
				intent.putExtra("left", "取消");
				intent.putExtra("right", "删除");
				intent.putExtra("type", 0);
				startActivityForResult(intent, REQUEST);
			}
			break;
		}

	}

	private void close() {
		this.finish();
		overridePendingTransition(0, R.anim.down_anim);
	}

	@Override
	public void onBackPressed() {
		close();
	}

	private void loadData() {
		SharedPreferences share = getSharedPreferences(
				Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
		String userId = share.getString("userId", "");
		HttpUtils httpUtils = new HttpUtils();
		// Toast.makeText(this, userId, Toast.LENGTH_LONG).show();
		System.out.print("userId=" + userId);
		httpUtils.configCurrentHttpCacheExpiry(200);
		httpUtils.send(HttpMethod.GET,
				String.format(Constant.GET_WEIKE, userId, "2"),
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

	public void save(final boolean isSave) {
		int count = -1;
		listSelct.clear();
		for (int i = 0; i < listAll.size(); i++) {
			for (int j = 0; j < listAll.get(i).size(); j++) {
				WeiRecord w = listAll.get(i).get(j);
				if (w.isSave()) {
					count++;
					listSelct.add(w);
				}
			}
		}

		if (!select && !selectAll) {
			Toast.makeText(SelectWeikeActivity.this, "至少选择一个",
					Toast.LENGTH_LONG).show();
		} else {
			new Thread(new Runnable() {
				@Override
				public void run() {
					JSONArray array = new JSONArray();
					try {
						for (int i = 0; i < listSelct.size(); i++) {
							JSONObject object = new JSONObject();
							WeiRecord w = listSelct.get(i);
							object.put("id", w.getId());
							if (isSave) {
								object.put("optionType", "1");
								object.put("content", "1");
							} else {
								object.put("optionType", "4");
								object.put("content", "1");
							}
							array.put(object);
						}
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
									new InputStreamReader(
											conn.getInputStream(), "utf-8"));
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
								// Toast.makeText(SelectWeikeActivity.this,
								// "数据发送成功" + ",result=" + reStr,
								// Toast.LENGTH_LONG).show();
								for (int i = listAll.size() - 1; i >= 0; i--) {
									for (int j = listAll.get(i).size() - 1; j >= 0; j--) {
										WeiRecord w = listAll.get(i).get(j);
										if (w.isSave()) {
											listAll.get(i).remove(j);
											adapter = listAdapter.get(i);
											adapter.notifyDataSetChanged();
										}
									}
								}
								showNoData("暂无数据！");
								int count = -1;
								for (int i = 0; i < listAll.size(); i++) {
									for (int j = 0; j < listAll.get(i).size(); j++) {
										WeiRecord w = listAll.get(i).get(j);
										if (w.isSave()) {
											count++;
										}
									}
								}
								if (count != -1) {
									img_save.setImageResource(R.drawable.weike_save_img);
									img_del.setImageResource(R.drawable.weike_del_img);
								} else {
									img_save.setImageResource(R.drawable.bt_shoucang_hui);
									img_del.setImageResource(R.drawable.bt_shanchu_hui);
								}
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
	}

	public void setImage() {
		if (select) {
			img_save.setImageResource(R.drawable.weike_save_img);
			img_del.setImageResource(R.drawable.weike_del_img);
		} else {
			img_save.setImageResource(R.drawable.bt_shoucang_hui);
			img_del.setImageResource(R.drawable.bt_shanchu_hui);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST && resultCode == RESULT_OK) {
			save(false);
			int count = -1;
			for (int i = 0; i < listAll.size(); i++) {
				for (int j = 0; j < listAll.get(i).size(); j++) {
					WeiRecord w = listAll.get(i).get(j);
					if (w.isSave()) {
						count++;
					}
				}
			}
			if (count != -1) {
				img_save.setImageResource(R.drawable.weike_save_img);
				img_del.setImageResource(R.drawable.weike_del_img);
			} else {
				img_save.setImageResource(R.drawable.bt_shoucang_hui);
				img_del.setImageResource(R.drawable.bt_shanchu_hui);
			}
		}
	}

	@Override
	public void OnRetryClick() {
		// TODO Auto-generated method stub

	}
}
