package com.xyd.student.xydexamanalysis.entity;

import java.io.Serializable;

public class Login_school_config implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	private int asId;
	private String appId;
	private String appName;
	private int serverId;
	private String serverName;
	private int areaId;
	private String areaName;
	private int schoolId;
	private String schoolName;
	private String status;
	private String loginServUrl;
	private String fileServUrl;
	private String reportServUrl;
	private String weiKeServUrl;
	private String basecenterUrl;
	private String webUrl;
	private String digital_camplusUrl;

	public Login_school_config() {
		super();
	}

	public Login_school_config(int asId, String appId, String appName,
			int serverId, String serverName, int areaId, String areaName,
			int schoolId, String schoolName, String status,
			String loginServUrl, String fileServUrl, String reportServUrl,
			String weiKeServUrl) {
		super();
		this.asId = asId;
		this.appId = appId;
		this.appName = appName;
		this.serverId = serverId;
		this.serverName = serverName;
		this.areaId = areaId;
		this.areaName = areaName;
		this.schoolId = schoolId;
		this.schoolName = schoolName;
		this.status = status;
		this.loginServUrl = loginServUrl;
		this.fileServUrl = fileServUrl;
		this.reportServUrl = reportServUrl;
		this.weiKeServUrl = weiKeServUrl;
	}

	public String getBasecenterUrl() {
		return basecenterUrl;
	}

	public void setBasecenterUrl(String basecenterUrl) {
		this.basecenterUrl = basecenterUrl;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	/**
	 * @return the asId
	 */
	public int getAsId() {
		return asId;
	}

	/**
	 * @param asId
	 *            the asId to set
	 */
	public void setAsId(int asId) {
		this.asId = asId;
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
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 *            the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the serverId
	 */
	public int getServerId() {
		return serverId;
	}

	/**
	 * @param serverId
	 *            the serverId to set
	 */
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	/**
	 * @return the serverName
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * @param serverName
	 *            the serverName to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * @return the areaId
	 */
	public int getAreaId() {
		return areaId;
	}

	/**
	 * @param areaId
	 *            the areaId to set
	 */
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName
	 *            the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
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
	 * @return the schoolName
	 */
	public String getSchoolName() {
		return schoolName;
	}

	/**
	 * @param schoolName
	 *            the schoolName to set
	 */
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the loginServUrl
	 */
	public String getLoginServUrl() {
		return loginServUrl;
	}

	/**
	 * @param loginServUrl
	 *            the loginServUrl to set
	 */
	public void setLoginServUrl(String loginServUrl) {
		this.loginServUrl = loginServUrl;
	}

	/**
	 * @return the fileServUrl
	 */
	public String getFileServUrl() {
		return fileServUrl;
	}

	/**
	 * @param fileServUrl
	 *            the fileServUrl to set
	 */
	public void setFileServUrl(String fileServUrl) {
		this.fileServUrl = fileServUrl;
	}

	/**
	 * @return the reportServUrl
	 */
	public String getReportServUrl() {
		return reportServUrl;
	}

	/**
	 * @param reportServUrl
	 *            the reportServUrl to set
	 */
	public void setReportServUrl(String reportServUrl) {
		this.reportServUrl = reportServUrl;
	}

	/**
	 * @return the weiKeServUrl
	 */
	public String getWeiKeServUrl() {
		return weiKeServUrl;
	}

	/**
	 * @param weiKeServUrl
	 *            the weiKeServUrl to set
	 */
	public void setWeiKeServUrl(String weiKeServUrl) {
		this.weiKeServUrl = weiKeServUrl;
	}

	public String getDigital_camplusUrl() {
		return digital_camplusUrl;
	}

	public void setDigital_camplusUrl(String digital_camplusUrl) {
		this.digital_camplusUrl = digital_camplusUrl;
	}

}