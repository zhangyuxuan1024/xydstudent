package com.xyd.student.xydexamanalysis.entity.Exam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ExamInfo implements Serializable {
	/*
	 * "isReaded": 0, "meDate": "2016-05-04 00:00:00.0", "meFullScore": 900,
	 * "meId": 19, "meName": "2016年海淀区九年级第二学期期中练习", "personalScore": 428,
	 */
	private String meDate;
	private int meId;
	private String meName;
	private List<SingleInfo> list;

	public void parserJson(JSONObject json) {
		if (json != null) {
			try {
				meDate = json.getString("meDate");
				meId = json.getInt("meId");
				meName = json.getString("meName");
				List<SingleInfo> list = new ArrayList<SingleInfo>();
				JSONArray array = json.getJSONArray("studentExamDetailList");
				if (array != null && array.length() > 0) {
					for (int j = 0; j < array.length(); j++) {
						JSONObject json2 = array.getJSONObject(j);
						SingleInfo info = new SingleInfo();
						info.parserJson(json2);
						list.add(info);
					}
					setList(list);
				}
			} catch (Exception e) {

			}
		}
	}

	/**
	 * @return the meDate
	 */
	public String getMeDate() {
		return meDate;
	}

	/**
	 * @param meDate
	 *            the meDate to set
	 */
	public void setMeDate(String meDate) {
		this.meDate = meDate;
	}

	/**
	 * @return the meId
	 */
	public int getMeId() {
		return meId;
	}

	/**
	 * @param meId
	 *            the meId to set
	 */
	public void setMeId(int meId) {
		this.meId = meId;
	}

	/**
	 * @return the meName
	 */
	public String getMeName() {
		return meName;
	}

	/**
	 * @param meName
	 *            the meName to set
	 */
	public void setMeName(String meName) {
		this.meName = meName;
	}

	/**
	 * @return the list
	 */
	public List<SingleInfo> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<SingleInfo> list) {
		this.list = list;
	}

}
