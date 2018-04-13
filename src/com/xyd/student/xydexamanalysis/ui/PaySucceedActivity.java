package com.xyd.student.xydexamanalysis.ui;

import com.umeng.analytics.MobclickAgent;
import com.xyd.student.xydexamanalysis.view.TitleBar;
import com.xyd.student.xydexamanalysis.view.TitleBar.TitleOnClickListener;

import com.xyd.student.xydexamanalysis.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class PaySucceedActivity extends Activity implements
		TitleOnClickListener, OnClickListener {

	private TitleBar titleBar;
	private ImageButton btn_succeed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pay_succeed);

		titleBar = (TitleBar) findViewById(R.id.pay_succeed_title);
		titleBar.setLeftIcon(R.drawable.x);
		titleBar.setTitle("支付成功");
		titleBar.setTitleClickListener(this);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		btn_succeed = (ImageButton) this.findViewById(R.id.btn_succeed);

		btn_succeed.setOnClickListener(this);

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
		case R.id.btn_succeed:

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
		MobclickAgent.onPageStart("PaySucceedActivity");
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("PaySucceedActivity");
		MobclickAgent.onPause(this);
	}

}
