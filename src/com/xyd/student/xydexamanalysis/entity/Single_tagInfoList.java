package com.xyd.student.xydexamanalysis.entity;

public class Single_tagInfoList {

	private int seId;
	private String tagName;
	private int count;
	private double fullScore;
	private double score;
	private double classScore;
	private double gradeScore;
	private double examScore;
	private double scoreRate;
	private double classScoreRate;
	private double gradeScoreRate;
	private double examScoreRate;
	private int flag;

	public Single_tagInfoList() {
		super();
	}

	public Single_tagInfoList(int seId, String tagName, int count,
			double fullScore, double score, double classScore,
			double gradeScore, double examScore, double scoreRate,
			double classScoreRate, double gradeScoreRate, double examScoreRate,
			int flag) {
		super();
		this.seId = seId;
		this.tagName = tagName;
		this.count = count;
		this.fullScore = fullScore;
		this.score = score;
		this.classScore = classScore;
		this.gradeScore = gradeScore;
		this.examScore = examScore;
		this.scoreRate = scoreRate;
		this.classScoreRate = classScoreRate;
		this.gradeScoreRate = gradeScoreRate;
		this.examScoreRate = examScoreRate;
		this.flag = flag;
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
	 * @return the tagName
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * @param tagName
	 *            the tagName to set
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the fullScore
	 */
	public double getFullScore() {
		return fullScore;
	}

	/**
	 * @param fullScore
	 *            the fullScore to set
	 */
	public void setFullScore(double fullScore) {
		this.fullScore = fullScore;
	}

	/**
	 * @return the score
	 */
	public double getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(double score) {
		this.score = score;
	}

	/**
	 * @return the classScore
	 */
	public double getClassScore() {
		return classScore;
	}

	/**
	 * @param classScore
	 *            the classScore to set
	 */
	public void setClassScore(double classScore) {
		this.classScore = classScore;
	}

	/**
	 * @return the gradeScore
	 */
	public double getGradeScore() {
		return gradeScore;
	}

	/**
	 * @param gradeScore
	 *            the gradeScore to set
	 */
	public void setGradeScore(double gradeScore) {
		this.gradeScore = gradeScore;
	}

	/**
	 * @return the examScore
	 */
	public double getExamScore() {
		return examScore;
	}

	/**
	 * @param examScore
	 *            the examScore to set
	 */
	public void setExamScore(double examScore) {
		this.examScore = examScore;
	}

	/**
	 * @return the scoreRate
	 */
	public double getScoreRate() {
		return scoreRate;
	}

	/**
	 * @param scoreRate
	 *            the scoreRate to set
	 */
	public void setScoreRate(double scoreRate) {
		this.scoreRate = scoreRate;
	}

	/**
	 * @return the classScoreRate
	 */
	public double getClassScoreRate() {
		return classScoreRate;
	}

	/**
	 * @param classScoreRate
	 *            the classScoreRate to set
	 */
	public void setClassScoreRate(double classScoreRate) {
		this.classScoreRate = classScoreRate;
	}

	/**
	 * @return the gradeScoreRate
	 */
	public double getGradeScoreRate() {
		return gradeScoreRate;
	}

	/**
	 * @param gradeScoreRate
	 *            the gradeScoreRate to set
	 */
	public void setGradeScoreRate(double gradeScoreRate) {
		this.gradeScoreRate = gradeScoreRate;
	}

	/**
	 * @return the examScoreRate
	 */
	public double getExamScoreRate() {
		return examScoreRate;
	}

	/**
	 * @param examScoreRate
	 *            the examScoreRate to set
	 */
	public void setExamScoreRate(double examScoreRate) {
		this.examScoreRate = examScoreRate;
	}

	/**
	 * @return the flag
	 */
	public int getFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}

}
