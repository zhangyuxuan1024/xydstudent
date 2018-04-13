package com.xyd.student.xydexamanalysis.entity.weike;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeiRecord implements Serializable {
	private List<WeiRecord> list;
	/*
	 * "id": "19", "indbtime": "2016-05-25 12:02:51.0", "isCollected": 0,
	 * "schoolId": 11270, "userId": "BC1009125865", "userType": 2, "wkName":
	 * "初三夏010-9-3_20151220225408", "wkUrl":
	 * "http://weike.iclassmate.cn/WeiKe/detail.htm?entityIds=1349"
	 */
	private String id;
	private String indbtime;
	private String isCollected;
	private String schoolId;
	private String userId;
	private String userType;
	private String wkName;
	private String wkUrl;
	private boolean isClickEdit;
	private boolean isClickLike;
	private boolean isSave;

	public void parserJson(JSONArray array) {
		if (array != null && array.length() > 0) {
			list = new ArrayList<WeiRecord>();
			for (int i = 0; i < array.length(); i++) {
				WeiRecord wr = new WeiRecord();
				try {
					JSONObject object = array.getJSONObject(i);
					id = object.getString("id");
					indbtime = object.getString("indbtime");
					isCollected = object.getString("isCollected");
					schoolId = object.getString("schoolId");
					userId = object.getString("userId");
					userType = object.getString("userType");
					wkName = object.getString("wkName");
					wkUrl = object.getString("wkUrl");
					wr.setId(id);
					wr.setIndbtime(indbtime);
					wr.setIsCollected(isCollected);
					wr.setSchoolId(schoolId);
					wr.setUserId(userId);
					wr.setUserType(userType);
					wr.setWkName(wkName);
					wr.setWkUrl(wkUrl);
					list.add(wr);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public List<WeiRecord> getList() {
		return list;
	}

	public void setList(List<WeiRecord> list) {
		this.list = list;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIndbtime() {
		return indbtime;
	}

	public void setIndbtime(String indbtime) {
		this.indbtime = indbtime;
	}

	public String getIsCollected() {
		return isCollected;
	}

	public void setIsCollected(String isCollected) {
		this.isCollected = isCollected;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getWkName() {
		return wkName;
	}

	public void setWkName(String wkName) {
		this.wkName = wkName;
	}

	public String getWkUrl() {
		return wkUrl;
	}

	public void setWkUrl(String wkUrl) {
		this.wkUrl = wkUrl;
	}

	public boolean isClickEdit() {
		return isClickEdit;
	}

	public void setClickEdit(boolean isClickEdit) {
		this.isClickEdit = isClickEdit;
	}

	public boolean isClickLike() {
		return isClickLike;
	}

	public void setClickLike(boolean isClickLike) {
		this.isClickLike = isClickLike;
	}

	public boolean isSave() {
		return isSave;
	}

	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}

}
