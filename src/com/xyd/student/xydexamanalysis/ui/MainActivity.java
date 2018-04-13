package com.xyd.student.xydexamanalysis.ui;

/**
 * Created by Lichg.
 */

import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

import com.umeng.analytics.MobclickAgent;
import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.Fragment.FragmentFactory;
import com.xyd.student.xydexamanalysis.application.BaseApplication;
import com.xyd.student.xydexamanalysis.constant.Constants;
import com.xyd.student.xydexamanalysis.utils.JsonUtils;
import com.xyd.student.xydexamanalysis.utils.LoginUtils;
import com.xyd.student.xydexamanalysis.utils.ToastUtils;
import com.xyd.student.xydexamanalysis.utils.UpdateManager;
import com.xyd.student.xydexamanalysis.view.LazyViewPager;
import com.xyd.student.xydexamanalysis.view.Menu_Utils;
import com.xyd.student.xydexamanalysis.view.TitleBar;

public class MainActivity extends FragmentActivity implements
		TitleBar.TitleOnClickListener {

	private ViewPagerAdapter viewPagerAdapter;
	private LazyViewPager pager;
	private Menu_Utils menu_Utils;
	private TitleBar titleBar;
	private Context context;
	private static DefaultHttpClient client;
	private static HttpResponse response;
	private int forceFlag;
	private String versionName;
	private String versionMark;
	private String versionUrl;
	private double versionCode;
	private double versionSize;
	private int nowCode;
	private UpdateManager updateManager;

	private String UPDATE_URL;

	private BaseApplication baseApplication;

	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Bundle bundle = msg.getData();
				String jsonresult = bundle.getString("jsonresult");
				JSONObject object;
				try {
					object = new JSONObject(jsonresult);
					JSONObject jsonObject = object.getJSONObject("versionInfo");
					forceFlag = jsonObject.getInt("forceFlag");
					versionName = jsonObject.getString("versionName");
					versionMark = jsonObject.getString("versionMark");
					versionSize = jsonObject.getDouble("versionSize");
					versionUrl = jsonObject.getString("versionUrl");
					versionCode = jsonObject.getDouble("versionCode");
					baseApplication = (BaseApplication) getApplication();
					baseApplication.setVersionName(versionName);
					baseApplication.setVersionCode(versionCode);
					baseApplication.setVersionMark(versionMark);
					baseApplication.setVersionSize(versionSize);
					baseApplication.setVersionUrl(versionUrl);
					nowCode = updateManager.getVersionCode(MainActivity.this);
					if (versionCode > nowCode) {
						if (forceFlag == 0) {
							// updateManager.showNoticeDialog(versionMark,
							// versionUrl);
							Intent intent = new Intent(context,
									DialogActivity.class);
							intent.putExtra("versionName", versionName);
							intent.putExtra("versionMark", versionMark);
							intent.putExtra("versionUrl", versionUrl);
							intent.putExtra("versionSize", versionSize);
							startActivity(intent);
						} else {
							updateManager.showNoticeDialog_must(versionMark,
									versionUrl);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_main);
		pager = (LazyViewPager) findViewById(R.id.pager);
		menu_Utils = new Menu_Utils(this);
		pager.setCanScroll(true);
		viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(viewPagerAdapter);
		pager.setOnPageChangeListener(new MyOnPageChangeListener());
		menu_Utils
				.setOnPageChangeListener(new Menu_Utils.onPageChangeListener() {
					@Override
					public void onPageChange(int position) {
						pager.setCurrentItem(position, true);
					}
				});

		titleBar = (TitleBar) findViewById(R.id.title_bar);
		titleBar.getTitle().setCompoundDrawables(null, null, null, null);
		titleBar.setTitle(this.getString(R.string.s_exam));
		titleBar.setTitleClickListener(this);
		updateManager = new UpdateManager(this);
		update();
	}

	private void update() {
		// TODO Auto-generated method stub
		SharedPreferences msharedPreferences = this.getSharedPreferences(
				Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);

		UPDATE_URL = msharedPreferences.getString("ReportServUrl", "");
		// System.out.println("UPDATE_URL" + UPDATE_URL);
		new Thread(new Runnable() {
			@Override
			public void run() {

				if (UPDATE_URL.equals("")) {
					// ToastUtils.show(this, "获取服务器地址错误，请重新登录");
					// finish();
					return;
				} else {
					UPDATE_URL = UPDATE_URL + "sac/getversion";
				}
				// Log.i("lxw", "UPDATE_URL" + UPDATE_URL);
				String jsonresult = getUpdateResult(UPDATE_URL, "get");
				// Log.i("lxw", "getUpdateResult" + jsonresult);
				if (jsonresult != null) {
					int resultCode = JsonUtils.getResultCode(jsonresult);
					if (resultCode == 0) {
						Message message = new Message();
						message.what = 1;
						Bundle bundle = new Bundle();
						bundle.putString("jsonresult", jsonresult);
						message.setData(bundle);
						handler.sendMessage(message);
					}
				}
			}

		}).start();

	}

	private String getUpdateResult(String urlsString, String method) {
		// TODO Auto-generated method stub
		URL url = null;
		try {
			url = new URL(urlsString);
			LoginUtils login = new LoginUtils();
			// 得到一个HttpClient对象
			client = new DefaultHttpClient();

			client.getCredentialsProvider().setCredentials(
					new AuthScope(url.getHost(), url.getPort(),
							AuthScope.ANY_REALM, AuthPolicy.DIGEST),

					new UsernamePasswordCredentials(login.getLoginUser(), login
							.getLoginPw()));
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					10 * 1000);

			// get方法
			if (method.equalsIgnoreCase("get")) {
				HttpGet getRequest = new HttpGet(urlsString);
				// 执行get请求
				response = client.execute(getRequest);
			}
			// 判断响应码
			if (response.getStatusLine().getStatusCode() == 200) {
				// 得到响应实体
				HttpEntity entity = response.getEntity();
				// 将响应实体按照utf-8的编码格式转化成字符串
				return EntityUtils.toString(entity, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void leftClick() {
		// UIUtils.showToastSafe("返回");
	}

	@Override
	public void rightClick() {

	}

	@Override
	public void titleClick() {

	}

	private void setTitleString(int position) {
		if (null == titleBar)
			titleBar = (TitleBar) findViewById(R.id.title_bar);
		switch (position) {
		// case 0:
		// titleBar.setTitle(this.getString(R.string.s_notice));
		// break;
		case 0:
			titleBar.setTitle(this.getString(R.string.s_exam));
			break;
		case 1:
			titleBar.setTitle(this.getString(R.string.s_weike));
			break;
		case 2:
			titleBar.setTitle(this.getString(R.string.s_owner));
			break;
		}
	}

	private class MyOnPageChangeListener implements
			LazyViewPager.OnPageChangeListener {
		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			menu_Utils.setPosition(position);
			setTitleString(position);
			FragmentFactory.flushitem(position);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}
	}

	private class ViewPagerAdapter extends FragmentStatePagerAdapter {
		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// Log.i("lxw", "ViewPagerAdapter-getItem" + position);
			return FragmentFactory.createFragment(position, context);
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// super.destroyItem(container, position, object);
		}
	}

	private long time = 0;

	@Override
	public void onBackPressed() {
		long now_time = System.currentTimeMillis();
		if (now_time - time > 2000) {
			time = now_time;
			ToastUtils.show(MainActivity.this, "再次点击返回键,系统将退出");
			// UIUtils.showToastSafe("再次点击返回键,系统将退出");
		} else {
			exit();
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		initView();
	}

	public void setBadge(int num) {
		menu_Utils.setDisplayNum(num);
	}

	public void exit() {

		MobclickAgent.onKillProcess(context);
		this.finish();
		System.exit(0);
	}

	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
		MobclickAgent.onPause(this);
	}

}
