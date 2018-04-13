package com.xyd.student.xydexamanalysis.ui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.umeng.analytics.MobclickAgent;
import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.adapter.GuidePagerAdapter;
import com.xyd.student.xydexamanalysis.view.CirclePageIndicator;
import com.xyd.student.xydexamanalysis.view.TitleBar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class VersionIntroduction extends Activity {
	private Context mContext;
	private ViewPager guide_viewPager;
	private GuidePagerAdapter adapter;
	private CirclePageIndicator indicator;
	private int currentIndex = 0;
	private TitleBar titleBar;
	// 引导图片资源
	private static final int[] pics = { R.drawable.guide1, R.drawable.guide2,
			R.drawable.guide3 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.version_intro_layout);
		mContext = this;
		guide_viewPager = (ViewPager) findViewById(R.id.guide_viewpager);
		List<View> views = new ArrayList<View>();

		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);

		for (int i = 0; i < pics.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setLayoutParams(mParams);
			Bitmap n1 = readBitMap(VersionIntroduction.this, pics[i]);
			iv.setImageBitmap(n1);
			// iv.setBackgroundResource(pics[i]);
			views.add(iv);
		}
		adapter = new GuidePagerAdapter(this, views);
		guide_viewPager.setAdapter(adapter);
		indicator = (CirclePageIndicator) findViewById(R.id.viewflowindic);
		indicator.setmListener(new MypageChangeListener());
		indicator.setViewPager(guide_viewPager);
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

		}
	}

	/**
	 * 友盟统计
	 */
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("VersionIntroduction");
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("VersionIntroduction");
		MobclickAgent.onPause(this);
	}
}
