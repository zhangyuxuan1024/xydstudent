package com.xyd.student.xydexamanalysis.entity;

public class Grade_CourseScore {
	private String courseId;
	private String courseName;
	private double score;
	private double classAvgScore;
	private double gradeAvgScore;
	private int classPercentRate;
	private int scorePercentRate;

	public Grade_CourseScore(String courseId, String courseName, double score,
			double classAvgScore, double gradeAvgScore, int classPercentRate,
			int scorePercentRate) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.score = score;
		this.classAvgScore = classAvgScore;
		this.gradeAvgScore = gradeAvgScore;
		this.classPercentRate = classPercentRate;
		this.scorePercentRate = scorePercentRate;
	}

	public int getScorePercentRate() {
		return scorePercentRate;
	}

	public void setScorePercentRate(int scorePercentRate) {
		this.scorePercentRate = scorePercentRate;
	}

	public Grade_CourseScore() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
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

	public double getGradeAvgScore() {
		return gradeAvgScore;
	}

	public void setGradeAvgScore(double gradeAvgScore) {
		this.gradeAvgScore = gradeAvgScore;
	}

	public int getClassPercentRate() {
		return classPercentRate;
	}

	public void setClassPercentRate(int classPercentRate) {
		this.classPercentRate = classPercentRate;
	}

}
