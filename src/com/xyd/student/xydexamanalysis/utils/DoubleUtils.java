package com.xyd.student.xydexamanalysis.utils;

import java.text.DecimalFormat;

public class DoubleUtils {
	// 五舍六入
	public static String doubletwo(double oldscore) {
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(oldscore);
	}

	// 四舍五入
	public static String doubletodouble(double oldscore) {
		java.math.BigDecimal bigDec = new java.math.BigDecimal(oldscore);
		double total = bigDec.setScale(2, java.math.BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(total) + "";
	}

	// 四舍五入取整
	public static int doubletoint(double oldscore) {
		int newscore = (int) Math.round(oldscore);
		return newscore;
	}
}
