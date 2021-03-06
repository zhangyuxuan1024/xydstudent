package com.xyd.student.xydexamanalysis.entity;

import java.util.ArrayList;

public class Single_subject_scores {
	private String personAnalysisInfo;
	private ArrayList<Single_classScoreInfo> classScoreInfoList;
	private String qstTagAnalysisInfo;
	private ArrayList<Single_tagInfoList> tagInfoList;
	private ArrayList<videoList> videoList;
	private String carelessInfo_courseName;
	private String carelessInfo_markInfo;
	private double carelessInfo_scoreRate;
	private double carelessInfo_carelessIndex;
	private ArrayList<Single_scoreList> scoreList;
	private ArrayList<Single_pageList> pageList;
	private ArrayList<CarelessInfo> carelessList;
	private double score;

	public Single_subject_scores() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Single_subject_scores(
			String personAnalysisInfo,
			ArrayList<Single_classScoreInfo> classScoreInfoList,
			String qstTagAnalysisInfo,
			ArrayList<Single_tagInfoList> tagInfoList,
			ArrayList<com.xyd.student.xydexamanalysis.entity.videoList> videoList,
			String carelessInfo_courseName, String carelessInfo_markInfo,
			double carelessInfo_scoreRate, double carelessInfo_carelessIndex,
			ArrayList<Single_scoreList> scoreList,
			ArrayList<Single_pageList> pageList,
			ArrayList<CarelessInfo> carelessList, double score) {
		super();
		this.personAnalysisInfo = personAnalysisInfo;
		this.classScoreInfoList = classScoreInfoList;
		this.qstTagAnalysisInfo = qstTagAnalysisInfo;
		this.tagInfoList = tagInfoList;
		this.videoList = videoList;
		this.carelessInfo_courseName = carelessInfo_courseName;
		this.carelessInfo_markInfo = carelessInfo_markInfo;
		this.carelessInfo_scoreRate = carelessInfo_scoreRate;
		this.carelessInfo_carelessIndex = carelessInfo_carelessIndex;
		this.scoreList = scoreList;
		this.pageList = pageList;
		this.carelessList = carelessList;
		this.score = score;
	}

	public ArrayList<CarelessInfo> getCarelessList() {
		return carelessList;
	}

