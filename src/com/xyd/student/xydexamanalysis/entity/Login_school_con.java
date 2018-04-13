package com.xyd.student.xydexamanalysis.entity;

import java.util.ArrayList;

public class Login_school_con {
	String schoolNameRegular;
	ArrayList<Login_school> schoollist;
	int resultCode;
	String resultDesc;

	public Login_school_con() {
		super();
	}

	public Login_school_con(String schoolNameRegular,
			ArrayList<Login_school> schoollist, int resultCode,
			String resultDesc) {
		super();
		this.schoolNameRegular = schoolNameRegular;
		this.schoollist = schoollist;
		this.resultCode = resultCode;
		this.resultDesc = resultDesc;
	}

	/**
	 * @return the schoolNameRegular
	 */
	public String getSchoolNameRegular() {
		return schoolNameRegular;
	}

	/**
	 * @param schoolNameRegular
	 *            the schoolNameRegular to set
	 */
	public void setSchoolNameRegular(String schoolNameRegular) {
		this.schoolNameRegular = schoolNameRegular;
	}

	/**
	 * @return the schoollist
	 */
	public ArrayList<Login_school> getSchoollist() {
		return schoollist;
	}

	/**
	 * @param schoollist
	 *            the schoollist to set
	 */
	public void setSchoollist(ArrayList<Login_school> schoollist) {
		this.schoollist = schoollist;
	}

	/**
	 * @return the resultCode
	 */
	public int getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode
	 *            the resultCode to set
	 */
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * @return the resultDesc
	 */
	public String getResultDesc() {
		return resultDesc;
	}

	/**
	 * @param resultDesc
	 *            the resultDesc to set
	 */
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

}
