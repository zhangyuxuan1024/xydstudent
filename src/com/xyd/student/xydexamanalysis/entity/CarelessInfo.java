package com.xyd.student.xydexamanalysis.entity;

public class CarelessInfo {

	private double scoreRate;
	private double carelessIndex;

	public CarelessInfo(double scoreRate, double carelessIndex) {
		super();
		this.scoreRate = scoreRate;
		this.carelessIndex = carelessIndex;
	}

	public CarelessInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public double getScoreRate() {
		return scoreRate;
	}

	public void setScoreRate(double scoreRate) {
		this.scoreRate = scoreRate;
	}

	public double getCarelessIndex() {
		return carelessIndex;
	}

	public void setCarelessIndex(double carelessIndex) {
		this.carelessIndex = carelessIndex;
	}

}
