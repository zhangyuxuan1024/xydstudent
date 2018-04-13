package com.xyd.student.xydexamanalysis.ui;

import java.io.InputStream;

import com.umeng.analytics.MobclickAgent;
import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.application.BaseApplication;
import com.xyd.student.xydexamanalysis.utils.UpdateManager;
import com.xyd.student.xydexamanalysis.view.TitleBar;
import com.xyd.student.xydexamanalysis.view.TitleBar.TitleOnClickListener;
import com.xyd.student.xydexamanalysis.view.MyImageView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UpdateActivity extends Activity implements OnClickListener,
		TitleOnClickListener {
	private TitleBar titleBar;
	private TextView preVersion, newVersion, verSize;
	private Context mContext;
	private Button upload;
	private boolean hasNew = false;
	private LinearLayout update_ll;
	private LinearLayout scrollV;
	private UpdateManager updateManager;
	private int nowCode;
	private double versionCode;
	private double versionSize;
	private String versionMark;
	private String versionUrl;
	private String versionName;
	private MyImageView myIv1, myIv2, myIv3;

	private BaseApplication baseApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.update_layout);
		mContext = this;
		updateManager = new UpdateManager(mContext);
		titleBar = (TitleBar) findViewById(R.id.title_bar);
		titleBar.setTitle("关于心意答");
		titleBar.setLeftIcon(R.drawable.x);
		titleBar.setTitleClickListener(this);

		upload = (Button) findViewById(R.id.upload);
		update_ll = (LinearLayout) findViewById(R.id.update_ll);
		scrollV = (LinearLayout) findViewById(R.id.scrollll);
		scrollV.setOnClickListener(this);
		upload.setOnClickListener(this);
		preVersion = (TextView) findViewById(R.id.version);
		newVersion = (TextView) findViewById(R.id.newversion);
		verSize = (TextView) findViewById(R.id.versionSize);
		preVersion.setText("V" + updateManager.getCurrentVersionName(mContext));
		myIv1 = (MyImageView) findViewById(R.id.myIV1);
		myIv2 = (MyImageView) findViewById(R.id.myIV2);
		myIv3 = (MyImageView) findViewById(R.id.myIV3);

		Bitmap bitmap1 = readBitMap(UpdateActivity.this, R.drawable.guide1);
		Bitmap bitmap2 = readBitMap(UpdateActivity.this, R.drawable.guide2);
		Bitmap bitmap3 = readBitMap(UpdateActivity.this, R.drawable.guide3);
		myIv1.setImageBitmap(bitmap1);
		myIv2.setImageBitmap(bitmap2);
		myIv3.setImageBitmap(bitmap3);

		baseApplication = (BaseApplication) getApplication();
		versionName = baseApplication.getVersionName();
		versionCode = baseApplication.getVersionCode();
		versionSize = baseApplication.getVersionSize();
		versionMark = baseApplication.getVersionMark();
		versionUrl = baseApplication.getVersionUrl();
		nowCode = updateManager.getVersionCode(UpdateActivity.this);
		if (versionCode > nowCode) {
			hasNew = true;
			update_ll.setVisibility(View.VISIBLE);
			newVersion.setText("V" + versionCode + "");
			verSize.setText("大小" + versionSize + "M");
		} else {
			hasNew = false;
			update_ll.setVisibility(View.GONE);
		}

		/*
		 * SharedPreferences msharedPreferences = this.getSharedPreferences(
		 * Constant.SHARED_PREFERENCES, Context.MODE_PRIVATE); update_url =
		 * msharedPreferences.getString("ReportServUrl", "");
		 * if(update_url.equals("")){ ToastUtils.show(this, "获取服务器地址错误，请重新登录");
		 * finish(); return ; }else{ update_url= update_url+"sac/getversion"; }
		 */

	}

	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.upload:
			// updateManager.showNoticeDialog(versionMark, versionUrl);
			Intent intent2 = new Intent(UpdateActivity.this,
					DialogActivity.class);
			intent2.putExtra("versionMark", versionMark);
			intent2.putExtra("versionUrl", versionUrl);
			intent2.putExtra("versionName", versionName);
			intent2.putExtra("versionSize", versionSize);
			startActivity(intent2);
			break;
		case R.id.scrollll:
			Intent intent = new Intent(UpdateActivity.this,
					VersionIntroduction.class);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	@Override
	public void leftClick() {
		// TODO Auto-generated method stub
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

	/**
	 * 友盟统计
	 */
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("UpdateActivity");
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("UpdateActivity");
		MobclickAgent.onPause(this);
	}
}
