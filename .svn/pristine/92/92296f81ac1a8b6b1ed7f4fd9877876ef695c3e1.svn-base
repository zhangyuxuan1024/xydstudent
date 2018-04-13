package com.xyd.student.xydexamanalysis.view;

import com.xyd.student.xydexamanalysis.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailTitle extends FrameLayout {
	private View view;

	public DetailTitle(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initview();
	}

	public DetailTitle(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private void initview() {
		view = LayoutInflater.from(getContext()).inflate(
				R.layout.activity_detail_title, this);
	}

	public void setTitle(int id_img, String titlename, String titletime) {
		((ImageView) view.findViewById(R.id.title_img))
				.setImageResource(id_img);
		((TextView) view.findViewById(R.id.title_name)).setText(titlename);
		((TextView) view.findViewById(R.id.title_time)).setText(titletime);
	}
}
