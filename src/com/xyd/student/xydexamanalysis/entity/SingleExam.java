package com.xyd.student.xydexamanalysis.entity;

/**
 * Created by Lichg.
 */
import java.io.Serializable;
import java.util.ArrayList;

import android.text.SpannableStringBuilder;

public class SingleExam {
	private String exam_time;
	private String exam_name;
	private String exam_info;
	private SpannableStringBuilder exam_info_s;
	private int meId;
	private ArrayList<SingleReport> reportlist;

	public SingleExam() {
		super();
	}

	public SingleExam(String exam_time, String exam_name, String exam_info,
			ArrayList<SingleReport> reportlist, int meId,
			SpannableStringBuilder exam_info_s) {
		super();
		this.exam_time = exam_time;
		this.exam_name = exam_name;
		this.exam_info = exam_info;
		this.reportlist = reportlist;
		this.meId = meId;
		this.exam_info_s = exam_info_s;
	}

	public String getExam_time() {
		return exam_time;
	}

	public void setExam_time(String exam_time) {
		this.exam_time = exam_time;
	}

	public String getExam_name() {
		return exam_name;
	}

	public void setExam_name(String exam_name) {
		this.exam_name = exam_name;
	}

	public String getExam_info() {
		return exam_info;
	}

	public void setExam_info(String exam_info) {
		this.exam_info = exam_info;
	}

	public ArrayList<SingleReport> getReportlist() {
		return reportlist;
	}

	public void setReportlist(ArrayList<SingleReport> reportlist) {
		this.reportlist = reportlist;
	}

	/**
	 * @return the meId
	 */
	public int getMeId() {
		return meId;
	}

	/**
	 * @param meId
	 *            the meId to set
	 */
	public void setMeId(int meId) {
		this.meId = meId;
	}

	/**
	 * @return the exam_info_s
	 */
	public SpannableStringBuilder getExam_info_s() {
		return exam_info_s;
	}

	/**
	 * @param exam_info_s
	 *            the exam_info_s to set
	 */
	public void setExam_info_s(SpannableStringBuilder exam_info_s) {
		this.exam_info_s = exam_info_s;
	}

}
