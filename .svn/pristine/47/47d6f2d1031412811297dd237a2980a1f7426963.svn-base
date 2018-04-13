package com.xyd.student.xydexamanalysis.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.xyd.student.xydexamanalysis.utils.UIUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Lichg2015.8.21
 */
public abstract class BaseActivity extends FragmentActivity {
	/**
	 * 记录处于前台的Activity
	 */
	private static BaseActivity mForegroundActivity = null;
	/**
	 * 记录所有活动的Activity
	 */
	private static final List<BaseActivity> mActivities = new LinkedList<BaseActivity>();
	private long time = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initTitle();
	}

	protected abstract void initView();

	protected abstract void initTitle();

	@Override
	protected void onResume() {
		mForegroundActivity = this;
		super.onResume();
	}

	@Override
	protected void onPause() {
		mForegroundActivity = null;
		super.onPause();
	}

	/**
	 * 关闭所有Activity
	 */
	public static void finishAll() {
		List<BaseActivity> copy;
		synchronized (mActivities) {
			copy = new ArrayList<BaseActivity>(mActivities);
		}
		for (BaseActivity activity : copy) {
			activity.finish();
		}
	}

	/**
	 * 关闭所有Activity，除了参数传递的Activity
	 */
	public static void finishAll(BaseActivity except) {
		List<BaseActivity> copy;
		synchronized (mActivities) {
			copy = new ArrayList<BaseActivity>(mActivities);
		}
		for (BaseActivity activity : copy) {
			if (activity != except)
				activity.finish();
		}
	}

	/**
	 * 是否有启动的Activity
	 */
	public static boolean hasActivity() {
		return mActivities.size() > 0;
	}

	/**
	 * 获取当前处于前台的activity
	 */
	public static BaseActivity getForegroundActivity() {
		return mForegroundActivity;
	}

	/**
	 * 获取当前处于栈顶的activity，无论其是否处于前台
	 */
	public static BaseActivity getCurrentActivity() {
		List<BaseActivity> copy;
		synchronized (mActivities) {
			copy = new ArrayList<BaseActivity>(mActivities);
		}
		if (copy.size() > 0) {
			return copy.get(copy.size() - 1);
		}
		return null;
	}

	/**
	 * 退出应用
	 */
	public void exit() {
		finishAll();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 退出系统方法
	 */
	public void exitApp() {
		long now_time = System.currentTimeMillis();
		if (now_time - time > 2000) {
			time = now_time;
			UIUtils.showToastSafe("再次点击返回键,系统将退出");
		} else {
			exit();
		}
	}
}