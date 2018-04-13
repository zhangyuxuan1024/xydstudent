package com.xyd.student.xydexamanalysis.entity;

public class Casa_detail {
	private String courseName;
	private double score;
	private double classAvgScore;
	private double offset;

	public Casa_detail(String courseName, double score, double classAvgScore,
			double offset) {
		super();
		this.courseName = courseName;
		this.score = score;
		this.classAvgScore = classAvgScore;
		this.offset = offset;
	}

	public Casa_detail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getClassAvgScore() {
		return classAvgScore;
	}

	public void setClassAvgScore(double classAvgScore) {
		this.classAvgScore = classAvgScore;
	}

	public double getOffset() {
		return offset;
	}

	public void setOffset(double offset) {
		this.offset = offset;
	}

}
