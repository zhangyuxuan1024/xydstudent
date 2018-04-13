package com.xyd.student.xydexamanalysis.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class MyItemListView extends ListView {

	public MyItemListView(Context context, AttributeSet attrs) {

		super(context, attrs);

	}

	public MyItemListView(Context context) {

		super(context);

	}

	public MyItemListView(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);

	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,

		MeasureSpec.AT_MOST);

		super.onMeasure(widthMeasureSpec, expandSpec);

	}

}
