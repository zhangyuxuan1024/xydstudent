package com.xyd.student.xydexamanalysis.entity;

import java.util.ArrayList;

public class UnbalanceInfo {
	private String markInfo;
	private int avgDegreeRate;
	private ArrayList<Unbalance_detail> unbalanceList;

	public UnbalanceInfo(String markInfo, int avgDegreeRate,
			ArrayList<Unbalance_detail> unbalanceList) {
		super();
		this.markInfo = markInfo;
		this.avgDegreeRate = avgDegreeRate;
		this.unbalanceList = unbalanceList;
	}

	public UnbalanceInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMarkInfo() {
		return markInfo;
	}

	public void setMarkInfo(String markInfo) {
		this.markInfo = markInfo;
	}

	public int getAvgDegreeRate() {
		return avgDegreeRate;
	}

	public void setAvgDegreeRate(int avgDegreeRate) {
		this.avgDegreeRate = avgDegreeRate;
	}

	public ArrayList<Unbalance_detail> getUnbalanceList() {
		return unbalanceList;
	}

	public void setUnbalanceList(ArrayList<Unbalance_detail> unbalanceList) {
		this.unbalanceList = unbalanceList;
	}

}
