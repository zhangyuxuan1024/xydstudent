package com.xyd.student.xydexamanalysis.ui;

import com.umeng.analytics.MobclickAgent;
import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.view.TitleBar;
import com.xyd.student.xydexamanalysis.view.TitleBar.TitleOnClickListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class PayCancelActivity extends Activity implements
		TitleOnClickListener, OnClickListener {

	private TitleBar titleBar;
	private TextView tv_course_name, tv_exam_name;
	private ImageButton btn_cancel_cancel;
	private Bundle bundle;
	private String chargeName;
	private String meName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pay_cancel);

		titleBar = (TitleBar) findViewById(R.id.pay_cancel_title);
		titleBar.setLeftIcon(R.drawable.x);
		titleBar.setTitle("取消支付");
		titleBar.setTitleClickListener(this);

		initValues();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		tv_course_name = (TextView) this.findViewById(R.id.tv_course_name);
		tv_exam_name = (TextView) this.findViewById(R.id.tv_exam_name);
		btn_cancel_cancel = (ImageButton) this
				.findViewById(R.id.btn_cancel_cancel);

		tv_exam_name.setText(meName);
		tv_course_name.setText("(" + chargeName + ")");

		btn_cancel_cancel.setOnClickListener(this);

	}

	private void initValues() {
		// TODO Auto-generated method stub
		this.bundle = this.getIntent().getExtras();
		chargeName = bundle.getString("chargeName");
		meName = bundle.getString("meName");

	}

	@Override
	public void leftClick() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_cancel_cancel:
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();

			break;

		default:
			break;
		}

	}

	/**
	 * 友盟统计
	 */
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("PayCancelActivity");
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("PayCancelActivity");
		MobclickAgent.onPause(this);
	}

}
