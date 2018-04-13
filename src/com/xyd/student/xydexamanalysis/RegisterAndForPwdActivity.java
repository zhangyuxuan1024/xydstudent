package com.xyd.student.xydexamanalysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.xyd.student.xydexamanalysis.constant.Constants;
import com.xyd.student.xydexamanalysis.entity.Login;
import com.xyd.student.xydexamanalysis.ui.LoginActivity;
import com.xyd.student.xydexamanalysis.utils.MyHttpUtil;
import com.xyd.student.xydexamanalysis.utils.MyHttpUtil.HttpCallback;
import com.xyd.student.xydexamanalysis.utils.NetWorkUtils;
import com.xyd.student.xydexamanalysis.utils.TimeUtils;
import com.xyd.student.xydexamanalysis.utils.ToastUtils;
import com.xyd.student.xydexamanalysis.utils.UIUtils;
import com.xyd.student.xydexamanalysis.view.TitleBar;
import com.xyd.student.xydexamanalysis.view.TitleBar.TitleOnClickListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterAndForPwdActivity extends Activity implements
		TitleOnClickListener {
	private TitleBar titleBar;
	private TextView tv_name, tv_start;
	private Button codeButton, registerButton, re_look;
	private EditText phoneNumber_et, code_et;
	private ImageView register_iv_suo;

	private String name, phoneNumber, code, newPwd;
	private String getCodeUrl, validateverifycodeUrl;
	private Login loginuserInfo;
	private SharedPreferences msharedPreferences;
	private Context mContext;
	private JSONObject param;

	private String number;
	private boolean isResetPass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_and_for_pwd);
		initView();
		name = getIntent().getStringExtra("name");
		if (name.equals("LoginActivity")) {
			isResetPass = false;
			titleBar.setTitle("快速注册");
			tv_name.setVisibility(View.GONE);
			register_iv_suo.setVisibility(View.INVISIBLE);
			re_look.setVisibility(View.GONE);
			phoneNumber_et.setHint("请输入手机号");
		} else {
			isResetPass = true;
			titleBar.setTitle("重置密码");
			tv_name.setVisibility(View.GONE);
			re_look.setVisibility(View.VISIBLE);
			tv_name.setText("密码");
			tv_start.setVisibility(View.GONE);
			registerButton.setText("完成");
			phoneNumber_et.setHint("密码为6-16位");
			number = getIntent().getStringExtra("phoneNumber");

			phoneNumber_et.setTransformationMethod(PasswordTransformationMethod
					.getInstance());
		}

		initClick();
	}

	private void initClick() {

		getCodeUrl = Constants.SMS_URL;
		validateverifycodeUrl = Constants.SMS_VERIFY_URL;

		if (name.equals("LoginActivity")) {
			codeButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					phoneNumber = phoneNumber_et.getText().toString().trim();
					Log.i("phoneNumber", phoneNumber);
					if (!NetWorkUtils.isNetworkAvailable(UIUtils.getContext())) {
						ToastUtils.show(UIUtils.getContext(), "请检查网络");
					} else {
						if (!isResetPass) {
							if (phoneNumber.equals("")) {
								Toast.makeText(RegisterAndForPwdActivity.this,
										"手机号不能为空", Toast.LENGTH_SHORT).show();
								return;
							}
							String value = phoneNumber;
							Pattern p = Pattern
									.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");
							Matcher m = p.matcher(value);
							if (!m.matches()) {
								ToastUtils.show(UIUtils.getContext(),
										"输入手机号不正确");
								return;
							}
						}
						if (!phoneNumber.equals("")) {
							if (checkPhoneNumber(phoneNumber)) {

								param = new JSONObject();

								try {
									param.put("phoneNum", phoneNumber);
									param.put("userCode", "");
									param.put("optionType", 3);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								// Post请求方式
								MyHttpUtil httpUtil = MyHttpUtil
										.getInstance(RegisterAndForPwdActivity.this);
								httpUtil.request(getCodeUrl, param,
										new HttpCallback() {
											@Override
											public void success(String result) {
												// TODO Auto-generated method
												// stub
												Log.i("短信验证码", result);
												try {
													JSONObject jsonObject = new JSONObject(
															result);
													int resultCode = jsonObject
															.getInt("resultCode");
													if (resultCode == -2) {
														ToastUtils.show(UIUtils
																.getContext(),
																"请求验证码过于频繁");
													} else if (resultCode == -1) {
														ToastUtils.show(UIUtils
																.getContext(),
																"验证码错误或失效");
													} else if (resultCode == -5) {
														ToastUtils.show(UIUtils
																.getContext(),
																"服务器繁忙");
													} else if (resultCode == 0) {
														TimeUtils time = new TimeUtils(codeButton,"重新获取");
														time.RunTimer();
													}
												} catch (JSONException e) {
													e.printStackTrace();
												}
											}

											@Override
											public void error(int state,
													String errorMsg) {
												// TODO Auto-generated method
												// stub
												ToastUtils.show(
														UIUtils.getContext(),
														errorMsg);
											}
										});
							} else {
								ToastUtils.show(UIUtils.getContext(),
										"请输入正确的手机号");
							}
						} else {
							ToastUtils.show(UIUtils.getContext(), "手机号不能为空");
						}
					}
				}
			});

			registerButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					code = code_et.getText().toString().trim();
					final String nowPhoneNumberString = phoneNumber_et
							.getText().toString().trim();
					if (!NetWorkUtils.isNetworkAvailable(UIUtils.getContext())) {
						ToastUtils.show(UIUtils.getContext(), "请检查网络");
					} else {
						if (!isResetPass) {
							if (nowPhoneNumberString.equals("")) {
								Toast.makeText(RegisterAndForPwdActivity.this,
										"手机号不能为空", Toast.LENGTH_SHORT).show();
								return;
							}
							String value = nowPhoneNumberString;
							// Pattern p =
							// Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9])|(17[0,5-9]))\\d{8}$");
							Pattern p = Pattern
									.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");
							Matcher m = p.matcher(value);
							if (!m.matches()) {
								ToastUtils.show(UIUtils.getContext(),
										"输入手机号不正确");
								return;
							}
							if (code.equals("")) {
								Toast.makeText(RegisterAndForPwdActivity.this,
										"验证码不能为空", Toast.LENGTH_SHORT).show();
								return;
							}
							if (code.length() != 6) {
								Toast.makeText(RegisterAndForPwdActivity.this,
										"验证码格式不正确", Toast.LENGTH_SHORT).show();
								return;
							}
						}

						if (nowPhoneNumberString.equals(phoneNumber)) {
							if (!code.equals("")) {
								param = new JSONObject();
								try {
									param.put("phoneNum", nowPhoneNumberString);
									param.put("userCode", nowPhoneNumberString);
									param.put("verifyCode", code);
									param.put("optionType", "4");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								// Post请求方式
								MyHttpUtil httpUtil = MyHttpUtil
										.getInstance(RegisterAndForPwdActivity.this);
								httpUtil.request(validateverifycodeUrl, param,
										new HttpCallback() {

											@Override
											public void success(String result) {
												// TODO Auto-generated method
												// stub
												Log.i("验证短信验证码", result);
												try {
													JSONObject jsonObject = new JSONObject(
															result);
													int resultCode = jsonObject
															.getInt("resultCode");
													if (resultCode == -1) {
														// ToastUtils.show(UIUtils.getContext(),"上传参数错误");
													} else if (resultCode == -2) {
														ToastUtils.show(UIUtils
																.getContext(),
																"手机号已被使用");
													} else if (resultCode == -3) {
														ToastUtils.show(UIUtils
																.getContext(),
																"手机号码与账号不匹配");
													} else if (resultCode == -4) {
														ToastUtils.show(UIUtils
																.getContext(),
																"验证码已失效或验证码错误");
													} else if (resultCode == -5) {
														ToastUtils.show(UIUtils
																.getContext(),
																"服务器忙");
													} else if (resultCode == 0) {
														// 验证成功
														Intent intent = new Intent(
																RegisterAndForPwdActivity.this,
																InputCodeActivity.class);
														intent.putExtra("name",
																"RegisterAndForPwdActivity");
														intent.putExtra(
																"phoneNum",
																nowPhoneNumberString);
														startActivity(intent);
														// finish();
													}
												} catch (JSONException e) {
													e.printStackTrace();
												}
											}

											@Override
											public void error(int state,
													String errorMsg) {
												// TODO Auto-generated method
												// stub
												ToastUtils.show(
														UIUtils.getContext(),
														errorMsg);
											}
										});

							} else {
								ToastUtils.show(UIUtils.getContext(), "验证码不能为空");
							}
						} else {
							if (!isResetPass) {
								Toast.makeText(RegisterAndForPwdActivity.this,
										"验证码不正确", Toast.LENGTH_SHORT).show();
								return;
							}
							ToastUtils.show(UIUtils.getContext(), "两次的手机号不一致");
						}

					}

				}
			});

		} else {

			final String resetpwdurl = Constants.CHANGE_PWS;

			// 显示密码
			re_look.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						/* 设定EditText的内容为可见的 */

						// ToastUtils.show(UIUtils.getContext(), "按下");
						phoneNumber_et
								.setTransformationMethod(HideReturnsTransformationMethod
										.getInstance());
					}
					if (event.getAction() == MotionEvent.ACTION_UP) {
						/* 设定EditText的内容为隐藏的 */

						// ToastUtils.show(UIUtils.getContext(), "抬起");
						phoneNumber_et
								.setTransformationMethod(PasswordTransformationMethod
										.getInstance());
					}
					return false;
				}
			});

			// 重置密码界面
			codeButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// phoneNumber=phoneNumber_et.getText().toString().trim();
					newPwd = phoneNumber_et.getText().toString().trim();
					Log.i("number", number);
					if (!NetWorkUtils.isNetworkAvailable(UIUtils.getContext())) {
						ToastUtils.show(UIUtils.getContext(), "请检查网络");
					} else {
						if (isResetPass) {
							if (newPwd.equals("")) {
								Toast.makeText(RegisterAndForPwdActivity.this,
										"新密码不能为空", Toast.LENGTH_SHORT).show();
								return;
							}
							if (newPwd.length() < 6 || newPwd.length() > 16) {
								Toast.makeText(RegisterAndForPwdActivity.this,
										"密码格式不正确", Toast.LENGTH_SHORT).show();
								return;
							}
						}
						if (!newPwd.equals("")) {
							if (checkPhoneNumber(number)) {
								param = new JSONObject();

								try {
									param.put("phoneNum", number);
									param.put("userCode", number);
									param.put("optionType", 2);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								// Post请求方式
								MyHttpUtil httpUtil = MyHttpUtil
										.getInstance(RegisterAndForPwdActivity.this);
								httpUtil.request(getCodeUrl, param,
										new HttpCallback() {

											@Override
											public void success(String result) {
												// TODO Auto-generated method
												// stub
												Log.i("短信验证码", result);
												try {
													JSONObject jsonObject = new JSONObject(
															result);
													int resultCode = jsonObject
															.getInt("resultCode");
													if (resultCode == -2) {
														ToastUtils.show(UIUtils
																.getContext(),
																"请求验证码过于频繁");
													} else if (resultCode == -1) {
														ToastUtils.show(UIUtils
																.getContext(),
																"验证码错误或失效");
													} else if (resultCode == -5) {
														ToastUtils.show(UIUtils
																.getContext(),
																"服务器繁忙");
													} else if (resultCode == 0) {
														TimeUtils time = new TimeUtils(
																codeButton,
																"重新获取");
														time.RunTimer();
													}
												} catch (JSONException e) {
													e.printStackTrace();
												}
											}

											@Override
											public void error(int state,
													String errorMsg) {
												// TODO Auto-generated method
												// stub
												ToastUtils.show(
														UIUtils.getContext(),
														errorMsg);
											}
										});
							} else {
								// ToastUtils.show(UIUtils.getContext(),
								// "请输入正确的手机号");
							}
						} else {
							ToastUtils.show(UIUtils.getContext(), "新密码不能为空");
						}
					}
				}
			});
			registerButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					code = code_et.getText().toString().trim();
					final String nowNewPwd = phoneNumber_et.getText()
							.toString().trim();
					if (!NetWorkUtils.isNetworkAvailable(UIUtils.getContext())) {
						ToastUtils.show(UIUtils.getContext(), "请检查网络");
					} else {
						if (isResetPass) {
							if (nowNewPwd.equals("")) {
								Toast.makeText(RegisterAndForPwdActivity.this,
										"密码不能为空", Toast.LENGTH_SHORT).show();
								return;
							}
							if (nowNewPwd.length() < 6
									|| nowNewPwd.length() > 16) {
								Toast.makeText(RegisterAndForPwdActivity.this,
										"密码格式不正确", Toast.LENGTH_SHORT).show();
								return;
							}
							if (code.equals("")) {
								Toast.makeText(RegisterAndForPwdActivity.this,
										"验证码不能为空", Toast.LENGTH_SHORT).show();
								return;
							}
							if (code.length() != 6) {
								Toast.makeText(RegisterAndForPwdActivity.this,
										"验证码格式不正确", Toast.LENGTH_SHORT).show();
								return;
							}
						}
						if (nowNewPwd.equals(newPwd)) {
							if (!code.equals("")) {
								param = new JSONObject();

								try {
									// param.put("phoneNum",
									// nowPhoneNumberString);
									param.put("userCode", number);
									param.put("verifyCode", code);
									param.put("newPwd", nowNewPwd);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								// Post请求方式
								MyHttpUtil httpUtil = MyHttpUtil
										.getInstance(RegisterAndForPwdActivity.this);
								httpUtil.request(resetpwdurl, param,
										new HttpCallback() {

											@Override
											public void success(String result) {
												// TODO Auto-generated method
												// stub
												Log.i("重置密码", result);
												// Toast.makeText(RegisterAndForPwdActivity.this,
												// "重置密码"+result,
												// Toast.LENGTH_LONG).show();
												try {
													JSONObject jsonObject = new JSONObject(
															result);
													int resultCode = jsonObject
															.getInt("resultCode");
													if (resultCode == -1) {
														// ToastUtils.show(UIUtils.getContext(),"上传参数错误");
													} else if (resultCode == -2) {
														ToastUtils.show(UIUtils
																.getContext(),
																"验证码已失效或验证码错误");
													} else if (resultCode == -5) {
														ToastUtils.show(UIUtils
																.getContext(),
																"服务器忙");
													} else if (resultCode == 0) {
														// 修改密码成功
														ToastUtils.show(UIUtils
																.getContext(),
																"修改密码成功");
														Intent intent = new Intent(
																RegisterAndForPwdActivity.this,
																LoginActivity.class);
														startActivity(intent);
														msharedPreferences = mContext
																.getSharedPreferences(
																		Constants.SHARED_PREFERENCES,
																		Context.MODE_PRIVATE);
														msharedPreferences
																.edit()
																.putBoolean(
																		"isphone",
																		true)
																.commit();
														msharedPreferences
																.edit()
																.putString(
																		"login_phone",
																		number)
																.commit();
														msharedPreferences
																.edit()
																.putString(
																		"login_password",
																		nowNewPwd)
																.commit();
														msharedPreferences
																.edit()
																.putString(
																		"ReportServUrl",
																		Constants.BASE_URL)
																.commit();
														finish();
													}
												} catch (JSONException e) {
													e.printStackTrace();
												}
											}

											@Override
											public void error(int state,
													String errorMsg) {
												// TODO Auto-generated method
												// stub
												ToastUtils.show(
														UIUtils.getContext(),
														errorMsg);
											}
										});

							} else {
								ToastUtils.show(UIUtils.getContext(), "验证码不能为空");
							}
						} else {
							ToastUtils.show(UIUtils.getContext(), "两次的密码不一致");
						}

					}

				}
			});
		}
	}

	private void getinsystem() {
		Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();
		msharedPreferences
				.edit()
				.putString("login_phone",
						loginuserInfo.getUserInfoList().getUserCode()).commit();
		msharedPreferences
				.edit()
				.putString("login_password",
						loginuserInfo.getUserInfoList().getName()).commit();
		msharedPreferences
				.edit()
				.putString("phone_userId",
						loginuserInfo.getUserInfoList().getUserId()).commit();
	}

	/**
	 * 验证手机号码
	 * 
	 * @param phoneNumber
	 *            手机号码
	 * @return boolean
	 */
	public static boolean checkPhoneNumber(String phoneNumber) {
		Pattern pattern = Pattern.compile("^1[0-9]{10}$");
		Matcher matcher = pattern.matcher(phoneNumber);
		return matcher.matches();
	}

	private void initView() {

		titleBar = (TitleBar) findViewById(R.id.register_title);
		titleBar.setLeftIcon(R.drawable.back_icon);
		titleBar.setTitleClickListener(this);

		tv_name = (TextView) findViewById(R.id.re_fo_name);
		register_iv_suo = (ImageView)findViewById(R.id.register_iv_suo);
		tv_start = (TextView) findViewById(R.id.phone_start);
		codeButton = (Button) findViewById(R.id.re_get_code_btn);
		registerButton = (Button) findViewById(R.id.register_btn);
		phoneNumber_et = (EditText) findViewById(R.id.et_re_1);
		code_et = (EditText) findViewById(R.id.re_verification_code);
		re_look = (Button) findViewById(R.id.re_look);

		mContext = this.getApplicationContext();
		msharedPreferences = mContext.getSharedPreferences(
				Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
	}

	@Override
	public void leftClick() {
		// TODO Auto-generated method stub
		if (isResetPass) {
			Toast.makeText(RegisterAndForPwdActivity.this, "重置密码失败",
					Toast.LENGTH_SHORT).show();
		} else {
			// Toast.makeText(RegisterAndForPwdActivity.this, "注册失败",
			// Toast.LENGTH_SHORT).show();
		}
		finish();
	}

	@Override
	public void rightClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void titleClick() {
		// TODO Auto-generated method stub

	}
}
