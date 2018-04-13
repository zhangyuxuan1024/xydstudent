package com.xyd.student.xydexamanalysis.ui;

import org.json.JSONObject;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.constant.Constants;
import com.xyd.student.xydexamanalysis.entity.PayOrder;
import com.xyd.student.xydexamanalysis.utils.GsonUtil;
import com.xyd.student.xydexamanalysis.utils.JsonUtils;
import com.xyd.student.xydexamanalysis.utils.LoginUtils;
import com.xyd.student.xydexamanalysis.utils.MyHttpUtil;
import com.xyd.student.xydexamanalysis.utils.SubjectIconUtil;
import com.xyd.student.xydexamanalysis.utils.ToastUtils;
import com.xyd.student.xydexamanalysis.utils.UIUtils;
import com.xyd.student.xydexamanalysis.view.LoadingHelper;
import com.xyd.student.xydexamanalysis.view.LoadingListener;
import com.xyd.student.xydexamanalysis.view.TitleBar;
import com.xyd.student.xydexamanalysis.view.TitleBar.TitleOnClickListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PayEnsureActivity extends Activity implements
		TitleOnClickListener, OnClickListener, LoadingListener {

	private LoadingHelper loadingHelper;
	private boolean loading = false;
	private TitleBar titleBar;
	private ImageView image_course;
	private TextView tv_pay_course_name, tv_pay_exam_name, tv_pay_money;
	private LinearLayout ll_pay, ll_wxpay;
	private Bundle bundle;
	private String chargeName;
	private int meId;
	private int seId;
	private Double amount;
	private String meName;
	private LoginUtils login;
	private JSONObject param;
	private PayOrder payOrder;
	private String orderId;
	private String payOrderUrl = Constants.PAY_ORDER_URL;
	private String payWXUrl = Constants.WX_PAY_URL;
	private static String TAG = "PayEnsureActivity";

	private int totalFee;

	private IWXAPI api;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pay_ensure);

		titleBar = (TitleBar) findViewById(R.id.pay_ensure_title);
		titleBar.setLeftIcon(R.drawable.x);
		titleBar.setTitle("支付");
		titleBar.setTitleClickListener(this);
		login = new LoginUtils();
		loadingHelper = new LoadingHelper(
				findViewById(R.id.loading_prompt_linear),
				findViewById(R.id.loading_empty_prompt_linear));
		loadingHelper.ShowLoading();
		loadingHelper.SetListener(this);
		initValues();
		readData();
		initView();
	}

	/**
	 * 请求数据
	 */
	private void readData() {
		// TODO Auto-generated method stub
		// 支付宝
		param = new JSONObject();

		try {
			param.put("type", "GetPayOrderIdReqInfo");
			param.put("userId", login.getLoginUserId());
			param.put("meId", meId);
			param.put("seId", seId);
			param.put("amount", amount);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i("lxw", "PayEnsureActivity-param=" + param);
		Log.i("lxw", "payOrderUrl=" + payOrderUrl);
		MyHttpUtil httpUtil = MyHttpUtil.getInstance(this);
		httpUtil.request(payOrderUrl, param, new MyHttpUtil.HttpCallback() {
			@Override
			public void success(String result) {
				Log.i("lxw", "payOrder sucessfully!!!!!!!" + result);
				getInfoSuccess(result);
			}

			@Override
			public void error(int state, String errorMsg) {
				Log.i("lxw", "payOrder faild!!!!!!!");
				loadingHelper.ShowError(errorMsg);
			}
		});

	}

	private void getInfoSuccess(String result) {

		if (GsonUtil.checkJson(result)) {
			loadingHelper.HideLoading(View.INVISIBLE);
			payOrder = JsonUtils.jsontoPayOrder(result);
			Log.i("lxw", "orderId" + payOrder.getOrderId());
			if (payOrder != null) {
				orderId = payOrder.getOrderId();

			}
		} else {
			loadingHelper.ShowError(this.getString(R.string.s_loading_no_data));
		}

	}

	private void getWXInfo(String orderId2) {

		loadingHelper.ShowLoading();
		loadingHelper.SetListener(this);
		param = new JSONObject();

		try {
			param.put("type", "WCPayGetPrePayIdReqInfo");
			param.put("body", chargeName);
			param.put("outTradeNo", orderId2);
			param.put("totalFee", totalFee);
			param.put("tradeType", "APP");
			param.put("platformType", "android");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		Log.i("lxw", "PayEnsureActivity-param_WX=" + param);
		Log.i("lxw", "payWXUrl=" + payWXUrl);
		MyHttpUtil httpUtil = MyHttpUtil.getInstance(this);
		httpUtil.request(payWXUrl, param, new MyHttpUtil.HttpCallback() {
			@Override
			public void success(String result) {
				Log.i("lxw", "WXpayOrder sucessfully!!!!!!!" + result);
				loadingHelper.HideLoading(View.INVISIBLE);
				try {
					JSONObject json = new JSONObject(result);
					PayReq req = new PayReq();
					req.appId = json.getString("appId");
					req.partnerId = json.getString("partnerId");
					req.timeStamp = json.getString("timeStamp");
					req.packageValue = "Sign=WXPay";
					req.prepayId = json.getString("prepayId");
					req.nonceStr = json.getString("nonceStr");
					req.sign = json.getString("sign");

					api.sendReq(req);

				} catch (Exception e) {
					loadingHelper.ShowError("异常：" + e.getMessage());
					Toast.makeText(PayEnsureActivity.this,
							"异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void error(int state, String errorMsg) {
				Log.i("lxw", "WXpayOrder faild!!!!!!!");
				loadingHelper.ShowError(errorMsg);
			}
		});

	}

	private void initView() {
		image_course = (ImageView) this.findViewById(R.id.image_course_name);
		tv_pay_course_name = (TextView) this
				.findViewById(R.id.tv_pay_course_name);
		tv_pay_exam_name = (TextView) this.findViewById(R.id.tv_pay_exam_name);
		tv_pay_money = (TextView) this.findViewById(R.id.tv_pay_money);

		tv_pay_course_name.setText(chargeName);
		tv_pay_exam_name.setText(meName);
		tv_pay_money.setText("￥" + amount);
		image_course.setBackgroundResource(SubjectIconUtil.getIcon(bundle
				.getString("courseName")));

		ll_pay = (LinearLayout) this.findViewById(R.id.ll_pay_alipay);
		ll_wxpay = (LinearLayout) this.findViewById(R.id.ll_pay_wxpay);

		// 通过WXAPIFactory工厂，获取IWXAPI的实例
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);

		ll_pay.setOnClickListener(this);
		ll_wxpay.setOnClickListener(this);
	}

	private void initValues() {
		// TODO Auto-generated method stub
		this.bundle = this.getIntent().getExtras();
		chargeName = bundle.getString("chargeName");
		meId = bundle.getInt("meId");
		seId = bundle.getInt("seId");
		amount = bundle.getDouble("amount");
		totalFee = (int) (amount * 100);
		meName = bundle.getString("meName");
		Log.i("lxw", "PayEnsureActivity+chargeName" + chargeName + "meId"
				+ meId + "seId" + seId + "amount" + amount + "meName" + meName);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_pay_alipay:
			Intent intent = new Intent(this, PayDemoActivity.class);
			Bundle p_bundle = new Bundle();
			p_bundle.putString("chargeName", chargeName);
			p_bundle.putDouble("amount", amount);
			p_bundle.putString(
					"describe",
					"您将支付的科目是：" + meName + "中的"
							+ bundle.getString("courseName"));
			p_bundle.putString("orderId", orderId);
			intent.putExtras(p_bundle);

			startActivity(intent);
			break;
		case R.id.ll_pay_wxpay:
			// 微信支付
			if (isWXAppInstalledAndSupported()) {
				getWXInfo(orderId);
			} else {
				Toast.makeText(PayEnsureActivity.this, "请先安装微信客户端！",
						Toast.LENGTH_SHORT).show();
			}

			break;
		default:
			break;
		}
	}

	// 判断手机是否安装微信客户端
	private boolean isWXAppInstalledAndSupported() {
		IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
		msgApi.registerApp(Constants.APP_ID);

		boolean sIsWXAppInstalledAndSupported = msgApi.isWXAppInstalled()
				&& msgApi.isWXAppSupportAPI();

		return sIsWXAppInstalledAndSupported;
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
	public void OnRetryClick() {
		// TODO Auto-generated method stub
		loadingHelper.ShowLoading();
		readData();
	}

	/**
	 * 友盟统计
	 */
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("PayEnsureActivity");
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("PayEnsureActivity");
		MobclickAgent.onPause(this);
	}

}
