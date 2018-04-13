package com.xyd.student.xydexamanalysis.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ListView;

public class FullListView extends ListView {
	public FullListView(Context context) {
		super(context);
	}

	public FullListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(50000,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
