package com.xyd.student.xydexamanalysis.adapter;

/**
 * Created by Lichg.
 */
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class MyknowledgeAdapter extends SimpleAdapter {

	public MyknowledgeAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.SimpleAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// View view = super.getView(position, convertView, parent);
		// if (position % 2 == 1)
		// view.setBackgroundColor(Color.rgb(241, 246, 250));
		// return view;
		convertView = null;
		convertView = super.getView(position, convertView, parent);
		convertView.setPadding(0, 25, 0, 25);
		if (position % 2 == 1) {
			convertView.setBackgroundColor(Color.rgb(241, 246, 250));
		}
		return convertView;
	}
}
