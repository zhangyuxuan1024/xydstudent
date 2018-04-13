package com.xyd.student.xydexamanalysis.adapter;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class GradeSituationAdapter extends SimpleAdapter {

	public GradeSituationAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = null;
		convertView = super.getView(position, convertView, parent);
		if (position % 2 == 1) {
			convertView.setBackgroundColor(Color.rgb(241, 246, 250));
		}
		convertView.setPadding(0, 25, 0, 25);
		return convertView;
	}
}
