package com.xyd.student.xydexamanalysis.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.xyd.student.xydexamanalysis.R;

public class PayDialogActivity extends Activity implements OnClickListener {

	private TextView tv_course, tv_money;
	private ImageButton btn_cancel, btn_ensure;
	private Bundle bundle;
	private String chargeName;
	private int meId;
	private int seId;
	private double amount;
	private String meName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pay_dialog);
		initValues();
		initView();
	}

	private void initValues() {
		// TODO Auto-generated method stub
		this.bundle = this.getIntent().getExtras();
		chargeName = bundle.getString("chargeName");
		meId = bundle.getInt("meId");
		seId = bundle.getInt("seId");
		amount = bundle.getDouble("amount");
		meName = bundle.getString("meName");
		Log.i("lxw", "chargeName" + chargeName + "meId" + meId + "seId" + seId
				+ "amount" + amount + "meName" + meName);

	}

	private void initView() {
		// TODO Auto-generated method stub
		tv_course = (TextView) this.findViewById(R.id.pay_course);
		tv_money = (TextView) this.findViewById(R.id.pay_money);
		btn_cancel = (ImageButton) this.findViewById(R.id.btn_cancel);
		btn_ensure = (ImageButton) this.findViewById(R.id.btn_ensure);

		tv_course.setText(chargeName);
		tv_money.setText("￥" + amount);

		btn_ensure.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_cancel:
			Intent intent_cancel = new Intent(this, PayCancelActivity.class);
			Bundle p_bundle_cancel = new Bundle();
			p_bundle_cancel.putString("chargeName", chargeName);
			p_bundle_cancel.putString("meName", meName);
			intent_cancel.putExtras(p_bundle_cancel);
			startActivity(intent_cancel);
			finish();

			break;

		case R.id.btn_ensure:
			Intent intent_ensure = new Intent(this, PayEnsureActivity.class);
			Bundle p_bundle_ensure = new Bundle();
			p_bundle_ensure.putInt("meId", meId);
			p_bundle_ensure.putInt("seId", seId);
			p_bundle_ensure.putString("chargeName", chargeName);
			p_bundle_ensure.putDouble("amount", amount);
			p_bundle_ensure.putString("meName", meName);
			p_bundle_ensure.putString("courseName",
					bundle.getString("courseName"));
			intent_ensure.putExtras(p_bundle_ensure);
			startActivity(intent_ensure);
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
		MobclickAgent.onPageStart("PayDialogActivity");
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("PayDialogActivity");
		MobclickAgent.onPause(this);
	}

}
