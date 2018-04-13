package com.xyd.student.xydexamanalysis.entity.Exam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;

public class ExamInfos implements Serializable {
	private List<ExamInfo> list;
	private int resultCode;
	private String resultDesc;

	public void parserJson(String result) {
		JSONObject jsonObject = null;
		if (result != null) {
			try {
				jsonObject = new JSONObject(result);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			if (jsonObject != null) {
				try {
					list = new ArrayList<ExamInfo>();
					JSONArray arrays = jsonObject
							.getJSONArray("studentExamList");
					if (arrays != null) {
						for (int i = 0; i < arrays.length(); i++) {
							ExamInfo info = new ExamInfo();
							JSONObject json = arrays.getJSONObject(i);
							info.parserJson(json);
							list.add(info);
						}
						setList(list);
					}
					resultCode = jsonObject.getInt("resultCode");
					resultDesc = jsonObject.getString("resultDesc");
				} catch (Exception e) {

				}
			}
		}
	}

	/**
	 * @return the list
	 */
	public List<ExamInfo> getList() {
		int unread_index = 0;
		if (list != null && list.size() > 1) {
			for (int i = 0; i < list.size(); i++) {
				ExamInfo info = list.get(i);
				boolean ret = isUnread(info.getList());
				if (ret) {
					list.remove(i);
					list.add(unread_index, info);
					unread_index++;
				}
			}
		}
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<ExamInfo> list) {
		this.list = list;
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

	private boolean isUnread(List<SingleInfo> data) {
		boolean ret = false;
		if (data != null) {
			for (int i = 0; i < data.size(); i++) {
				SingleInfo info = data.get(i);
				if (info.getIsReaded() == 0) {
					ret = true;
					break;
				}
			}
		}
		return ret;
	}
}
