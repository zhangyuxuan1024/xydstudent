package com.xyd.student.xydexamanalysis.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast提示框工具类
 * 
 * @author Lichg2015.8.21
 */
public class ToastUtils {

	public static void show(Context ctx, String msg) {
		show(ctx, msg, Toast.LENGTH_SHORT);
	}

	/**
	 * 显示Toast
	 * 
	 * @param c
	 *            context
	 * @param name
	 *            显示文字
	 */
	public static void show(Context ctx, String msg, int delay) {
		Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 关闭Toast
	 */
	public static void close() {

	}

	public static boolean checkJsonError(Context c, Object obj) {
		if (obj == null) {
			show(c, "解析错误");
			return true;
		}
		return false;
	}
}
