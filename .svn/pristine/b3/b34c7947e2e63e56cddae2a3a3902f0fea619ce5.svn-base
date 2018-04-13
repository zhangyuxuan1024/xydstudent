package com.xyd.student.xydexamanalysis.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;
import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.constant.Constants;
import com.xyd.student.xydexamanalysis.entity.Login_school;
import com.xyd.student.xydexamanalysis.entity.Login_school_con;
import com.xyd.student.xydexamanalysis.entity.Login_school_config;
import com.xyd.student.xydexamanalysis.utils.GsonUtil;
import com.xyd.student.xydexamanalysis.utils.JsonUtils;
import com.xyd.student.xydexamanalysis.utils.MyHttpUtil;
import com.xyd.student.xydexamanalysis.utils.MyHttpUtil.HttpCallback;
import com.xyd.student.xydexamanalysis.utils.ToastUtils;
import com.xyd.student.xydexamanalysis.utils.UIUtils;
import com.xyd.student.xydexamanalysis.view.LoadingHelper;
import com.xyd.student.xydexamanalysis.view.TitleBar;
import com.xyd.student.xydexamanalysis.view.TitleBar.TitleOnClickListener;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class LoginSearchSchoolActivity extends Activity implements
		TitleOnClickListener {

	private EditText search_view;
	private ListView search_list;
	private ImageView ivDeleteText;
	private ArrayList<Login_school> schoollist;
	private Login_school_con loginschoolcon;
	private SimpleAdapter schoollistadapter;
	private Context context;
	private String latestsearch, latestsearching;
	private ArrayList<Map<String, Object>> mData;
	private LoadingHelper loadingHelper;
	private TitleBar titleBar;
	private Boolean switchreq;
	private Button cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = this;
		switchreq = true;
		setContentView(R.layout.activity_searchschool);
		titleBar = (TitleBar) findViewById(R.id.school_search_titlebar);
		titleBar.setLeftIcon(R.drawable.x);
		titleBar.setTitle("查询学校");

		titleBar.setTitleClickListener(this);

		loadingHelper = new LoadingHelper(
				findViewById(R.id.loading_prompt_linear),
				findViewById(R.id.loading_empty_prompt_linear));

		search_view = (EditText) findViewById(R.id.search_view);
		/*
		 * search_view.setInputType(InputType.TYPE_CLASS_TEXT |
		 * InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS |
		 * InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
		 */

		// search_view.setInputType(InputType.TYPE_CLASS_TEXT
		// | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
		// | InputType.TYPE_DATETIME_VARIATION_NORMAL);

		// 添加登陆界面传来的学校名称到搜索中
		String school_name = getIntent().getStringExtra("school_name");
		if (school_name != null) {
			search_view.setText(school_name);
		}

		ivDeleteText = (ImageView) findViewById(R.id.ivDeleteText);
		ivDeleteText.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				search_view.setText("");
				switchreq = true;
			}
		});

		search_list = (ListView) findViewById(R.id.search_list);
		search_view.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				Log.i("Lichg", arg0.toString());
				String text = arg0.toString();

				ArrayList<String> textList = new ArrayList<String>();
				char input[] = null;
				input = text.trim().toCharArray();
				int j = input.length;
				System.out.println("input" + j);

				latestsearch = arg0.toString().replace(" ", "");
				if (switchreq == true) {
					switchreq = false;
					searchSchool(latestsearch);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (arg0.length() == 0) {
					ivDeleteText.setVisibility(View.GONE);
					switchreq = true;
				} else {
					ivDeleteText.setVisibility(View.VISIBLE);
				}
			}
		});
		cancel = (Button) findViewById(R.id.delete);
		cancel.setOnClickListener((OnClickListener) new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (view.getId() == R.id.delete) {
					Login_school_config loginschoolconfig = new Login_school_config();
					loginschoolconfig.setSchoolId(0);
					loginschoolconfig.setSchoolName("");
					Intent it = new Intent(context, LoginActivity.class);
					Bundle v_it = new Bundle();
					v_it.putSerializable("schoolconfig", loginschoolconfig);
					it.putExtras(v_it);
					setResult(LoginActivity.NOTICE_CODE_SCHOOL_ACTIVITY_RESULT,
							it);
					finish();
				}
			}
		});
	}

	public void searchSchool(String s) {
		Log.i("Lichg", "search:" + s);
		latestsearching = s;
		initemptylist();

		if (s.length() > 0) {
			loadingHelper.ShowLoading();
		} else {
			switchreq = true;
			loadingHelper.HideLoading(View.INVISIBLE);
			return;
		}
		/*
		 * JSONObject param = new JSONObject(); try { param.put("type",
		 * "GetSchoolsReqInfo"); param.put("schoolNameRegular", s); } catch
		 * (JSONException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		// System.out.println(param);
		String url = Constants.LOGIN_SEARCHSCHOOL_DM_URL + s;
		MyHttpUtil httpUtil = MyHttpUtil.getInstance(context);

		/*
		 * httpUtil.request(Constant.LOGIN_SEARCHSCHOOL_URL, param, new
		 * MyHttpUtil.HttpCallback() {
		 * 
		 * @Override public void success(String result) { updatelist(result); }
		 * 
		 * @Override public void error(int state, String errorMsg) { switchreq =
		 * true; // //loadingHelper.ShowError(errorMsg); } });
		 */
		httpUtil.requestMethdget(url, new MyHttpUtil.HttpCallback() {
			@Override
			public void success(String result) {
				// updatelist(result);
				uodateSchoolist(result);
				// String filename = "xydlogin.txt";
				// writeFileData(filename, result);
			}

			@Override
			public void error(int state, String errorMsg) {
				switchreq = true;
				// loadingHelper.ShowError(errorMsg);
				Toast.makeText(context, "请检查网络！", Toast.LENGTH_SHORT).show();
				loadingHelper.HideLoading(8);
			}
		});
	}

	private void updatelist(String school) {
		if (GsonUtil.checkJson(school)) {
			loadingHelper.HideLoading(View.INVISIBLE);

			loginschoolcon = JsonUtils.jsontoschoollist(school);
			Log.i("Lichg", loginschoolcon.toString());
			if (latestsearch.equals(loginschoolcon.getSchoolNameRegular())) {
				schoollist = loginschoolcon.getSchoollist();
				if (loginschoolcon.getResultCode() != 0) {
					loadingHelper.ShowError(loginschoolcon.getResultDesc());
				} else if ((schoollist == null || schoollist.size() == 0)
						&& loginschoolcon.getResultDesc().equals("成功")) {
					loadingHelper.ShowError(this
							.getString(R.string.s_loading_no_data));
				} else if (schoollist == null || schoollist.size() == 0) {
					loadingHelper.ShowError(loginschoolcon.getResultDesc());
				} else
					initlist();
				switchreq = true;
			} else {
				switchreq = false;
				searchSchool(latestsearch);
			}
		} else {
			loadingHelper.ShowError(this.getString(R.string.s_loading_no_data));
			switchreq = true;
		}
	}

	private void uodateSchoolist(String school) {
		try {
			JSONArray shooljsonarry = new JSONArray(school);
			if (shooljsonarry != null) {
				loadingHelper.HideLoading(View.INVISIBLE);
				if (latestsearch.equals(latestsearching)) {
					ArrayList<Login_school> loginschoollist = new ArrayList<Login_school>();
					for (int i = 0; i < shooljsonarry.length(); i++) {
						JSONObject objectlist = shooljsonarry.getJSONObject(i);
						int schoolId = objectlist.getInt("schoolId");
						int areaId = objectlist.getInt("areaId");
						String name = objectlist.getString("name");
						String areaName = objectlist.getString("areaName");
						int status = objectlist.getInt("status");
						Login_school loginschool = new Login_school(schoolId,
								areaId, name, areaName, status);
						loginschoollist.add(loginschool);
					}
					if (loginschoollist != null && loginschoollist.size() > 0) {
						schoollist = loginschoollist;
						initlist();
					} else {
						loadingHelper.ShowError(this
								.getString(R.string.s_loading_no_result));
					}
					switchreq = true;
				} else {
					switchreq = false;
					searchSchool(latestsearch);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			loadingHelper.ShowError(this
					.getString(R.string.s_loading_no_result));
			switchreq = true;
		}
	}

	private void initemptylist() {
		if (mData != null)
			mData.clear();
		mData = new ArrayList<Map<String, Object>>();
		schoollistadapter = new SimpleAdapter(UIUtils.getContext(), mData,
				R.layout.item_school, new String[] { "schoolname" },
				new int[] { R.id.schoolname });
		search_list.setAdapter(schoollistadapter);
	}

	private void initlist() {
		if (mData != null)
			mData.clear();
		mData = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < schoollist.size(); i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("schoolname", schoollist.get(i).getName());
			mData.add(item);
		}

		schoollistadapter = new SimpleAdapter(UIUtils.getContext(), mData,
				R.layout.item_school, new String[] { "schoolname" },
				new int[] { R.id.schoolname });
		search_list.setAdapter(schoollistadapter);
		search_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {
				loadingHelper.ShowLoading();
				itemclick(index);
			}
		});
	}

	private void itemclick(final int index) {

		/*
		 * JSONObject param = new JSONObject(); try { param.put("type",
		 * "GetSchoolConfigReqInfo"); param.put("schoolId",
		 * schoollist.get(index).getSchoolId()); } catch (JSONException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 * System.out.println(param); MyHttpUtil httpUtil =
		 * MyHttpUtil.getInstance(context);
		 * 
		 * httpUtil.request(Constant.LOGIN_SEARCHSCHOOLCONFIG_URL, param, new
		 * MyHttpUtil.HttpCallback() {
		 * 
		 * @Override public void success(String result) { Login_school_config
		 * loginschoolconfig = getSchoolCongfig(result); if (loginschoolconfig
		 * != null) { Intent it = new Intent(context, LoginActivity.class);
		 * Bundle v_it = new Bundle(); v_it.putSerializable("schoolconfig",
		 * loginschoolconfig); it.putExtras(v_it); setResult(
		 * LoginActivity.NOTICE_CODE_SCHOOL_ACTIVITY_RESULT, it); finish(); }
		 * else { loadingHelper.HideLoading(View.INVISIBLE);
		 * ToastUtils.show(UIUtils.getContext(), "学校信息有错误，请联系学校！"); } }
		 * 
		 * @Override public void error(int state, String errorMsg) {
		 * loadingHelper.HideLoading(View.INVISIBLE);
		 * ToastUtils.show(UIUtils.getContext(), "学校信息有错误，请联系学校！"); } });
		 */
		int schoolid = schoollist.get(index).getSchoolId();
		int appdid = 10003;
		String getshcoollisturl = Constants.GET_SCHOOL_SERVERLIST_DM + "?"
				+ "schoolId=" + schoolid + "&" + "appId=" + appdid;
		Log.e("getshcoollisturl", "getshcoollisturl:" + getshcoollisturl);
		MyHttpUtil httpUtil = MyHttpUtil.getInstance(context);
		httpUtil.requestMethdget(getshcoollisturl, new HttpCallback() {

			@Override
			public void success(String result) {
				// TODO Auto-generated method stub
				// Log.d("onitemclick", ""+result);
				String filename = "xydschoolclick.txt";
				writeFileData(filename, result);
				Login_school_config loginschoolconfig = JsonUtils
						.analys_Login_schconfig(result);
				System.out.println("Login_school_config.result" + result);
				System.out.println("Login_school_config" + loginschoolconfig);
				if (loginschoolconfig != null) {
					Intent it = new Intent(context, LoginActivity.class);
					Bundle v_it = new Bundle();
					v_it.putSerializable("schoolconfig", loginschoolconfig);
					it.putExtras(v_it);
					setResult(LoginActivity.NOTICE_CODE_SCHOOL_ACTIVITY_RESULT,
							it);
					finish();
				} else if (loginschoolconfig == null && result.equals("[]")) {
					Intent it = new Intent(context, LoginActivity.class);
					String schoolInfo = schoollist.get(index).getName();
					it.putExtra("schoolInfo", schoolInfo);
					startActivity(it);

				} else {
					loadingHelper.HideLoading(View.INVISIBLE);
					ToastUtils.show(UIUtils.getContext(), "学校信息有错误，请联系学校！");
				}
			}

			@Override
			public void error(int state, String errorMsg) {
				// TODO Auto-generated method stub
				loadingHelper.HideLoading(View.INVISIBLE);
				ToastUtils.show(UIUtils.getContext(), "学校信息有错误，请联系学校！");
			}
		});

	}

	public Login_school_config getSchoolCongfig(String s) {
		if (GsonUtil.checkJson(s)) {
			Login_school_config schoolconfg = JsonUtils.jsontoschoolconfig(s);
			return schoolconfg;
		} else {
			return null;
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
			return super.dispatchTouchEvent(ev);
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev)) {
			return true;
		}
		return onTouchEvent(ev);
	}

	public boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };
			// 获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	@Override
	public void leftClick() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void rightClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void titleClick() {
		// TODO Auto-generated method stub

	}

	public void writeFileData(String fileName, String message) {

		try {

			// //FileOutputStream fout =openFileOutput(fileName, MODE_PRIVATE);

			File file = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath(), fileName);
			FileOutputStream fout = new FileOutputStream(file);
			System.out.println(message);
			byte[] bytes = message.getBytes();
			System.out.println(bytes);
			fout.write(bytes);

			fout.close();

		}

		catch (Exception e) {

			e.printStackTrace();

		}
	}

	/**
	 * 友盟统计
	 */
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("LoginSearchSchoolActivity");
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("LoginSearchSchoolActivity");
		MobclickAgent.onPause(this);
	}
}