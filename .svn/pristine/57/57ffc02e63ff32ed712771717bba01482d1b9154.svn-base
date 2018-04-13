package com.xyd.student.xydexamanalysis.view;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;

/**
 * Custom implementation of the MarkerView.
 * 
 * @author Philipp Jahoda
 */
public class MyMarkerView extends MarkerView {

	private TextView tv1, tv2;
	private ArrayList<String> knowledge_point;
	private ArrayList<Float> userList, classList, gradeList, examList;
	private String studentName;
	private int indext, indextold;
	private DecimalFormat decimalFormat;

	public MyMarkerView(Context context, int layoutResource, TextView tv1,
			TextView tv2, String studentName,
			ArrayList<String> knowledge_point, ArrayList<Float> userList,
			ArrayList<Float> classList, ArrayList<Float> gradeList,
			ArrayList<Float> examList) {
		super(context, layoutResource);

		this.studentName = studentName;
		this.knowledge_point = knowledge_point;
		this.userList = userList;
		this.classList = classList;
		this.gradeList = gradeList;
		this.examList = examList;
		this.indext = 0;
		this.indextold = 0;
		this.tv1 = tv1;
		this.tv2 = tv2;

		this.decimalFormat = new DecimalFormat("###,###,###,##0" + ".00");

		tv1.setText(knowledge_point.get(indext));
		tv2.setText(studentName + ":"
				+ decimalFormat.format(userList.get(indext) * 100) + "%"
				+ " 班级:" + decimalFormat.format(classList.get(indext) * 100)
				+ "%" + " 年级:"
				+ decimalFormat.format(gradeList.get(indext) * 100) + "%"
				+ " 考试:" + decimalFormat.format(examList.get(indext) * 100)
				+ "%");

	}

	// callbacks everytime the MarkerView is redrawn, can be used to update the
	// content (user-interface)
	@Override
	public void refreshContent(Entry e, Highlight highlight) {

		if (e instanceof CandleEntry) {

			CandleEntry ce = (CandleEntry) e;

			tv1.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
		} else {
			indext = e.getXIndex();
			if (indextold != indext) {
				tv1.setText(knowledge_point.get(indext));
				tv2.setText(studentName + ":"
						+ decimalFormat.format(userList.get(indext) * 100)
						+ "%" + " 班级:"
						+ decimalFormat.format(classList.get(indext) * 100)
						+ "%" + " 年级:"
						+ decimalFormat.format(gradeList.get(indext) * 100)
						+ "%" + " 考试:"
						+ decimalFormat.format(examList.get(indext) * 100)
						+ "%");
				indextold = indext;
			}
		}
	}

	@Override
	public int getXOffset() {
		// TODO Auto-generated method stub
		return -(getWidth() / 2);
	}

	@Override
	public int getYOffset() {
		// TODO Auto-generated method stub
		return -getHeight();
	}
}
