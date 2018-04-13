package com.xyd.student.xydexamanalysis.ui;

import com.umeng.analytics.MobclickAgent;
import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.Fragment.NoticeFragment;
import com.xyd.student.xydexamanalysis.entity.SingleExam;
import com.xyd.student.xydexamanalysis.entity.SingleNotice;
import com.xyd.student.xydexamanalysis.utils.ToastUtils;
import com.xyd.student.xydexamanalysis.utils.UIUtils;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PayDialog2Activity extends Activity implements OnClickListener {

	private Button mLaterLook, mNowLoook;
	private TextView mPhone, mCost, mName;
	private String phoneNumber;
	private TextView hoursTv, minutesTv, secondsTv;
	private long mHour;
	private long mMin;
	private long mSecond;// 小时,分钟,秒

	private String hours;
	private String mins;
	private String seconds;

	private boolean isRun = true;

	private Bundle bundle;
	private String chargeName;
	private int meId;
	private int seId;
	private double amount;
	private String meName;
	private String freeTime;
	private String noticetype;
	private SingleNotice singleNotice;
	private int position;
	private String fragmentType;
	private String studentId;
	private int schoolId;
	private String studentName;

	private String time;
	private String name;
	private String classId;
	private String examname;
	private String subject;
	private Context mContext;

	private Handler timeHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				computeTime();
				formatTime();
				if (mHour >= 0) {
					hoursTv.setText(hours);
					minutesTv.setText(mins);
					secondsTv.setText(seconds);
				}
			}
		}
	};

	/**
	 * 倒计时计算
	 */
	private void computeTime() {
		mSecond--;
		if (mSecond < 0) {
			mMin--;
			mSecond = 59;
			if (mMin < 0) {
				mMin = 59;
				mHour--;
				if (mHour < 0) {
					// 倒计时结束
					// Intent intent = new Intent(this, MainActivity.class);
					// startActivity(intent);
					isRun = false;
				}
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_dialog2);

		mContext = this;

		initValues();
		initView();
	}

	private void initValues() {

		this.bundle = this.getIntent().getExtras();
		chargeName = bundle.getString("chargeName");
		meId = bundle.getInt("meId");
		seId = bundle.getInt("seId");
		amount = bundle.getDouble("amount");
		meName = bundle.getString("meName");
		freeTime = bundle.getString("freeTime");

		fragmentType = this.getIntent().getStringExtra("fragmentType");
		noticetype = this.getIntent().getStringExtra("noticetype");
		time = this.getIntent().getStringExtra("time");
		if (fragmentType.equals("Exam")) {
			if (noticetype.equals("总成绩报告")) {
				studentId = this.getIntent().getStringExtra("studentId");
				schoolId = this.getIntent().getIntExtra("schoolId", 0);

				name = this.getIntent().getStringExtra("name");

			} else {
				studentId = this.getIntent().getStringExtra("studentId");
				schoolId = this.getIntent().getIntExtra("schoolId", 0);
				studentName = this.getIntent().getStringExtra("studentName");

				classId = this.getIntent().getStringExtra("classId");
				examname = this.getIntent().getStringExtra("examname");
				subject = this.getIntent().getStringExtra("subject");

			}

		} else if (fragmentType.equals("Notice")) {
			singleNotice = (SingleNotice) this.getIntent()
					.getSerializableExtra("singleNotice");
			position = this.getIntent().getIntExtra("position", -1);
		}

		Log.i("lxw", "chargeName" + chargeName + "meId" + meId + "seId" + seId
				+ "amount" + amount + "meName" + meName + "freeTime" + freeTime);
		initTime(freeTime);

	}

	private void initTime(String freeTime2) {
		int time = Integer.parseInt(freeTime2);
		mHour = time / 3600;
		mMin = (time - (mHour * 3600)) / 60;
		mSecond = time - (mHour * 3600) - (mMin * 60);

		Log.i("倒计时时间", mHour + ":" + mMin + ":" + mSecond);
	}

	private void initView() {
		mPhone = (TextView) findViewById(R.id.pay_course_phone);
		mCost = (TextView) findViewById(R.id.pay_course_cost);
		mName = (TextView) findViewById(R.id.pay_course_name);
		mLaterLook = (Button) findViewById(R.id.later_look);
		mNowLoook = (Button) findViewById(R.id.immediately_look);
		hoursTv = (TextView) findViewById(R.id.pay_course_time_hour);
		minutesTv = (TextView) findViewById(R.id.pay_course_time_minute);
		secondsTv = (TextView) findViewById(R.id.pay_course_time_second);

		mName.setText(chargeName);
		mCost.setText("￥" + amount);
		formatTime();

		hoursTv.setText(hours);
		minutesTv.setText(mins);
		secondsTv.setText(seconds);

		startRun();

		phoneNumber = mPhone.getText().toString().trim();

		mPhone.setOnClickListener(this);
		mLaterLook.setOnClickListener(this);
		mNowLoook.setOnClickListener(this);
	}

	private void formatTime() {
		if (mHour < 10) {
			hours = "0" + mHour;
		} else {
			hours = mHour + "";
		}
		if (mMin < 10) {
			mins = "0" + mMin;
		} else {
			mins = mMin + "";
		}
		if (mSecond < 10) {
			seconds = "0" + mSecond;
		} else {
			seconds = mSecond + "";
		}
	}

	/**
	 * 开启倒计时
	 */
	private void startRun() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (isRun) {
					try {
						Thread.sleep(1000); // sleep 1000ms
						Message message = Message.obtain();
						message.what = 1;
						timeHandler.sendMessage(message);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pay_course_phone:
			Intent i = new Intent("android.intent.action.CALL",
					Uri.parse("tel:" + phoneNumber));
			startActivity(i);
			break;
		case R.id.later_look:
			MobclickAgent.onEvent(mContext, "view_later");
			if (mHour < 0) {
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
			} else {
				Intent intent_cancel = new Intent(this, PayCancelActivity.class);
				Bundle p_bundle_cancel = new Bundle();
				p_bundle_cancel.putString("chargeName", chargeName);
				p_bundle_cancel.putString("meName", meName);
				intent_cancel.putExtras(p_bundle_cancel);
				startActivity(intent_cancel);
				finish();
			}
			break;
		case R.id.immediately_look:
			if (mHour < 0) {
				MobclickAgent.onEvent(mContext, "view_now_withoutpay");
				if (noticetype.equals("总成绩报告")) {
					if (fragmentType.equals("Exam")) {
						Intent intent = new Intent(UIUtils.getContext(),
								GradeReportActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

						Bundle bundle = new Bundle();
						bundle.putString("time", time);
						bundle.putString("name", name);
						bundle.putInt("meId", meId);
						bundle.putString("studentId", studentId);
						bundle.putInt("schoolId", schoolId);
						System.out.println(bundle);
						intent.putExtras(bundle);
						startActivity(intent);
						finish();
					} else {
						Intent g_intent = new Intent(UIUtils.getContext(),
								GradeReportActivity.class);
						Bundle grade_bundle = new Bundle();

						grade_bundle.putString("name",
								singleNotice.getN_exam_name());
						grade_bundle.putString("time",
								singleNotice.getN_meDate());
						grade_bundle.putInt("meId", singleNotice.getN_meid());
						grade_bundle.putInt("schoolId",
								singleNotice.getN_schoolId());
						grade_bundle.putString("studentId",
								singleNotice.getN_studentId());
						grade_bundle.putInt("msgId", singleNotice.getN_id());
						grade_bundle.putInt("isReaded",
								singleNotice.getN_isread());
						grade_bundle.putInt("currentindex", position);
						g_intent.putExtras(grade_bundle);

						startActivity(g_intent);
						finish();
					}

				} else if (noticetype.equals("单科成绩报告")) {
					if (fragmentType.equals("Exam")) {

						Intent intent = new Intent(UIUtils.getContext(),
								SingReportActivity.class);

						Bundle v_intent = new Bundle();
						v_intent.putString("studentName", studentName);
						v_intent.putString("studentId", studentId);
						v_intent.putString("classId", classId);
						v_intent.putInt("schoolId", schoolId);
						v_intent.putInt("seId", seId);
						v_intent.putInt("meId", meId);
						v_intent.putInt("currentindex", -1);
						v_intent.putString("examname", examname);
						v_intent.putString("subject", subject);
						v_intent.putString("time", time);
						intent.putExtras(v_intent);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						finish();
					} else {

						Intent intent = new Intent(UIUtils.getContext(),
								SingReportActivity.class);
						Bundle v_intent = new Bundle();
						v_intent.putString("studentName",
								singleNotice.getN_studentName());
						v_intent.putString("studentId",
								singleNotice.getN_studentId());
						v_intent.putString("classId",
								singleNotice.getN_classId());
						v_intent.putInt("schoolId",
								singleNotice.getN_schoolId());
						v_intent.putInt("seId", singleNotice.getN_seId());
						v_intent.putInt("meId", singleNotice.getN_meid());
						v_intent.putInt("currentindex", position);
						v_intent.putString("examname",
								singleNotice.getN_exam_name());
						v_intent.putString("subject",
								singleNotice.getN_subject());
						v_intent.putString("time", singleNotice.getN_meDate());
						v_intent.putInt("msgId", singleNotice.getN_id());
						v_intent.putInt("isReaded", singleNotice.getN_isread());

						intent.putExtras(v_intent);
						startActivity(intent);
						finish();
					}

				} else if (noticetype.equals("试题讲解")) {
					Intent e_intent = new Intent(UIUtils.getContext(),
							ExplainQuestionActivity.class);
					Bundle ex_bundle = new Bundle();

					ex_bundle.putString("name", singleNotice.getN_exam_name());
					ex_bundle.putString("time", singleNotice.getN_meDate());
					ex_bundle.putInt("meId", singleNotice.getN_meid());
					ex_bundle.putInt("seId", singleNotice.getN_seId());
					ex_bundle.putString("schoolId",
							singleNotice.getN_schoolId() + "");
					ex_bundle.putString("studentId",
							singleNotice.getN_studentId());
					ex_bundle.putInt("msgType", 2);
					ex_bundle.putInt("msgId", singleNotice.getN_id());
					ex_bundle.putInt("isReaded", singleNotice.getN_isread());
					ex_bundle.putInt("currentindex", position);
					e_intent.putExtras(ex_bundle);

					startActivity(e_intent);
					finish();

				} else if (noticetype.equals("优秀答案")) {
					Intent e_intent = new Intent(UIUtils.getContext(),
							ExplainQuestionActivity.class);
					Bundle ex_bundle = new Bundle();

					ex_bundle.putString("name", singleNotice.getN_exam_name());
					ex_bundle.putString("time", singleNotice.getN_meDate());
					ex_bundle.putInt("meId", singleNotice.getN_meid());
					ex_bundle.putInt("seId", singleNotice.getN_seId());
					ex_bundle.putString("schoolId",
							singleNotice.getN_schoolId() + "");
					ex_bundle.putString("studentId",
							singleNotice.getN_studentId());
					ex_bundle.putInt("msgType", 3);
					ex_bundle.putInt("msgId", singleNotice.getN_id());
					ex_bundle.putInt("isReaded", singleNotice.getN_isread());
					ex_bundle.putInt("currentindex", position);
					e_intent.putExtras(ex_bundle);

					startActivity(e_intent);
					finish();

				} else if (noticetype.equals("失分题")) {
					Intent e_intent = new Intent(UIUtils.getContext(),
							ExplainQuestionActivity.class);
					Bundle ex_bundle = new Bundle();

					ex_bundle.putString("name", singleNotice.getN_exam_name());
					ex_bundle.putString("time", singleNotice.getN_meDate());
					ex_bundle.putInt("meId", singleNotice.getN_meid());
					ex_bundle.putInt("seId", singleNotice.getN_seId());
					ex_bundle.putString("schoolId",
							singleNotice.getN_schoolId() + "");
					ex_bundle.putString("studentId",
							singleNotice.getN_studentId());
					ex_bundle.putInt("msgType", 4);
					ex_bundle.putInt("msgId", singleNotice.getN_id());
					ex_bundle.putInt("isReaded", singleNotice.getN_isread());
					ex_bundle.putInt("currentindex", position);
					e_intent.putExtras(ex_bundle);

					startActivity(e_intent);
					finish();

				}

			} else {
				MobclickAgent.onEvent(mContext, "view_now_pay");
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
			}

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
		MobclickAgent.onPageStart("PayDialog2Activity");
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("PayDialog2Activity");
		MobclickAgent.onPause(this);
	}
}
