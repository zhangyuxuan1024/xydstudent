package com.xyd.student.xydexamanalysis.utils;

/**
 * Created by Lichg.
 */
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;

import com.google.gson.Gson;

public class GsonUtil {

	public static <T> T jsonToBean(String json, Class<T> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(json, clazz);
	}

	public static boolean checkJson(String json) {
		boolean isSuccess = false;
		try {
			JSONObject jsonObject = new JSONObject(json);
			isSuccess = jsonObject.optBoolean("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return isSuccess;
	}

}
