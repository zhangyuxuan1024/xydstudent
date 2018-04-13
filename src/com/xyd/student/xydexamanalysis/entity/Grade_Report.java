package com.xyd.student.xydexamanalysis.entity;

import java.util.ArrayList;

public class Grade_Report {
	private ArrayList<Grade_CourseScore> courselist;
	private ArrayList<Casa_detail> casaList;
	private ArrayList<Gasa_detal> gasaList;
	private UnbalanceInfo unbalanceInfo;
	private String courseMark;

	public String getCourseMark() {
		return courseMark;
	}

	public void setCourseMark(String courseMark) {
		this.courseMark = courseMark;
	}

	public Grade_Report() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UnbalanceInfo getUnbalanceInfo() {
		return unbalanceInfo;
	}

	public void setUnbalanceInfo(UnbalanceInfo unbalanceInfo) {
		this.unbalanceInfo = unbalanceInfo;
	}

	public Grade_Report(ArrayList<Grade_CourseScore> courselist,
			ArrayList<Casa_detail> casaList, ArrayList<Gasa_detal> gasaList,
			UnbalanceInfo unbalanceInfo, String courseMark) {
		super();
		this.courselist = courselist;
		this.casaList = casaList;
		this.gasaList = gasaList;
		this.unbalanceInfo = unbalanceInfo;
		this.courseMark = courseMark;
	}

	public ArrayList<Gasa_detal> getGasaList() {
		return gasaList;
	}

	public void setGasaList(ArrayList<Gasa_detal> gasaList) {
		this.gasaList = gasaList;
	}

	public ArrayList<Casa_detail> getCasaList() {
		return casaList;
	}

	public void setCasaList(ArrayList<Casa_detail> casaList) {
		this.casaList = casaList;
	}

	public ArrayList<Grade_CourseScore> getCourselist() {
		return courselist;
	}

	public void setCourselist(ArrayList<Grade_CourseScore> courselist) {
		this.courselist = courselist;
	}

}
