package com.xyd.student.xydexamanalysis.entity;

public class Unbalance_detail {
	private String courseName;
	private int degreeRate;
	private int avgDegreeRate;
	private int degreeRateOffset;

	public Unbalance_detail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Unbalance_detail(String courseName, int degreeRate,
			int avgDegreeRate, int degreeRateOffset) {
		super();
		this.courseName = courseName;
		this.degreeRate = degreeRate;
		this.avgDegreeRate = avgDegreeRate;
		this.degreeRateOffset = degreeRateOffset;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getDegreeRate() {
		return degreeRate;
	}

	public void setDegreeRate(int degreeRate) {
		this.degreeRate = degreeRate;
	}

	public int getAvgDegreeRate() {
		return avgDegreeRate;
	}

	public void setAvgDegreeRate(int avgDegreeRate) {
		this.avgDegreeRate = avgDegreeRate;
	}

	public int getDegreeRateOffset() {
		return degreeRateOffset;
	}

	public void setDegreeRateOffset(int degreeRateOffset) {
		this.degreeRateOffset = degreeRateOffset;
	}

}
