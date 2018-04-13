package com.xyd.student.xydexamanalysis.view;

import com.xyd.student.xydexamanalysis.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

public class MyScoreView extends View {
	private Context mcontext;
	private String score;
	private float sX;
	private float sY;

	public float getsX() {
		return sX;
	}

	public void setsX(float sX) {
		this.sX = sX;
	}

	public float getsY() {
		return sY;
	}

	public void setsY(float sY) {
		this.sY = sY;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public MyScoreView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mcontext = context;
	}

	public MyScoreView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mcontext = context;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		drawScore(mcontext, canvas);
	}

	private void drawScore(Context context, Canvas cv) {
		if (score != null && !score.equals(null) && !score.equals("null")) {
			char[] charArray = score.toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				char c = charArray[i];
				switch (c) {
				case '1':
					Bitmap n1 = BitmapFactory.decodeResource(
							context.getResources(), R.drawable.no_1);
					cv.drawBitmap(n1, sX, sY, null);
					sX += n1.getWidth();
					n1.recycle();
					n1 = null;
					break;
				case '2':
					Bitmap n2 = BitmapFactory.decodeResource(
							context.getResources(), R.drawable.no_2);
					cv.drawBitmap(n2, sX, sY, null);
					sX += n2.getWidth();
					n2.recycle();
					n2 = null;
					break;
				case '3':
					Bitmap n3 = BitmapFactory.decodeResource(
							context.getResources(), R.drawable.no_3);
					cv.drawBitmap(n3, sX, sY, null);
					sX += n3.getWidth();
					n3.recycle();
					n3 = null;
					break;
				case '4':
					Bitmap n4 = BitmapFactory.decodeResource(
							context.getResources(), R.drawable.no_4);
					cv.drawBitmap(n4, sX, sY, null);
					sX += n4.getWidth();
					n4.recycle();
					n4 = null;
					break;
				case '5':
					Bitmap n5 = BitmapFactory.decodeResource(
							context.getResources(), R.drawable.no_5);
					cv.drawBitmap(n5, sX, sY, null);
					sX += n5.getWidth();
					n5.recycle();
					n5 = null;
					break;
				case '6':
					Bitmap n6 = BitmapFactory.decodeResource(
							context.getResources(), R.drawable.no_6);
					cv.drawBitmap(n6, sX, sY, null);
					sX += n6.getWidth();
					n6.recycle();
					n6 = null;
					break;
				case '7':
					Bitmap n7 = BitmapFactory.decodeResource(
							context.getResources(), R.drawable.no_7);
					cv.drawBitmap(n7, sX, sY, null);
					sX += n7.getWidth();
					n7.recycle();
					n7 = null;
					break;
				case '8':
					Bitmap n8 = BitmapFactory.decodeResource(
							context.getResources(), R.drawable.no_8);
					cv.drawBitmap(n8, sX, sY, null);
					sX += n8.getWidth();
					n8.recycle();
					n8 = null;
					break;
				case '9':
					Bitmap n9 = BitmapFactory.decodeResource(
							context.getResources(), R.drawable.no_9);
					cv.drawBitmap(n9, sX, sY, null);
					sX += n9.getWidth();
					n9.recycle();
					n9 = null;
					break;
				case '0':
					Bitmap n0 = BitmapFactory.decodeResource(
							context.getResources(), R.drawable.no_0);
					cv.drawBitmap(n0, sX, sY, null);
					sX += n0.getWidth();
					n0.recycle();
					n0 = null;
					break;
				case '.':
					Bitmap nd = BitmapFactory.decodeResource(
							context.getResources(), R.drawable.no_dian);
					cv.drawBitmap(nd, sX, sY, null);
					sX += nd.getWidth();
					nd.recycle();
					nd = null;
					break;
				}
			}
		} else {
			Toast.makeText(mcontext, "-----", Toast.LENGTH_SHORT).show();
		}
	}
}