package com.xyd.student.xydexamanalysis.entity;

import java.io.Serializable;

public class Explain_single_info implements Serializable {
	private String type;
	private int displayIndex;
	private double difficulty;
	// private int fullScore;
	// private int score;
	private double fullScore;
	private double score;
	private String wkId;
	private String wkName;
	// private String pathUrl;
	private String displayName;

	private String errorXywh;
	private String excellentXywh;
	private String prefixUrl;
	private String pageUrl;
	private String answerXywh;

	public Explain_single_info() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Explain_single_info(String type, int displayIndex,
			double difficulty, double fullScore, double score, String wkId,
			String wkName, /* String pathUrl, */String displayName,
			String errorXywh, String excellentXywh, String prefixUrl,
			String pageUrl, String answerXywh) {
		super();
		this.type = type;
		this.displayIndex = displayIndex;
		this.difficulty = difficulty;
		this.fullScore = fullScore;
		this.score = score;
		this.wkId = wkId;
		this.wkName = wkName;
		// this.pathUrl = pathUrl;
		this.displayName = displayName;

		this.errorXywh = errorXywh;
		this.excellentXywh = excellentXywh;
		this.prefixUrl = prefixUrl;
		this.pageUrl = pageUrl;
		this.answerXywh = answerXywh;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDisplayIndex() {
		return displayIndex;
	}

	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}

	public double getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(double difficulty) {
		this.difficulty = difficulty;
	}

	// public int getFullScore() {
	// return fullScore;
	// }
	// public void setFullScore(int fullScore) {
	// this.fullScore = fullScore;
	// }
	// public int getScore() {
	// return score;
	// }
	// public void setScore(int score) {
	// this.score = score;
	// }

	public String getWkId() {
		return wkId;
	}

	public double getFullScore() {
		return fullScore;
	}

	public void setFullScore(double fullScore) {
		this.fullScore = fullScore;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public void setWkId(String wkId) {
		this.wkId = wkId;
	}

	public String getWkName() {
		return wkName;
	}

	public void setWkName(String wkName) {
		this.wkName = wkName;
	}

	/*
	 * public String getPathUrl() { return pathUrl; } public void
	 * setPathUrl(String pathUrl) { this.pathUrl = pathUrl; }
	 */

	@Override
	public String toString() {
		return "Explain_single_info [type=" + type + ", displayIndex="
				+ displayIndex + ", difficulty=" + difficulty + ", fullScore="
				+ fullScore + ", score=" + score + ", wkId=" + wkId
				+ ", wkName=" + wkName + ", displayName=" + displayName + "]";
	}

	public String getErrorXywh() {
		return errorXywh;
	}

	public void setErrorXywh(String errorXywh) {
		this.errorXywh = errorXywh;
	}

	public String getExcellentXywh() {
		return excellentXywh;
	}

	public void setExcellentXywh(String excellentXywh) {
		this.excellentXywh = excellentXywh;
	}

	public String getPrefixUrl() {
		return prefixUrl;
	}

	public void setPrefixUrl(String prefixUrl) {
		this.prefixUrl = prefixUrl;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getAnswerXywh() {
		return answerXywh;
	}

	public void setAnswerXywh(String answerXywh) {
		this.answerXywh = answerXywh;
	}

}
