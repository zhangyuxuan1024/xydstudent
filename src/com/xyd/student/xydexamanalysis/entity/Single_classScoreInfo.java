package com.xyd.student.xydexamanalysis.entity;

public class Single_classScoreInfo {

	private String label;
	private double minScore;
	private double maxScore;
	private int count;
	private int isMyFlag;
	private String groupFlag;

	public Single_classScoreInfo() {
		super();
	}

	public Single_classScoreInfo(String label, double minScore,
			double maxScore, int count, int isMyFlag, String groupFlag) {
		super();
		this.label = label;
		this.minScore = minScore;
		this.maxScore = maxScore;
		this.count = count;
		this.isMyFlag = isMyFlag;
		this.groupFlag = groupFlag;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the minScore
	 */
	public double getMinScore() {
		return minScore;
	}

	/**
	 * @param minScore
	 *            the minScore to set
	 */
	public void setMinScore(double minScore) {
		this.minScore = minScore;
	}

	/**
	 * @return the maxScore
	 */
	public double getMaxScore() {
		return maxScore;
	}

	/**
	 * @param maxScore
	 *            the maxScore to set
	 */
	public void setMaxScore(double maxScore) {
		this.maxScore = maxScore;
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
	 * @return the isMyFlag
	 */
	public int getIsMyFlag() {
		return isMyFlag;
	}

	/**
	 * @param isMyFlag
	 *            the isMyFlag to set
	 */
	public void setIsMyFlag(int isMyFlag) {
		this.isMyFlag = isMyFlag;
	}

	/**
	 * @return the groupFlag
	 */
	public String getGroupFlag() {
		return groupFlag;
	}

	/**
	 * @param groupFlag
	 *            the groupFlag to set
	 */
	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}
}