	public void setCarelessList(ArrayList<CarelessInfo> carelessList) {
		this.carelessList = carelessList;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	/**
	 * @return the personAnalysisInfo
	 */
	public String getPersonAnalysisInfo() {
		return personAnalysisInfo;
	}

	/**
	 * @param personAnalysisInfo
	 *            the personAnalysisInfo to set
	 */
	public void setPersonAnalysisInfo(String personAnalysisInfo) {
		this.personAnalysisInfo = personAnalysisInfo;
	}

	/**
	 * @return the classScoreInfoList
	 */
	public ArrayList<Single_classScoreInfo> getClassScoreInfoList() {
		return classScoreInfoList;
	}

	/**
	 * @param classScoreInfoList
	 *            the classScoreInfoList to set
	 */
	public void setClassScoreInfoList(
			ArrayList<Single_classScoreInfo> classScoreInfoList) {
		this.classScoreInfoList = classScoreInfoList;
	}

	/**
	 * @return the qstTagAnalysisInfo
	 */
	public String getQstTagAnalysisInfo() {
		return qstTagAnalysisInfo;
	}

	/**
	 * @param qstTagAnalysisInfo
	 *            the qstTagAnalysisInfo to set
	 */
	public void setQstTagAnalysisInfo(String qstTagAnalysisInfo) {
		this.qstTagAnalysisInfo = qstTagAnalysisInfo;
	}

	/**
	 * @return the tagInfoList
	 */
	public ArrayList<Single_tagInfoList> getTagInfoList() {
		return tagInfoList;
	}

	/**
	 * @param tagInfoList
	 *            the tagInfoList to set
	 */
	public void setTagInfoList(ArrayList<Single_tagInfoList> tagInfoList) {
		this.tagInfoList = tagInfoList;
	}

	/**
	 * @return the carelessInfo_courseName
	 */
	public String getCarelessInfo_courseName() {
		return carelessInfo_courseName;
	}

	/**
	 * @param carelessInfo_courseName
	 *            the carelessInfo_courseName to set
	 */
	public void setCarelessInfo_courseName(String carelessInfo_courseName) {
		this.carelessInfo_courseName = carelessInfo_courseName;
	}

	/**
	 * @return the carelessInfo_markInfo
	 */
	public String getCarelessInfo_markInfo() {
		return carelessInfo_markInfo;
	}

	/**
	 * @param carelessInfo_markInfo
	 *            the carelessInfo_markInfo to set
	 */
	public void setCarelessInfo_markInfo(String carelessInfo_markInfo) {
		this.carelessInfo_markInfo = carelessInfo_markInfo;
	}

	/**
	 * @return the carelessInfo_scoreRate
	 */
	public double getCarelessInfo_scoreRate() {
		return carelessInfo_scoreRate;
	}

	/**
	 * @param carelessInfo_scoreRate
	 *            the carelessInfo_scoreRate to set
	 */
	public void setCarelessInfo_scoreRate(double carelessInfo_scoreRate) {
		this.carelessInfo_scoreRate = carelessInfo_scoreRate;
	}

	/**
	 * @return the carelessInfo_carelessIndex
	 */
	public double getCarelessInfo_carelessIndex() {
		return carelessInfo_carelessIndex;
	}

	/**
	 * @param carelessInfo_carelessIndex
	 *            the carelessInfo_carelessIndex to set
	 */
	public void setCarelessInfo_carelessIndex(double carelessInfo_carelessIndex) {
		this.carelessInfo_carelessIndex = carelessInfo_carelessIndex;
	}

	/**
	 * @return the scoreList
	 */
	public ArrayList<Single_scoreList> getScoreList() {
		return scoreList;
	}

	/**
	 * @param scoreList
	 *            the scoreList to set
	 */
	public void setScoreList(ArrayList<Single_scoreList> scoreList) {
		this.scoreList = scoreList;
	}

	/**
	 * @return the pageList
	 */
	public ArrayList<Single_pageList> getPageList() {
		return pageList;
	}

	/**
	 * @param pageList
	 *            the pageList to set
	 */
	public void setPageList(ArrayList<Single_pageList> pageList) {
		this.pageList = pageList;
	}

	/**
	 * @return the videoList
	 */
	public ArrayList<videoList> getVideoList() {
		return videoList;
	}

	/**
	 * @param videoList
	 *            the videoList to set
	 */
	public void setVideoList(ArrayList<videoList> videoList) {
		this.videoList = videoList;
	}

	@Override
	public String toString() {
		return "Single_subject_scores [personAnalysisInfo="
				+ personAnalysisInfo + ", classScoreInfoList="
				+ classScoreInfoList + ", qstTagAnalysisInfo="
				+ qstTagAnalysisInfo + ", tagInfoList=" + tagInfoList
				+ ", videoList=" + videoList + ", carelessInfo_courseName="
				+ carelessInfo_courseName + ", carelessInfo_markInfo="
				+ carelessInfo_markInfo + ", carelessInfo_scoreRate="
				+ carelessInfo_scoreRate + ", carelessInfo_carelessIndex="
				+ carelessInfo_carelessIndex + ", scoreList=" + scoreList
				+ ", pageList=" + pageList + ", carelessList=" + carelessList
				+ ", score=" + score + ", getCarelessList()="
				+ getCarelessList() + ", getScore()=" + getScore()
				+ ", getPersonAnalysisInfo()=" + getPersonAnalysisInfo()
				+ ", getClassScoreInfoList()=" + getClassScoreInfoList()
				+ ", getQstTagAnalysisInfo()=" + getQstTagAnalysisInfo()
				+ ", getTagInfoList()=" + getTagInfoList()
				+ ", getCarelessInfo_courseName()="
				+ getCarelessInfo_courseName()
				+ ", getCarelessInfo_markInfo()=" + getCarelessInfo_markInfo()
				+ ", getCarelessInfo_scoreRate()="
				+ getCarelessInfo_scoreRate()
				+ ", getCarelessInfo_carelessIndex()="
				+ getCarelessInfo_carelessIndex() + ", getScoreList()="
				+ getScoreList() + ", getPageList()=" + getPageList()
				+ ", getVideoList()=" + getVideoList() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
