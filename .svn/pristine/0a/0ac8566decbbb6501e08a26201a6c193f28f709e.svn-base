package com.xyd.student.xydexamanalysis.utils;

/**
 * Created by Lichg.
 */
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class MyHttpUtil implements Handler.Callback {
	private JSONObject myparams;
	private String paramsvideo;
	private String myUrl, videoUrl;
	private Handler Handler;
	private Context mContext;
	private static MyHttpUtil instance;

	public static final int RESPONSE_STATE_SUCCESS = 0;
	public static final int RESPONSE_STATE_NO_NET = 1;
	public static final int RESPONSE_STATE_TIMEOUT = 2;
	public static final int RESPONSE_STATE_NONCONNECT = 3;
	public static final int RESPONSE_STATE_ERRORMSG = 4;
	public static final int RESPONSE_STATE_PARSEERROR = 5;
	public static final int RESPONSE_STATE_OTHERERROR = 6;
	public static final int RESPONSE_STATE_UNAUTHORIZED = 7;

	public interface HttpCallback {
		public void success(String result);

		public void error(int state, String errorMsg);
	}

	private MyHttpUtil(Context ctx) {
		mContext = ctx;
		Handler = new Handler(this);
	}

	public static MyHttpUtil getInstance(Context ctx) {
		if (instance == null) {
			instance = new MyHttpUtil(ctx);
		}
		return instance;
	}

	public void request(String url, JSONObject params,
			final HttpCallback callback) {
		Log.i("Lichg", "url" + url);
		Log.i("Lichg", "params" + params);
		requestpost(url, params, callback);
	}

	public void requestMethdget(final String url, final HttpCallback callback) {
		if (isNetworkAvailable()) {
			Thread getdatathread = new Thread(new Runnable() {
				@Override
				public void run() {
					// httpclientget(myUrl, myparams, callback);
					httpclientMethdGet(url, callback);
				}
			});
			getdatathread.start();
		} else {
			Handler.post(new Runnable() {
				@Override
				public void run() {
					if (callback != null) {
						callback.error(RESPONSE_STATE_NO_NET, "请检查您的网络状态");
					}
				}
			});
		}
	}

	public void requestMethPost(final String url,
			final List<NameValuePair> bodyparam, final HttpCallback callback) {
		if (isNetworkAvailable()) {
			Thread getdatathread = new Thread(new Runnable() {
				@Override
				public void run() {
					// httpclientget(myUrl, myparams, callback);
					httpclientMethdPost(url, bodyparam, callback);
				}
			});
			getdatathread.start();
		} else {
			Handler.post(new Runnable() {
				@Override
				public void run() {
					if (callback != null) {
						callback.error(RESPONSE_STATE_NO_NET, "请检查您的网络状态");
					}
				}
			});
		}
	}

	private void requestpost(String url, JSONObject params,
			final HttpCallback callback) {
		myparams = params;
		myUrl = url;
		if (isNetworkAvailable()) {
			Thread getdatathread = new Thread(new Runnable() {
				@Override
				public void run() {
					httpclientget(myUrl, myparams, callback);
				}
			});
			getdatathread.start();
		} else {
			Handler.post(new Runnable() {
				@Override
				public void run() {
					if (callback != null) {
						callback.error(RESPONSE_STATE_NO_NET, "请检查您的网络状态");
					}
				}
			});
		}
	}

	private void httpclientMethdGet(String stringurl,
			final HttpCallback callback) {

		try {
			DefaultHttpClient myhttpclient = new DefaultHttpClient();
			myhttpclient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 19 * 1000);
			myhttpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 19 * 1000);
			HttpGet httpget = new HttpGet(stringurl);

			HttpResponse response = myhttpclient.execute(httpget);
			final int httpstates = response.getStatusLine().getStatusCode();
			final String msg = response.getStatusLine().getReasonPhrase();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (httpstates == HttpStatus.SC_OK) {
				InputStream respStream = response.getEntity().getContent();
				int len = 0;
				byte buffer[] = new byte[1024];
				while ((len = respStream.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				respStream.close();
				baos.close();
			}
			final String Stringbaos = baos.toString();

			Handler.post(new Runnable() {
				@Override
				public void run() {
					if (httpstates == HttpStatus.SC_OK) {
						if (callback != null) {
							callback.success(Stringbaos);
						}
					} else if (httpstates == HttpStatus.SC_UNAUTHORIZED) {
						if (callback != null)
							callback.error(RESPONSE_STATE_UNAUTHORIZED,
									"您的考号或姓名错误，请重新输入！");
					} else if (httpstates == HttpStatus.SC_REQUEST_TIMEOUT) {
						if (callback != null)
							callback.error(RESPONSE_STATE_TIMEOUT, "网络请求超时");
					} else if (msg.contains("HttpHostConnectException")) {
						if (callback != null)
							callback.error(RESPONSE_STATE_NONCONNECT,
									"请查看您的网络状态");
					} else if (msg.contains("Timeout")) {
						if (callback != null)
							callback.error(RESPONSE_STATE_TIMEOUT, "网络请求超时");
					} else {
						if (callback != null)
							callback.error(RESPONSE_STATE_OTHERERROR, "网络异常");
					}
				}
			});
			myhttpclient.getConnectionManager().shutdown();
			Thread.currentThread().interrupt();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Handler.post(new Runnable() {
				@Override
				public void run() {
					callback.error(RESPONSE_STATE_OTHERERROR, "网络异常！");
					Thread.currentThread().interrupt();
				}
			});
		}
		return;
	}

	private void httpclientMethdPost(String stringurl,
			List<NameValuePair> pamras, final HttpCallback callback) {

		try {
			DefaultHttpClient myhttpclient = new DefaultHttpClient();
			myhttpclient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 19 * 1000);
			myhttpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 19 * 1000);
			HttpPost httppost = new HttpPost(stringurl);
			httppost.setEntity(new UrlEncodedFormEntity(pamras, HTTP.UTF_8));

			HttpResponse response = myhttpclient.execute(httppost);
			final int httpstates = response.getStatusLine().getStatusCode();
			final String msg = response.getStatusLine().getReasonPhrase();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (httpstates == HttpStatus.SC_OK) {
				InputStream respStream = response.getEntity().getContent();
				int len = 0;
				byte buffer[] = new byte[1024];
				while ((len = respStream.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				respStream.close();
				baos.close();
			}
			final String Stringbaos = baos.toString();

			Handler.post(new Runnable() {
				@Override
				public void run() {
					if (httpstates == HttpStatus.SC_OK) {
						if (callback != null) {
							callback.success(Stringbaos);
						}
					} else if (httpstates == HttpStatus.SC_UNAUTHORIZED) {
						if (callback != null)
							callback.error(RESPONSE_STATE_UNAUTHORIZED,
									"您的考号或姓名错误，请重新输入！");
					} else if (httpstates == HttpStatus.SC_REQUEST_TIMEOUT) {
						if (callback != null)
							callback.error(RESPONSE_STATE_TIMEOUT, "网络请求超时");
					} else if (msg.contains("HttpHostConnectException")) {
						if (callback != null)
							callback.error(RESPONSE_STATE_NONCONNECT,
									"请查看您的网络状态");
					} else if (msg.contains("Timeout")) {
						if (callback != null)
							callback.error(RESPONSE_STATE_TIMEOUT, "网络请求超时");
					} else {
						if (callback != null)
							callback.error(RESPONSE_STATE_OTHERERROR, "无数据信息");
					}
				}
			});
			myhttpclient.getConnectionManager().shutdown();
			Thread.currentThread().interrupt();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Handler.post(new Runnable() {
				@Override
				public void run() {
					callback.error(RESPONSE_STATE_OTHERERROR, "无数据信息");
					Thread.currentThread().interrupt();
				}
			});
		}
		return;
	}

	private void httpclientget(String stringurl, JSONObject jsonresult,
			final HttpCallback callback) {

		try {
			DefaultHttpClient myhttpclient = new DefaultHttpClient();
			URL url = new URL(stringurl);
			LoginUtils login = new LoginUtils();
			myhttpclient.getCredentialsProvider().setCredentials(
					new AuthScope(url.getHost(), url.getPort(),
							AuthScope.ANY_REALM, AuthPolicy.DIGEST),

					new UsernamePasswordCredentials(login.getLoginUser(), login
							.getLoginPw()));
			myhttpclient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 19 * 1000);
			myhttpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 19 * 1000);

			/**/
			HttpPost httppost = new HttpPost(stringurl);
			HttpEntity entity = new StringEntity(jsonresult.toString(), "UTF-8");
			httppost.setEntity(entity);
			long currentTime1 = System.currentTimeMillis();

			// final String settype = jsonresult.getString("type");
			HttpResponse response = myhttpclient.execute(httppost);
			long currentTime2 = System.currentTimeMillis();
			Log.i("lxw", "请求HttpResponse的时间：" + (currentTime2 - currentTime1));
			final int httpstates = response.getStatusLine().getStatusCode();
			final String msg = response.getStatusLine().getReasonPhrase();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (httpstates == HttpStatus.SC_OK) {
				InputStream respStream = response.getEntity().getContent();
				int len = 0;
				byte buffer[] = new byte[1024];
				while ((len = respStream.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				respStream.close();
				baos.close();
			}
			final String Stringbaos = baos.toString();

			Handler.post(new Runnable() {
				@Override
				public void run() {
					if (httpstates == HttpStatus.SC_OK) {
						if (JsonUtils.getResultCode(Stringbaos) == 0) {
							if (callback != null) {
								callback.success(Stringbaos);
							}
						} else {
							if (callback != null) {
								callback.error(RESPONSE_STATE_ERRORMSG,
										JsonUtils.getResultMsg(Stringbaos));
							}
						}
					} else if (httpstates == HttpStatus.SC_UNAUTHORIZED) {
						if (callback != null)
							callback.error(RESPONSE_STATE_UNAUTHORIZED,
									"您的考号或姓名错误，请重新输入！");
					} else if (httpstates == HttpStatus.SC_REQUEST_TIMEOUT) {
						if (callback != null)
							callback.error(RESPONSE_STATE_TIMEOUT, "网络请求超时");
					} else if (msg.contains("HttpHostConnectException")) {
						if (callback != null)
							callback.error(RESPONSE_STATE_NONCONNECT,
									"请查看您的网络状态");
					} else if (msg.contains("Timeout")) {
						if (callback != null)
							callback.error(RESPONSE_STATE_TIMEOUT, "网络请求超时");
					} else {
						if (callback != null)
							callback.error(RESPONSE_STATE_OTHERERROR, "无数据信息");
					}
				}
			});
			myhttpclient.getConnectionManager().shutdown();
			Thread.currentThread().interrupt();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Handler.post(new Runnable() {
				@Override
				public void run() {
					callback.error(RESPONSE_STATE_OTHERERROR, "无数据信息");
					Thread.currentThread().interrupt();
				}
			});
		}
		return;
	}

	public void request(String url, String params, final HttpCallback callback) {
		Log.i("Lichg", "url" + url);
		Log.i("Lichg", "params" + params);
		requestpost(url, params, callback);
	}

	private void requestpost(String url, String params,
			final HttpCallback callback) {
		paramsvideo = params;
		videoUrl = url;
		if (isNetworkAvailable()) {
			Thread getdatathreadvideo = new Thread(new Runnable() {
				@Override
				public void run() {
					httpclientget(videoUrl, paramsvideo, callback);
				}
			});
			getdatathreadvideo.start();
		} else {
			Handler.post(new Runnable() {
				@Override
				public void run() {
					if (callback != null) {
						callback.error(RESPONSE_STATE_NO_NET, "请检查您的网络状态");
					}
				}
			});
		}
	}

	private void httpclientget(String stringurl, String resource,
			final HttpCallback callback) {
		try {

			List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd",
					Locale.CHINA);
			Date curDate = new Date(System.currentTimeMillis());
			String str = formatter.format(curDate);
			String tokenstring = StringUtils.md5(resource + "ask" + str);
			params.add(new BasicNameValuePair("action",
					"DruqwPmd1h9k2wRxUV7njw@@"));
			params.add(new BasicNameValuePair("resource", resource));
			params.add(new BasicNameValuePair("token", tokenstring));
			Log.i("Lichg", params.toString());
			DefaultHttpClient myhttpclient = new DefaultHttpClient();
			myhttpclient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);
			myhttpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 10 * 1000);
			HttpPost httppost = new HttpPost(stringurl);
			httppost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			HttpResponse response = myhttpclient.execute(httppost);
			final int httpstates = response.getStatusLine().getStatusCode();
			final String msg = response.getStatusLine().getReasonPhrase();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (httpstates == HttpStatus.SC_OK) {
				InputStream respStream = response.getEntity().getContent();

				int len = 0;
				byte buffer[] = new byte[1024];
				while ((len = respStream.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				respStream.close();
				baos.close();
			}
			final String Stringbaos = baos.toString();
			Handler.post(new Runnable() {
				@Override
				public void run() {
					if (httpstates == HttpStatus.SC_OK) {
						if (callback != null) {
							callback.success(Stringbaos);
						}
					} else if (httpstates == HttpStatus.SC_UNAUTHORIZED) {
						if (callback != null)
							callback.error(RESPONSE_STATE_UNAUTHORIZED,
									"您的考号或姓名错误，请重新输入！");
					} else if (httpstates == HttpStatus.SC_REQUEST_TIMEOUT) {
						if (callback != null)
							callback.error(RESPONSE_STATE_TIMEOUT, "网络请求超时");
					} else if (msg.contains("HttpHostConnectException")) {
						if (callback != null)
							callback.error(RESPONSE_STATE_NONCONNECT,
									"请查看您的网络状态");
					} else if (msg.contains("Timeout")) {
						if (callback != null)
							callback.error(RESPONSE_STATE_TIMEOUT, "网络请求超时");
					} else {
						if (callback != null)
							callback.error(RESPONSE_STATE_OTHERERROR, msg);
					}
				}
			});
			myhttpclient.getConnectionManager().shutdown();
			Thread.currentThread().interrupt();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Handler.post(new Runnable() {
				@Override
				public void run() {
					callback.error(RESPONSE_STATE_OTHERERROR, "无数据信息");
					Thread.currentThread().interrupt();
				}
			});
		}
		return;
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager;
		if (mContext != null)
			connectivityManager = (ConnectivityManager) mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
		else
			connectivityManager = (ConnectivityManager) UIUtils.getContext()
					.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null)
			return false;
		// 获取NetworkInfo对象
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isAvailable()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean handleMessage(Message arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
