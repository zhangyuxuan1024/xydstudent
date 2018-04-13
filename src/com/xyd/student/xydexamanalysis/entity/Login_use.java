package com.xyd.student.xydexamanalysis.entity;

public class Login_use {
	private int schoolId;
	private String userCode;
	private String userName;
	private String userId;
	private String appId;
	private String urlPath;

	public Login_use() {
		super();
	}

	public Login_use(int schoolId, String userCode, String userName,
			String userId, String appId, String urlPath) {
		super();
		this.schoolId = schoolId;
		this.userCode = userCode;
		this.userName = userName;
		this.userId = userId;
		this.appId = appId;
		this.urlPath = urlPath;
	}

	/**
	 * @return the schoolId
	 */
	public int getSchoolId() {
		return schoolId;
	}

	/**
	 * @param schoolId
	 *            the schoolId to set
	 */
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	/**
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode
	 *            the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId
	 *            the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return the urlPath
	 */
	public String getUrlPath() {
		return urlPath;
	}

	/**
	 * @param urlPath
	 *            the urlPath to set
	 */
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

}