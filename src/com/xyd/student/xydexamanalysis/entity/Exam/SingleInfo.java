package com.xyd.student.xydexamanalysis.entity.Exam;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class SingleInfo implements Serializable {
	/*
	 * "isReaded": 0, "meId": 19, "personalScore": 55, "seDate": "2016-05-04",
	 * "seFullScore": 80, "seId": 100, "seName": "2016年海淀区九年级第二学期期中练习_化学"
	 */
	private String classId;
	private int courseId;
	private int isReaded;
	private int meId;
	private int seId;
	private double personalScore;
	private int seFullScore;
	private PayInfo payInfo;

	public void parserJson(JSONObject json) {
		if (json != null) {
			try {
				classId = json.getString("classId");
				courseId = json.getInt("courseId");
				isReaded = json.getInt("isReaded");
				meId = json.getInt("meId");
				seId = json.getInt("seId");
				personalScore = json.optDouble("personalScore");
				seFullScore = json.optInt("seFullScore");

				JSONObject jsonObject = json.getJSONObject("payInfo");
				if (jsonObject != null) {
					payInfo = new PayInfo();
					payInfo.parserJson(jsonObject);
					setPayInfo(payInfo);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
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

	/**
	 * @return the courseId
	 */
	public int getCourseId() {
		return courseId;
	}

	/**
	 * @param courseId
	 *            the courseId to set
	 */
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	/**
	 * @return the isReaded
	 */
	public int getIsReaded() {
		return isReaded;
	}

	/**
	 * @param isReaded
	 *            the isReaded to set
	 */
	public void setIsReaded(int isReaded) {
		this.isReaded = isReaded;
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
	 * @return the personalScore
	 */
	public double getPersonalScore() {
		return personalScore;
	}

	/**
	 * @param personalScore
	 *            the personalScore to set
	 */
	public void setPersonalScore(double personalScore) {
		this.personalScore = personalScore;
	}

	/**
	 * @return the seFullScore
	 */
	public int getSeFullScore() {
		return seFullScore;
	}

	/**
	 * @param seFullScore
	 *            the seFullScore to set
	 */
	public void setSeFullScore(int seFullScore) {
		this.seFullScore = seFullScore;
	}

	/**
	 * @return the payInfo
	 */
	public PayInfo getPayInfo() {
		return payInfo;
	}

	/**
	 * @param payInfo
	 *            the payInfo to set
	 */
	public void setPayInfo(PayInfo payInfo) {
		this.payInfo = payInfo;
	}

}
