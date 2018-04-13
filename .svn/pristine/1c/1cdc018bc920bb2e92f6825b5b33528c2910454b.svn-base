package com.xyd.student.xydexamanalysis.adapter;

/**
 * Created by Lichg.
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.R.drawable;
import com.xyd.student.xydexamanalysis.entity.SingleNotice;
import com.xyd.student.xydexamanalysis.utils.StringUtils;
import com.xyd.student.xydexamanalysis.utils.SubjectIconUtil;
import android.content.Context;
import android.opengl.Visibility;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyNoticeAdapter extends BaseAdapter {
	private List<SingleNotice> data;
	private Context context;
	private ViewHolder holder;
	private String currenttime;
	private SpannableStringBuilder msp = null;

	public MyNoticeAdapter(Context context, List<SingleNotice> data) {
		this.data = data;
		this.context = context;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.CHINA);// 设置日期格式
		currenttime = df.format(new Date());// new Date()为获取当前系统时间
	}

	private class ViewHolder {
		private TextView v_type, v_info, v_time;
		private ImageView subject_img, unread_img;
		private ImageView pay_icon;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data == null ? 0 : data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data == null ? null : data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			int layoutid = R.layout.item_notice;
			convertView = LayoutInflater.from(context).inflate(layoutid, null);
			holder = new ViewHolder();
			holder.v_type = (TextView) convertView.findViewById(R.id.v_type);
			holder.v_info = (TextView) convertView.findViewById(R.id.v_info);
			holder.v_time = (TextView) convertView.findViewById(R.id.v_time);
			holder.subject_img = (ImageView) convertView
					.findViewById(R.id.subject_img);
			holder.unread_img = (ImageView) convertView
					.findViewById(R.id.unread_img);

			holder.pay_icon = (ImageView) convertView
					.findViewById(R.id.image_pay_icon);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (data != null) {
			setImageDisplayById(data.get(position).getCourseId());
			setReadIcon(data.get(position).getN_isread());
			holder.v_type.setText(data.get(position).getN_type());
			String displayinfo = data.get(position).getN_info();
			msp = StringUtils.getSpannableStringBuilder(displayinfo);
			holder.v_info.setText(msp);
			holder.v_time
					.setText(getdisplayTime(data.get(position).getN_time()));

			if (data.get(position).getPayInfo().getPayStatus() == 1) {
				holder.pay_icon.setBackgroundResource(R.drawable.qian);
				if (data.get(position).getPayInfo().getChargeInfo() == null) {
					holder.pay_icon.setBackgroundResource(R.drawable.blank);
				}

			} else {
				holder.pay_icon.setBackgroundResource(R.drawable.qian_disabled);
			}

		}
		return convertView;
	}

	private void setImageDisplayById(int courseId) {
		holder.subject_img.setImageResource(SubjectIconUtil
				.getIconById(courseId));
	}

	public void setReadIcon(int is) {
		if (is != 0) {
			holder.unread_img.setVisibility(View.GONE);
		} else {
			holder.unread_img.setVisibility(View.VISIBLE);
		}
	}

	private void setImageDisplay(String subject) {

		holder.subject_img.setImageResource(SubjectIconUtil.getIcon(subject));
	}

	/**
	 * 处理listview数据变化
	 * 
	 * @param list
	 */
	public void setList(List<SingleNotice> list) {
		this.data = list;
		notifyDataSetChanged();
	}

	public String getdisplayTime(String time) {
		int cd, d;
		if (!currenttime.substring(0, 4).equals(time.substring(0, 4))) {
			d = Integer.parseInt(time.substring(0, 4));
			cd = Integer.parseInt(currenttime.substring(0, 4));
			return (cd - d) + "年前";
		}
		if (!currenttime.substring(5, 7).equals(time.substring(5, 7))) {
			d = Integer.parseInt(time.substring(5, 7));
			cd = Integer.parseInt(currenttime.substring(5, 7));
			return (cd - d) + "个月前";
		}
		if (!currenttime.substring(8, 10).equals(time.substring(8, 10))) {
			d = Integer.parseInt(time.substring(8, 10));
			cd = Integer.parseInt(currenttime.substring(8, 10));
			return (cd - d) + "天前";
		}
		if (!currenttime.substring(11, 13).equals(time.substring(11, 13))) {
			d = Integer.parseInt(time.substring(11, 13));
			cd = Integer.parseInt(currenttime.substring(11, 13));
			return (cd - d) + "个小时前";
		}
		if (!currenttime.substring(14, 16).equals(time.substring(14, 16))) {
			d = Integer.parseInt(time.substring(14, 16));
			cd = Integer.parseInt(currenttime.substring(14, 16));
			return (cd - d) + "分钟前";
		}
		return "";
	}
}
