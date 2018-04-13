package com.xyd.student.xydexamanalysis.adapter;

/**
 * Created by Lichg.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.entity.SingleExam;
import com.xyd.student.xydexamanalysis.ui.GradeReportActivity;
import com.xyd.student.xydexamanalysis.ui.PayDialog2Activity;
import com.xyd.student.xydexamanalysis.ui.PayDialogActivity;
import com.xyd.student.xydexamanalysis.ui.SingReportActivity;
import com.xyd.student.xydexamanalysis.utils.LoginUtils;
import com.xyd.student.xydexamanalysis.utils.StringUtils;
import com.xyd.student.xydexamanalysis.utils.SubjectIconUtil;
import com.xyd.student.xydexamanalysis.utils.UIUtils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MyExamAdapter extends BaseAdapter {

	private List<SingleExam> data;
	private Context context;
	private ViewHolder holder;
	private LoginUtils loginUtils;

	public MyExamAdapter(Context context, List<SingleExam> data) {
		this.data = data;
		this.context = context;
		loginUtils = new LoginUtils();
	}

	private class ViewHolder {
		private TextView item_title_time, item_exam_name1, item_exam_name2;
		private ListView listview;
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

		ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_exam, null);
			holder = new ViewHolder();
			holder.item_title_time = (TextView) convertView
					.findViewById(R.id.item_title_time);
			holder.item_exam_name1 = (TextView) convertView
					.findViewById(R.id.item_exam_name1);
			holder.item_exam_name2 = (TextView) convertView
					.findViewById(R.id.item_exam_name2);
			holder.listview = (ListView) convertView
					.findViewById(R.id.listview);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (data != null) {
			holder.item_title_time.setText(StringUtils.timeToYYYYMMdd(data.get(
					position).getExam_time()));
			holder.item_exam_name1.setText(data.get(position).getExam_name());
			holder.item_exam_name2.setText(StringUtils.ToDBC(data.get(position)
					.getExam_info()));

			int listviewlength = data.get(position).getReportlist().size();
			for (int i = 0; i < listviewlength; i++) {
				Map<String, Object> item = new HashMap<String, Object>();
				// item.put("title",
				// data.get(position).reportlist.get(i).se_course_name);
				item.put(
						"text_name",
						StringUtils.ToDBC(data.get(position).getReportlist()
								.get(i).getCourse_name()));
				item.put("text_score", data.get(position).getReportlist()
						.get(i).getCourse_score());
				item.put(
						"text",
						StringUtils.ToDBC((data.get(position).getReportlist()
								.get(i).getSe_info())));
				item.put(
						"image",
						SubjectIconUtil.getIconById(data.get(position)
								.getReportlist().get(i).getCourse_id()));
				item.put(
						"image_pay",
						SubjectIconUtil.getPayIcon(data.get(position)
								.getReportlist().get(i).getPayInfo()
								.getPayStatus(), data.get(position)
								.getReportlist().get(i).getPayInfo()
								.getChargeInfo(), data.get(position)
								.getReportlist().get(i).getPayInfo()
								.getPayStatus()));
				mData.add(item);
			}
			SimpleAdapter adapter = new SimpleAdapter(UIUtils.getContext(),
					mData, R.layout.item_single_report, new String[] { "image",
							"text", "text_name", "text_score", "image_pay" },
					new int[] { R.id.image, R.id.text, R.id.text_name,
							R.id.text_score, R.id.image_pay_icon2 });
			holder.listview.setAdapter(adapter);
			holder.listview.setOnItemClickListener(new OnItemClickListener() {
				@SuppressLint("NewApi")
				@Override
				public void onItemClick(AdapterView<?> adapterView, View view,
						int position_item, long id) {
					String course_name = data.get(position).getReportlist()
							.get(position_item).getSe_course_name();

					int payStatus = data.get(position).getReportlist()
							.get(position_item).getPayInfo().getPayStatus();
					if (course_name.endsWith("总分")) {
						// 判断支付

						if (payStatus == 1) {
							Intent intent = new Intent(context,
									GradeReportActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

							Bundle bundle = new Bundle();
							bundle.putString("time", data.get(position)
									.getExam_time());
							bundle.putString("name", data.get(position)
									.getExam_name());
							bundle.putInt("meId", data.get(position).getMeId());
							bundle.putString("studentId",
									loginUtils.getStudentid());
							bundle.putInt("schoolId", loginUtils.getSchoolId());
							System.out.println(bundle);
							intent.putExtras(bundle);
							context.startActivity(intent);
						} else {
							int payType = data.get(position).getReportlist()
									.get(position_item).getPayInfo()
									.getChargeInfo().getPayType();
							if (payType == 1) {
								Intent p_intent = new Intent(context,
										PayDialogActivity.class);
								Bundle p_bundle = new Bundle();
								p_bundle.putInt("meId", data.get(position)
										.getReportlist().get(position_item)
										.getMeId());
								p_bundle.putInt("seId", data.get(position)
										.getReportlist().get(position_item)
										.getSeId());
								p_bundle.putString(
										"chargeName",
										data.get(position).getReportlist()
												.get(position_item)
												.getPayInfo().getChargeInfo()
												.getChargeName());
								p_bundle.putDouble("amount", data.get(position)
										.getReportlist().get(position_item)
										.getPayInfo().getChargeInfo()
										.getAmount());
								p_bundle.putString("meName", data.get(position)
										.getExam_name());
								p_bundle.putString(
										"courseName",
										data.get(position).getReportlist()
												.get(position_item)
												.getCourse_name());
								p_intent.putExtras(p_bundle);

								p_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

								context.startActivity(p_intent);
							} else if (payType == 2) {
								Intent p_intent = new Intent(context,
										PayDialog2Activity.class);
								Bundle p_bundle = new Bundle();
								p_bundle.putInt("meId", data.get(position)
										.getReportlist().get(position_item)
										.getMeId());
								p_bundle.putInt("seId", data.get(position)
										.getReportlist().get(position_item)
										.getSeId());
								p_bundle.putString(
										"chargeName",
										data.get(position).getReportlist()
												.get(position_item)
												.getPayInfo().getChargeInfo()
												.getChargeName());
								p_bundle.putDouble("amount", data.get(position)
										.getReportlist().get(position_item)
										.getPayInfo().getChargeInfo()
										.getAmount());
								p_bundle.putString("meName", data.get(position)
										.getExam_name());
								p_bundle.putString(
										"courseName",
										data.get(position).getReportlist()
												.get(position_item)
												.getCourse_name());
								p_bundle.putString(
										"freeTime",
										data.get(position).getReportlist()
												.get(position_item)
												.getPayInfo().getChargeInfo()
												.getFreeTime());

								p_bundle.putString("time", data.get(position)
										.getExam_time());
								p_bundle.putString("name", data.get(position)
										.getExam_name());

								p_intent.putExtras(p_bundle);

								p_intent.putExtra("studentId",
										loginUtils.getStudentid());
								p_intent.putExtra("schoolId",
										loginUtils.getSchoolId());
								p_intent.putExtra("fragmentType", "Exam");
								p_intent.putExtra("noticetype", "总成绩报告");

								p_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

								context.startActivity(p_intent);
							}

						}

					} else {
						// 判断支付

						if (payStatus == 1) {

							Intent intent = new Intent(context,
									SingReportActivity.class);

							Bundle v_intent = new Bundle();
							v_intent.putString("studentName",
									loginUtils.getStudentname());
							v_intent.putString("studentId",
									loginUtils.getStudentid());
							v_intent.putString("classId", data.get(position)
									.getReportlist().get(position_item)
									.getClassId());
							v_intent.putInt("schoolId",
									loginUtils.getSchoolId());
							v_intent.putInt("seId", data.get(position)
									.getReportlist().get(position_item)
									.getSeId());
							v_intent.putInt("meId", data.get(position)
									.getReportlist().get(position_item)
									.getMeId());
							v_intent.putInt("currentindex", -1);
							v_intent.putString("examname", data.get(position)
									.getExam_name());
							v_intent.putString("subject", data.get(position)
									.getReportlist().get(position_item)
									.getSe_course_name());
							v_intent.putString("time", data.get(position)
									.getExam_time());
							v_intent.putString("classId", data.get(position)
									.getReportlist().get(position_item)
									.getClassId());
							intent.putExtras(v_intent);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							context.startActivity(intent);
						} else {
							int payType = data.get(position).getReportlist()
									.get(position_item).getPayInfo()
									.getChargeInfo().getPayType();

							if (payType == 1) {
								Intent p_intent = new Intent(context,
										PayDialogActivity.class);
								Bundle p_bundle = new Bundle();
								p_bundle.putInt("meId", data.get(position)
										.getReportlist().get(position_item)
										.getMeId());
								p_bundle.putInt("seId", data.get(position)
										.getReportlist().get(position_item)
										.getSeId());
								p_bundle.putString(
										"chargeName",
										data.get(position).getReportlist()
												.get(position_item)
												.getPayInfo().getChargeInfo()
												.getChargeName());
								p_bundle.putDouble("amount", data.get(position)
										.getReportlist().get(position_item)
										.getPayInfo().getChargeInfo()
										.getAmount());
								p_bundle.putString("meName", data.get(position)
										.getExam_name());
								p_bundle.putString(
										"courseName",
										data.get(position).getReportlist()
												.get(position_item)
												.getCourse_name());
								p_intent.putExtras(p_bundle);
								p_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

								context.startActivity(p_intent);
							} else if (payType == 2) {
								Intent p_intent = new Intent(context,
										PayDialog2Activity.class);
								Bundle p_bundle = new Bundle();
								p_bundle.putInt("meId", data.get(position)
										.getReportlist().get(position_item)
										.getMeId());
								p_bundle.putInt("seId", data.get(position)
										.getReportlist().get(position_item)
										.getSeId());
								p_bundle.putString(
										"chargeName",
										data.get(position).getReportlist()
												.get(position_item)
												.getPayInfo().getChargeInfo()
												.getChargeName());
								p_bundle.putDouble("amount", data.get(position)
										.getReportlist().get(position_item)
										.getPayInfo().getChargeInfo()
										.getAmount());
								p_bundle.putString("meName", data.get(position)
										.getExam_name());
								p_bundle.putString(
										"courseName",
										data.get(position).getReportlist()
												.get(position_item)
												.getCourse_name());
								p_bundle.putString(
										"freeTime",
										data.get(position).getReportlist()
												.get(position_item)
												.getPayInfo().getChargeInfo()
												.getFreeTime());

								p_bundle.putString(
										"classId",
										data.get(position).getReportlist()
												.get(position_item)
												.getClassId());
								p_bundle.putString("examname",
										data.get(position).getExam_name());
								p_bundle.putString(
										"subject",
										data.get(position).getReportlist()
												.get(position_item)
												.getSe_course_name());
								p_bundle.putString("time", data.get(position)
										.getExam_time());

								p_intent.putExtras(p_bundle);

								p_intent.putExtra("studentId",
										loginUtils.getStudentid());
								p_intent.putExtra("schoolId",
										loginUtils.getSchoolId());
								p_intent.putExtra("studentName",
										loginUtils.getStudentname());
								p_intent.putExtra("fragmentType", "Exam");
								p_intent.putExtra("noticetype", "单科成绩报告");

								p_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

								context.startActivity(p_intent);
							}

						}
					}
				}
			});
		}
		return convertView;
	}

	/**
	 * 处理listview数据变化
	 * 
	 * @param list
	 */
	public void setList(List<SingleExam> list) {
		this.data = list;
		notifyDataSetChanged();
	}
}
