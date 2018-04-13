package com.xyd.student.xydexamanalysis.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyImageView extends ImageView {

	private int color;

	public MyImageView(Context context) {
		super(context);
		color = Color.argb(0xff, 0x00, 0x00, 0x00);
	}

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		color = Color.argb(0xff, 0x00, 0x00, 0x00);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ImageView#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		super.onDraw(canvas);
		// 画边框
		Rect rec = canvas.getClipBounds();
		rec.left++;
		rec.top++;
		rec.bottom--;
		rec.right--;
		Paint paint = new Paint();
		paint.setColor(color);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(rec, paint);
	}
}
