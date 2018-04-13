package com.xyd.student.xydexamanalysis.entity.Exam;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class PayInfo implements Serializable {
	private int payStatus;

	public void parserJson(JSONObject json) {
		if (json != null) {
			try {
				payStatus = json.getInt("payStatus");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the payStatus
	 */
	public int getPayStatus() {
		return payStatus;
	}

	/**
	 * @param payStatus
	 *            the payStatus to set
	 */
	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

}
