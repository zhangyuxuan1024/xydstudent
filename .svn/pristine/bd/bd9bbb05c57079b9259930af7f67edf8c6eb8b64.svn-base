package com.xyd.student.xydexamanalysis.adapter;

import io.vov.vitamio.utils.Log;

import java.io.Serializable;
import java.util.ArrayList;

import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.R.id;
import com.xyd.student.xydexamanalysis.entity.Explain_single_info;
import com.xyd.student.xydexamanalysis.ui.StudentPaperActivity;
import com.xyd.student.xydexamanalysis.ui.ViewVideoActivity;
import com.xyd.student.xydexamanalysis.utils.LoginUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.BounceInterpolator;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ExplainqustionAdapter extends BaseAdapter {

	private int catege;
	private ArrayList<Explain_single_info> datalist;
	private LayoutInflater inflater;
	private Context context;
	private LoginUtils login;

	public ExplainqustionAdapter(Context mContext, int catage,
			ArrayList<Explain_single_info> cdatalist) {
		this.catege = catage;
		this.context = mContext;
		this.datalist = cdatalist;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (datalist != null) {
			return datalist.size();
		}
		return 0;
	}

	@Override
	public Explain_single_info getItem(int position) {
		// TODO Auto-generated method stub
		if (null == datalist || position < 0 || position > getCount()) {
			return null;
		}
		return datalist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ExplainqustionViewholder viewholder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.examquestion_item_layout,
					null);
			TextView qustion_type_tx = (TextView) convertView
					.findViewById(R.id.qustion_type_tx);
			TextView full_scro = (TextView) convertView
					.findViewById(R.id.full_scro);
			TextView aquire_scro = (TextView) convertView
					.findViewById(R.id.aquire_scro);
			TextView tv_show = (TextView) convertView
					.findViewById(R.id.tv_show);
			ImageView qustion_dificulty_img = (ImageView) convertView
					.findViewById(R.id.qustion_dificulty_img);
			ImageView qustion_url_image = (ImageView) convertView
					.findViewById(R.id.qustion_url_image);
			viewholder = new ExplainqustionViewholder();
			viewholder.qustion_type_tx = qustion_type_tx;
			viewholder.full_scro = full_scro;
			viewholder.aquire_scro = aquire_scro;
			viewholder.qustion_dificulty_img = qustion_dificulty_img;
			viewholder.qustion_url_image = qustion_url_image;
			viewholder.tv_show = tv_show;
			convertView.setTag(viewholder);
		} else {
			viewholder = (ExplainqustionViewholder) convertView.getTag();
		}
		final Explain_single_info info = getItem(position);
		Log.i("测试专用", datalist + "" + position);

		viewholder.full_scro.setText(info.getFullScore() + "");
		viewholder.aquire_scro.setText(info.getScore() + "");
		double difficulty = info.getDifficulty();
		if (difficulty >= 0 && difficulty < 0.05) {
			viewholder.qustion_dificulty_img.setImageResource(R.drawable.star0);
		} else if (difficulty >= 0.05 && difficulty < 0.15) {
			viewholder.qustion_dificulty_img.setImageResource(R.drawable.star1);
		} else if (difficulty >= 0.15 && difficulty < 0.25) {
			viewholder.qustion_dificulty_img.setImageResource(R.drawable.star2);
		} else if (difficulty >= 0.25 && difficulty < 0.35) {
			viewholder.qustion_dificulty_img.setImageResource(R.drawable.star3);
		} else if (difficulty >= 0.35 && difficulty < 0.45) {
			viewholder.qustion_dificulty_img.setImageResource(R.drawable.star4);
		} else if (difficulty >= 0.45 && difficulty < 0.55) {
			viewholder.qustion_dificulty_img.setImageResource(R.drawable.star5);
		} else if (difficulty >= 0.55 && difficulty < 0.65) {
			viewholder.qustion_dificulty_img.setImageResource(R.drawable.star6);
		} else if (difficulty >= 0.65 && difficulty < 0.75) {
			viewholder.qustion_dificulty_img.setImageResource(R.drawable.star7);
		} else if (difficulty >= 0.75 && difficulty < 0.85) {
			viewholder.qustion_dificulty_img.setImageResource(R.drawable.star8);
		} else if (difficulty >= 0.85 && difficulty < 0.95) {
			viewholder.qustion_dificulty_img.setImageResource(R.drawable.star9);
		} else if (difficulty >= 0.95 && difficulty <= 1.00) {
			viewholder.qustion_dificulty_img
					.setImageResource(R.drawable.star10);
		} else {
			viewholder.qustion_dificulty_img.setImageResource(R.drawable.star0);
		}
		String type = info.getType();
		System.out.println(type);
		if (type.equals("QuestionExplainMsgDetail")) {
			viewholder.qustion_dificulty_img.setVisibility(View.GONE);
			viewholder.tv_show.setText("相关题目：" + info.getDisplayName());

			final String wkId = info.getWkId();

			viewholder.qustion_type_tx.setText(info.getWkName());
			viewholder.qustion_url_image
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (wkId.equals("") || wkId.equals(null)) {
								Toast.makeText(context, "该题没有视频讲解！",
										Toast.LENGTH_LONG).show();
							} else {
								login = new LoginUtils();
								Intent intentwk = new Intent(context,
										ViewVideoActivity.class);
								intentwk.putExtra("wk_reourceid", wkId);
								intentwk.putExtra("wk_url",
										login.getWeiKeServUrl()
												+ "WeiKe/resource/");
								context.startActivity(intentwk);
							}

						}
					});
		} else {
			viewholder.qustion_type_tx.setText(info.getDisplayName());
			viewholder.qustion_url_image.setVisibility(View.VISIBLE);
			viewholder.qustion_url_image
					.setBackgroundResource(R.drawable.chakan);
			viewholder.qustion_url_image
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(context,
									StudentPaperActivity.class);
							intent.putExtra("info", info);
							context.startActivity(intent);
						}
					});
		}
		return convertView;
	}

	private class ExplainqustionViewholder {
		private TextView qustion_type_tx;
		private TextView full_scro;
		private TextView aquire_scro;
		private TextView tv_show;
		private ImageView qustion_dificulty_img;
		private ImageView qustion_url_image;

	}

}
