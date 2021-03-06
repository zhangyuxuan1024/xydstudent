package com.xyd.student.xydexamanalysis.ui.weike;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.constant.Constant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLayoutChangeListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateWeikeActivity extends Activity implements OnClickListener {
	private EditText editText;
	private TextView tv_cancle, tv_update;
	private String wk_name;

	private String reStr;
	private String id, optionType, content;
	private boolean isChange;
	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_weike);
		Intent intent = getIntent();
		wk_name = intent.getStringExtra("name");
		id = intent.getStringExtra("id");
		init();
	}

	private void init() {
		editText = (EditText) findViewById(R.id.weike_name);
		tv_cancle = (TextView) findViewById(R.id.weike_cancel);
		tv_update = (TextView) findViewById(R.id.weike_update);

		tv_cancle.setOnClickListener(this);
		tv_update.setOnClickListener(this);
		editText.setHint(wk_name);
		isChange = false;
		editText.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if (arg1 && !editText.getText().toString().equals(wk_name)) {
					isChange = true;
				}
			}
		});
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {
				// TODO Auto-generated method stub
				String text = editText.getText().toString();
				editText.setSelection(text.length());
				if (text.length() > 12) {
					text = text.substring(0, 12);
					editText.setText(text);
				} else if (text.length() > 0) {
					tv_update.setBackgroundResource(R.drawable.button_shape_normal);
					tv_update.setTextColor(Color.parseColor("#ffffff"));
				} else if (text.length() == 0) {
					tv_update.setClickable(false);
					tv_update.setBackgroundResource(R.drawable.dialog_true_or_false);
					tv_update.setTextColor(Color.parseColor("#525252"));
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.weike_cancel:
			this.finish();
			break;
		case R.id.weike_update:
			String text = editText.getText().toString();
			String text2 = editText.getHint().toString();
			if ((text == null || text.equals("") || text.length() == 0)
					&& (text2 == null || text2.equals("") || text2.length() == 0)) {
				Toast.makeText(this, "至少输入一个字符", Toast.LENGTH_LONG).show();
				return;
			}
			if (isChange && text.length() > 12) {
				Toast.makeText(this, "最多可输入12个字符", Toast.LENGTH_LONG).show();
				return;
			}
			Intent intent = new Intent(UpdateWeikeActivity.this,
					ScanRecordActivity.class);
			if (text.length() == 0) {
				text = text2;
			}
			intent.putExtra("name", text);
			setResult(RESULT_OK, intent);
			content = editText.getText().toString();
			this.finish();

			new Thread(new Runnable() {
				@Override
				public void run() {
					JSONArray array = new JSONArray();
					JSONObject object = new JSONObject();
					try {
						object.put("id", id);
						object.put("optionType", "3");
						object.put("content", content);
						array.put(object);
						JSONObject json = new JSONObject();
						json.put("modifyWKInfos", array.toString());
						String jsonstr = json.toString();

						if (jsonstr.contains("\\")) {
							jsonstr = jsonstr.replace("\\", "");
							jsonstr = jsonstr.replace("\"[", "[");
							jsonstr = jsonstr.replace("]\"", "]");
						}
						URL url = new URL(Constant.UPDATE_WEIKE);
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						conn.setDoInput(true);
						conn.setDoOutput(true);
						conn.setRequestMethod("POST");
						conn.setRequestProperty("Content-Type",
								"application/json;charset=UTF-8");
						OutputStreamWriter osw = new OutputStreamWriter(conn
								.getOutputStream(), "utf-8");
						osw.write(jsonstr);
						osw.flush();

						conn.setConnectTimeout(1000 * 100);
						conn.setReadTimeout(1000 * 100);
						if (conn.getResponseCode() == 200) {
							BufferedReader br = new BufferedReader(
									new InputStreamReader(
											conn.getInputStream(), "utf-8"));
							String str = "";
							while ((str = br.readLine()) != null) {
								reStr += str;
							}
							br.close();
							conn.disconnect();
						}
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								// Toast.makeText(UpdateWeikeActivity.this,
								// "数据发送成功" + ",result=" + reStr,
								// Toast.LENGTH_LONG).show();
							}
						});
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
			break;
		}
	}
}
