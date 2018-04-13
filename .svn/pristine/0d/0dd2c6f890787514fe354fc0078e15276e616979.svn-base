package com.xyd.student.xydexamanalysis.ui;

import java.io.InputStream;

import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.constant.Constants;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class WelcomeActivity extends InstrumentedActivity {
	private Context mContext;
	private Boolean isFirst;
	private SharedPreferences sharedPreferences;
	private Editor editor;
	private LinearLayout welcomeLayout;
	private ImageView imageView;
	private IWXAPI api;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext = this;
		setContentView(R.layout.welcome_activity);
		// 注册微信
		regToWx();

		MobclickAgent.setDebugMode(true);
		// SDK在统计Fragment时，需要关闭Activity自带的页面统计，
		// 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
		MobclickAgent.openActivityDurationTrack(false);

		welcomeLayout = (LinearLayout) findViewById(R.id.welcome_linearlayout);
		imageView = (ImageView) findViewById(R.id.iv_welcome);
		Bitmap bitmap = readBitMap(WelcomeActivity.this, R.drawable.qidong);
		imageView.setImageBitmap(bitmap);
		sharedPreferences = mContext.getSharedPreferences(
				Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
		AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
		aa.setDuration(2000);
		welcomeLayout.setAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				isFirst = sharedPreferences.getBoolean(Constants.FIRST_LOGIN,
						true);
				if (isFirst) {
					editor = sharedPreferences.edit();
					editor.putBoolean(Constants.FIRST_LOGIN, false);
					editor.commit();
					Intent intent = new Intent(WelcomeActivity.this,
							GuideActivity.class);
					startActivity(intent);
					WelcomeActivity.this.finish();
				} else {

					Intent intent = new Intent(WelcomeActivity.this,
							LoginActivity.class);
					startActivity(intent);
					WelcomeActivity.this.finish();
				}
				overridePendingTransition(R.anim.alpha_in_anim,
						R.anim.alpha_out_anim);
			}
		});
	}

	private void regToWx() {
		// 通过WXAPIFactory工厂，获取IWXAPI的实例
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);

		// 将应用的appId注册到微信
		api.registerApp(Constants.APP_ID);
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

	/**
	 * 友盟统计
	 */
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("WelcomeActivity");
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("WelcomeActivity");
		MobclickAgent.onPause(this);
	}

}
