package com.xyd.student.xydexamanalysis.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.xyd.student.xydexamanalysis.constant.Constants;
import com.xyd.student.xydexamanalysis.entity.Login_use;

/**
 * 系统登录工具类
 * 
 * @author Lichg
 * 
 */
public class LoginUtils {

	private LoginCallback callback;
	private String LoginUser;
	private String LoginPw;
	private String LoginUserId;
	private String studentid;
	private String studentname;
	private String classId;
	private int schoolId;
	private String FileServUrl;
	private String weiKeServUrl;
	private JSONObject param;
	private SharedPreferences msharedPreferences;

	public void setLoginCallback(LoginCallback cb) {
		this.callback = cb;
	}

	public LoginUtils() {
		super();
		this.msharedPreferences = UIUtils.getContext().getSharedPreferences(
				Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
	}

	public LoginUtils(LoginCallback callback) {
		super();
		this.callback = callback;
		this.msharedPreferences = UIUtils.getContext().getSharedPreferences(
				Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
	}

	/**
	 * 登录回调
	 */
	public interface LoginCallback {
		public void successMsg(String msg);

		public void errorMsg(String errorMsg);
	}

	/**
	 * 登录方法
	 * 
	 * @param userName
	 *            账号
	 * @param password
	 *            密码
	 */
	public void login(Context ctx, Login_use login) {
		/*
		 * param = new JSONObject(); try { param.put("type", "LoginReqInfo");
		 * param.put("schoolId", login.getSchoolId()); param.put("userCode",
		 * login.getUserCode()); param.put("userName", login.getUserName());
		 * param.put("userId", login.getUserId()); param.put("appId",
		 * login.getAppId()); param.put("urlPath", login.getUrlPath()); } catch
		 * (JSONException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		String loginurl = login.getUrlPath();
		String bodyparam = "schoolId=" + login.getSchoolId() + "&"
				+ "userCode=" + login.getUserCode() + "&" + "userName="
				+ login.getUserName() + "&" + "userId=" + login.getUserId()
				+ "&" + "appId=" + login.getAppId();

		Log.e("login url", "----loginurl:" + loginurl);
		Log.e("login bodyparam", "----bodyparam:" + bodyparam);

		List<NameValuePair> params = new ArrayList<NameValuePair>(); // 简单名称值对节点类NameValuePair
		int schoolId = login.getSchoolId();
		if (schoolId == 11424 || schoolId == 11423) {
			schoolId = 0;
		}
		params.add(new BasicNameValuePair("schoolId", String.valueOf(schoolId)));
		params.add(new BasicNameValuePair("userCode", login.getUserCode()));
		params.add(new BasicNameValuePair("userName", login.getUserName()));
		params.add(new BasicNameValuePair("userId", login.getUserId()));
		params.add(new BasicNameValuePair("appId", login.getAppId()));

		// Post请求方式
		MyHttpUtil httpUtil = MyHttpUtil.getInstance(ctx);
		httpUtil.requestMethPost(loginurl, params,
				new MyHttpUtil.HttpCallback() {
					@Override
					public void success(String result) {
						Log.e("Lichg", "--------登陆成功返回：" + result);
						if (callback != null) {
							callback.successMsg(result);
						}
					}

					@Override
					public void error(int state, String errorMsg) {
						if (callback != null) {
							callback.errorMsg(errorMsg);
						}
					}
				});
	}

	public String getLoginUser() {
		LoginUser = "studentAppCenter";
		return LoginUser;
	}

	public void setLoginUser(String use) {
		LoginUser = use;
	}

	public String getLoginPw() {
		LoginPw = "s7a3c5b5s5s6";
		return LoginPw;
	}

	public void setLoginPw(String loginpw) {
		LoginPw = loginpw;
	}

	public String getLoginUserId() {
		LoginUserId = msharedPreferences.getString("userId", "");
		return LoginUserId;
	}

	public void setLoginUserId(String use) {
		LoginUserId = use;
	}

	/**
	 * @return the classId
	 */
	public String getClassId() {
		return classId;
	}

	/**
	 * @param classId
	 *            the classId to set
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}

	/**
	 * @return the schoolId
	 */
	public int getSchoolId() {
		schoolId = msharedPreferences.getInt("schoolId", -1);
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
	 * @return the studentid
	 */
	public String getStudentid() {
		studentid = msharedPreferences.getString("userCode", "");
		return studentid;
	}

	/**
	 * @param studentid
	 *            the studentid to set
	 */
	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	/**
	 * @return the studentname
	 */
	public String getStudentname() {
		studentname = msharedPreferences.getString("userName", "");
		return studentname;
	}

	/**
	 * @param studentname
	 *            the studentname to set
	 */
	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}

	/**
	 * @return the fileServUrl
	 */
	public String getFileServUrl() {
		FileServUrl = msharedPreferences.getString("FileServUrl", "");
		return FileServUrl;
	}

	/**
	 * @param fileServUrl
	 *            the fileServUrl to set
	 */
	public void setFileServUrl(String fileServUrl) {
		this.FileServUrl = fileServUrl;
	}

	public String getWeiKeServUrl() {
		weiKeServUrl = msharedPreferences.getString("weiKeServUrl", "");
		return weiKeServUrl;
	}

	/**
	 * @param fileServUrl
	 *            the fileServUrl to set
	 */
	public void setWeiKeServUrl(String weiKeServUrl) {
		this.weiKeServUrl = weiKeServUrl;
	}

	/**
	 * 检测登录返回数据
	 * 
	 * @param backString
	 *            返回信息
	 */
	void checkBackState(String backString) {

	}

}
