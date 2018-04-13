package com.xyd.student.xydexamanalysis.wxapi;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.constant.Constants;
import com.xyd.student.xydexamanalysis.ui.PayEnsureActivity;
import com.xyd.student.xydexamanalysis.ui.PaySucceedActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_result);

		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

			switch (resp.errCode) {
			case 0:
				// 返回值为0，说明支付成功：
				Intent intent = new Intent(WXPayEntryActivity.this,
						PaySucceedActivity.class);
				startActivity(intent);
				this.finish();
				break;
			case -1:
				// 返回值为-1，说明支付失败：

				Toast.makeText(WXPayEntryActivity.this, "支付失败！",
						Toast.LENGTH_SHORT).show();
				this.finish();
				break;
			case -2:
				// 返回值为-2，说明取消支付：

				this.finish();
				break;

			default:
				break;
			}
		}
	}

	/**
	 * 友盟统计
	 */
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("WXPayEntryActivity");
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("WXPayEntryActivity");
		MobclickAgent.onPause(this);
	}
}
