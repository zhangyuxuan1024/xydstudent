package com.xyd.student.xydexamanalysis.entity;

import java.io.Serializable;

import android.text.SpannableStringBuilder;

/**
 * Created by Lichg.
 */
/****
 * 单科名称 a.se_course_name 单科分 b.ess_score 单科班排前N名 b.ess_class_order 单科年排前N名
 * b.ess_grade_order 单科最高分 a.se_max_score 单科平均分 a.se_avg_score
 * ****/

public class SingleReport {
	private String se_course_name;
	private String Se_info;

	private int course_id;
	private String course_name;
	private double course_score;

	private SpannableStringBuilder Se_info_s;
	private int meId;
	private int seId;
	private String classId;

	private PayInfo payInfo;

	public SingleReport() {
		super();
	}

	public SingleReport(String se_course_name, int course_id,
			String course_name, double course_score, String se_info,
			SpannableStringBuilder Se_info_s, int meId, int seId,
			String classId, PayInfo payInfo) {
		super();
		this.se_course_name = se_course_name;
		this.course_id = course_id;
		this.course_name = course_name;
		this.course_score = course_score;
		this.Se_info = se_info;
		this.Se_info_s = Se_info_s;
		this.meId = meId;
		this.seId = seId;
		this.classId = classId;

		this.payInfo = payInfo;
	}

	public int getCourse_id() {
		return course_id;
	}

	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	/**
	 * @return the course_score
	 */
	public double getCourse_score() {
		return course_score;
	}

	/**
	 * @param course_score
	 *            the course_score to set
	 */
	public void setCourse_score(double course_score) {
		this.course_score = course_score;
	}

	public PayInfo getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(PayInfo payInfo) {
		this.payInfo = payInfo;
	}

	public String getSe_course_name() {
		return se_course_name;
	}

	public void setSe_course_name(String se_course_name) {
		this.se_course_name = se_course_name;
	}

	public String getSe_info() {
		return Se_info;
	}

	public void setSe_info(String se_info) {
		Se_info = se_info;
	}

	/**
	 * @return the meId
	 */
	public int getMeId() {
		return meId;
	}

	/**
	 * @return the se_info_s
	 */
	public SpannableStringBuilder getSe_info_s() {
		return Se_info_s;
	}

	/**
	 * @param se_info_s
	 *            the se_info_s to set
	 */
	public void setSe_info_s(SpannableStringBuilder se_info_s) {
		Se_info_s = se_info_s;
	}

	/**
	 * @param meId
	 *            the meId to set
	 */
	public void setMeId(int meId) {
		this.meId = meId;
	}

	/**
	 * @return the seId
	 */
	public int getSeId() {
		return seId;
	}

	/**
	 * @param seId
	 *            the seId to set
	 */
	public void setSeId(int seId) {
		this.seId = seId;
	}

	/**
	 * @return the classId
	 */
	public String getClassId() {
		return classId;
	}

	/**
	 * @param classId
	 *            the classId to set
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SingleReport [se_course_name=" + se_course_name + ", Se_info="
				+ Se_info + ", course_id=" + course_id + ", course_name="
				+ course_name + ", course_score=" + course_score
				+ ", Se_info_s=" + Se_info_s + ", meId=" + meId + ", seId="
				+ seId + ", classId=" + classId + ", payInfo=" + payInfo + "]";
	}

}
