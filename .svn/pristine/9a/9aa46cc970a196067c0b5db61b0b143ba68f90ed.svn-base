package com.xyd.student.xydexamanalysis.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.xyd.student.xydexamanalysis.R;

public class DialogActivity extends Activity implements OnClickListener {
	private TextView tv_later, tv_now, tv_content, tv_dialog_latest_version,
			tv_dialog_latest_version_size;
	private String versionName, versionMark, versionUrl;
	private double versionSize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dialog);
		init();
		getValues();
		tv_content.setText(versionMark);
		tv_content.setMovementMethod(new ScrollingMovementMethod());
		tv_dialog_latest_version.setText(versionName);
		tv_dialog_latest_version_size.setText(versionSize + "M");
	}

	public void getValues() {
		Intent intent = getIntent();
		versionName = intent.getStringExtra("versionName");
		versionMark = intent.getStringExtra("versionMark");
		versionUrl = intent.getStringExtra("versionUrl");
		versionSize = intent.getDoubleExtra("versionSize", 0.00);
	}

	public void init() {
		tv_later = (TextView) findViewById(R.id.dialog_later);
		tv_now = (TextView) findViewById(R.id.dialog_now);
		tv_content = (TextView) findViewById(R.id.dialog_content);
		tv_dialog_latest_version = (TextView) findViewById(R.id.dialog_latest_version);
		tv_dialog_latest_version_size = (TextView) findViewById(R.id.dialog_latest_version_size);
		tv_later.setOnClickListener(this);
		tv_now.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.dialog_later:
			DialogActivity.this.finish();
			break;

		case R.id.dialog_now:
			Intent intent = new Intent(DialogActivity.this,
					Update2Activity.class);
			intent.putExtra("versionUrl", versionUrl);
			startActivity(intent);
			DialogActivity.this.finish();
			break;
		}
	}

	/**
	 * 友盟统计
	 */
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("DialogActivity");
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("DialogActivity");
		MobclickAgent.onPause(this);
	}
}
