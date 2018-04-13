package com.xyd.student.xydexamanalysis.ui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.umeng.analytics.MobclickAgent;
import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.adapter.GuidePagerAdapter;
import com.xyd.student.xydexamanalysis.view.CirclePageIndicator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GuideActivity extends Activity {
	private Context mContext;
	private Button startButton;
	private ViewPager guide_viewPager;
	private GuidePagerAdapter adapter;
	private CirclePageIndicator indicator;
	private int currentIndex = 0;
	// 引导图片资源
	private static final int[] pics = { R.drawable.guide1, R.drawable.guide2,
			R.drawable.guide3 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guide_layout);
		mContext = this;
		guide_viewPager = (ViewPager) findViewById(R.id.guide_viewpager);
		startButton = (Button) findViewById(R.id.button);
		List<View> views = new ArrayList<View>();

		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		for (int i = 0; i < pics.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setLayoutParams(mParams);
			Bitmap n1 = readBitMap(GuideActivity.this, pics[i]);
			iv.setImageBitmap(n1);
			views.add(iv);
		}
		adapter = new GuidePagerAdapter(this, views);
		guide_viewPager.setAdapter(adapter);
		indicator = (CirclePageIndicator) findViewById(R.id.viewflowindic);
		indicator.setmListener(new MypageChangeListener());
		indicator.setViewPager(guide_viewPager);
		startButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(GuideActivity.this,
						LoginActivity.class);
				startActivity(intent);
				GuideActivity.this.finish();
				overridePendingTransition(R.anim.alpha_in_anim, // Activity的切换动画，从一个activity跳转到另外一个activity时的动画。
						R.anim.alpha_out_anim);
			}
		});
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

	private class MypageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int position) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageSelected(int arg0) {
			currentIndex = arg0;
			if (currentIndex == 2) {
				startButton.setVisibility(View.VISIBLE);
			} else {
				startButton.setVisibility(View.GONE);
			}

		}
	}

	/**
	 * 友盟统计
	 */
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("GuideActivity");
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("GuideActivity");
		MobclickAgent.onPause(this);
	}
}
