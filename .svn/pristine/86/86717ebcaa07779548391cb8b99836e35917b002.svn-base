package com.xyd.student.xydexamanalysis.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import com.xyd.student.xydexamanalysis.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

public class UpdateManager {

	/* 下载中 */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;
	/* 下载异常 */
	private static final int EXCEPTION = 0;
	/* 非法http协议 */
	private static final int MALFORMED = -1;
	/* 服务器不可用 */
	private static final int HOSTWRONG = -2;
	/* 下载保存路径 */
	private String mSavePath;
	/* 记录进度条数量 */
	private int progress;
	/* 是否取消更新 */
	private boolean cancelUpdate = false;

	private Context mContext;
	/* 更新进度条 */
	private ProgressBar mProgress;
	private Dialog mDownloadDialog;
	private Dialog noticeDialog;
	private String mark;
	private String url;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 正在下载
			case DOWNLOAD:
				// 设置进度条位置
				mProgress.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				// 安装文件
				// installApk();
				break;
			case 3:
				if (noticeDialog != null && noticeDialog.isShowing()) {
					noticeDialog.dismiss();
				}
				showDownloadDialog();
				break;
			case 10:
				if (mDownloadDialog != null) {
					mDownloadDialog.dismiss();
				}
				Toast.makeText(mContext, "下载出错,重新更新版本", 1000).show();
				break;
			case EXCEPTION:
				if (mDownloadDialog != null) {
					mDownloadDialog.dismiss();
				}
				if (!cancelUpdate) {
					Toast.makeText(mContext, "服务器超时,更新版本失败!", 1000).show();
				}
				break;
			case MALFORMED:
				if (mDownloadDialog != null) {
					mDownloadDialog.dismiss();
				}
				Toast.makeText(mContext, "非法的HTTP协议", 1000).show();
				break;
			case HOSTWRONG:
				if (mDownloadDialog != null) {
					mDownloadDialog.dismiss();
				}
				Toast toast = Toast.makeText(mContext, "服务器不可用", 3000);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				break;
			default:
				break;
			}
		};
	};

	public UpdateManager(Context context) {
		this.mContext = context;
	}

	/**
	 * 获取当前软件版本
	 */
	public int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			PackageManager manager = context.getPackageManager();
			PackageInfo packageInfo = manager.getPackageInfo(
					context.getPackageName(), 0);
			String pakname = packageInfo.packageName;
			Log.v("getVersionCode", "package name = " + pakname);
			versionCode = packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 获取当前软件版本名称
	 */
	public String getVersionName(Context context) {
		String versionName = null;
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			PackageManager manager = context.getPackageManager();
			PackageInfo packageInfo = manager.getPackageInfo(
					context.getPackageName(), 0);
			String pakname = packageInfo.packageName;
			Log.v("getVersionCode", "package name = " + pakname);
			versionName = packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}

	public static String getCurrentVersionName(Context mContext) {
		try {
			return mContext.getPackageManager().getPackageInfo(
					mContext.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 显示软件更新对话框
	 */
	public void showNoticeDialog(String markString, String urlString) {
		this.mark = markString;
		this.url = urlString;
		if (noticeDialog == null) {
			// 构造对话框
			AlertDialog.Builder builder = new Builder(
					mContext.getApplicationContext());
			builder.setTitle(R.string.soft_update_title);
			builder.setMessage(R.string.soft_update_info);
			String desc = mark;
			builder.setMessage(desc);
			// 更新
			builder.setPositiveButton(R.string.soft_update_updatebtn,
					new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							// isCancel = true;
							// 显示下载对话框
							showDownloadDialog();
						}
					});
			// 稍后更新
			builder.setNegativeButton(R.string.soft_update_later,
					new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			noticeDialog = builder.create();
			noticeDialog.getWindow().setType(
					WindowManager.LayoutParams.TYPE_TOAST);
			noticeDialog.setCanceledOnTouchOutside(false);
		}
		if (!noticeDialog.isShowing()) {
			noticeDialog.show();
		}
	}

	// 必须更新
	public void showNoticeDialog_must(String markString, String urlString) {
		this.mark = markString;
		this.url = urlString;
		if (noticeDialog == null) {
			// 构造对话框
			AlertDialog.Builder builder = new Builder(
					mContext.getApplicationContext());
			builder.setTitle(R.string.soft_update_title);
			builder.setMessage(R.string.soft_update_info);
			String desc = mark;
			builder.setMessage(desc);
			// 更新
			builder.setPositiveButton(R.string.soft_update_updatebtn,
					new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							// isCancel = true;
							// 显示下载对话框
							showDownloadDialog();
						}
					});

			noticeDialog = builder.create();
			noticeDialog.getWindow().setType(
					WindowManager.LayoutParams.TYPE_TOAST);
			noticeDialog.setCanceledOnTouchOutside(false);
			noticeDialog.setCancelable(false);
		}
		if (!noticeDialog.isShowing()) {
			noticeDialog.show();
		}
	}

	/**
	 * 显示软件下载对话框
	 */
	private void showDownloadDialog() {
		// 构造软件下载对话框
		AlertDialog.Builder builder = new Builder(
				mContext.getApplicationContext());
		builder.setTitle(R.string.soft_updating);
		// 给下载对话框增加进度条
		final LayoutInflater inflater = LayoutInflater.from(mContext
				.getApplicationContext());
		View v = inflater.inflate(R.layout.softupdate_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		builder.setView(v);
		// 取消更新
		builder.setNegativeButton(R.string.soft_update_cancel,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 设置取消状态
						cancelUpdate = true;
					}
				});
		mDownloadDialog = builder.create();
		mDownloadDialog.getWindow().setType(
				WindowManager.LayoutParams.TYPE_TOAST);
		mDownloadDialog.setCanceledOnTouchOutside(false);
		mDownloadDialog.setCancelable(false);
		mDownloadDialog.show();
		// 现在文件
		downloadApk();
	}

	/**
	 * 下载apk文件
	 */
	private synchronized void downloadApk() {
		// 启动新线程下载软件
		new downloadApkThread().start();
	}

	/**
	 * 下载文件线程
	 * 
	 */
	private class downloadApkThread extends Thread {
		@Override
		public void run() {
			try {
				// 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// 获得存储卡的路径
					String sdpath = Environment.getExternalStorageDirectory()
							+ "/";
					mSavePath = sdpath
							+ "com.xyd.student.xydexamanalysis/download";
					String ip = null;
					if (url != null) {
						ip = url;
					} else {
						mHandler.sendEmptyMessage(10);
						return;
					}
					URL url = new URL(ip);
					DefaultHttpClient client = new DefaultHttpClient();
					LoginUtils login = new LoginUtils();
					client.getCredentialsProvider().setCredentials(
							new AuthScope(url.getHost(), url.getPort(),
									AuthScope.ANY_REALM, AuthPolicy.DIGEST),

							new UsernamePasswordCredentials(login
									.getLoginUser(), login.getLoginPw()));
					client.getParams().setParameter(
							CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);
					client.getParams().setParameter(
							CoreConnectionPNames.SO_TIMEOUT, 10 * 1000);
					HttpGet httpGet = new HttpGet(ip);
					HttpResponse response;
					response = client.execute(httpGet);

					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						HttpEntity entity = response.getEntity();

						long length = entity.getContentLength();

						InputStream is = entity.getContent();

						File file = new File(mSavePath);
						// 判断文件目录是否存在
						if (!file.exists()) {
							file.mkdirs();
						}
						File apkFile = new File(mSavePath, "xyd.apk");
						FileOutputStream fos = new FileOutputStream(apkFile);
						int count = 0;
						// 缓存
						byte buf[] = new byte[1024];
						// 写入到文件中
						do {
							int numread = is.read(buf);
							count += numread;
							// 计算进度条位置
							progress = (int) (((float) count / length) * 100);
							// 更新进度
							mHandler.sendEmptyMessage(DOWNLOAD);
							if (numread <= 0) {
								// 下载完成
								mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
								break;
							}
							// 写入文件
							fos.write(buf, 0, numread);
						} while (!cancelUpdate);// 点击取消就停止下载.
						if (cancelUpdate) {// 手动取消下载
							client.getConnectionManager().shutdown();
						}
						fos.close();
						is.close();
					} else {
						mHandler.sendEmptyMessage(HOSTWRONG);
					}

				}
			} catch (MalformedURLException e) {
				mHandler.sendEmptyMessage(MALFORMED);
				e.printStackTrace();
			} catch (IOException e) {
				mHandler.sendEmptyMessage(EXCEPTION);
				e.printStackTrace();
			}
			// 取消下载对话框显示
			mDownloadDialog.dismiss();
		}
	};

	/**
	 * 安装APK文件
	 */
	private void installApk() {
		Log.i("TAG", "---------");
		File apkfile = new File(mSavePath, "xyd.apk");
		if (!apkfile.exists()) {
			return;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
}
