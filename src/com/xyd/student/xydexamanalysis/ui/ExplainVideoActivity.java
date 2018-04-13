package com.xyd.student.xydexamanalysis.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgentJSInterface;
import com.xyd.student.xydexamanalysis.R;

public class ExplainVideoActivity extends Activity {
	private boolean isErrorPage = false;
	private boolean isFullVideo = false;
	private WebView webView;
	private View myView = null;
	private ProgressDialog dialog = null;
	private WebChromeClient.CustomViewCallback myCallback = null;
	private String video_url;
	// private String
	// string1="http://v.youku.com/v_show/id_XMTM0MDIwNjkyMA==.html?f=26087352&ev=2";
	private String string1 = "http://www.iqiyi.com/v_19rrocbkhs.html?list=19rroena26";

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.video_activity_layout);
		webView = (WebView) findViewById(R.id.wv);

		new MobclickAgentJSInterface(this, webView, new WebChromeClient());
		Bundle bundle = getIntent().getExtras();
		String videoUrl = bundle.getString("url");
		if (videoUrl.equals(null)) {
			video_url = videoUrl;
		} else {
			video_url = string1;
		}
		setRequestedOrientation(0);
		initWebView(string1);

	}

	private void initWebView(String videoUrl) {
		// TODO Auto-generated method stub

		this.webView.setWebViewClient(this.detailsClient);
		this.webView.setWebChromeClient(this.chromeClient);
		WebSettings localWebSettings = this.webView.getSettings();
		localWebSettings.setCacheMode(2);
		localWebSettings.setUseWideViewPort(true);
		localWebSettings.setLoadWithOverviewMode(true);
		localWebSettings.setJavaScriptEnabled(true);
		localWebSettings.setAllowFileAccess(true);

		localWebSettings.setPluginState(PluginState.ON);
		localWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		localWebSettings.setDefaultTextEncodingName("UTF-8");
		webView.setVisibility(View.VISIBLE);
		dialog = new ProgressDialog(this);
		dialog.setCancelable(true);
		dialog.setMessage("正在加载视频数据...");
		dialog.show();
		this.webView.loadUrl(videoUrl);
	}

	private final WebViewClient detailsClient = new WebViewClient() {
		public void onPageFinished(WebView paramAnonymousWebView,
				String paramAnonymousString) {
			if (ExplainVideoActivity.this.isErrorPage)
				return;
			ExplainVideoActivity.this.webView.scrollTo(0, 0);
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}

		public void onReceivedError(WebView paramAnonymousWebView,
				int paramAnonymousInt, String paramAnonymousString1,
				String paramAnonymousString2) {
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
			ExplainVideoActivity.this.isErrorPage = true;
		}

		public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView,
				String paramAnonymousString) {
			ExplainVideoActivity.this.webView.loadUrl(paramAnonymousString);
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
			return true;
		}
	};

	private final WebChromeClient chromeClient = new WebChromeClient() {
		public void onHideCustomView() {
			ExplainVideoActivity.this.hideCustomView();
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}

		public void onShowCustomView(
				View paramAnonymousView,
				WebChromeClient.CustomViewCallback paramAnonymousCustomViewCallback) {
			ExplainVideoActivity.this.showCustomView(paramAnonymousView,
					paramAnonymousCustomViewCallback);
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	};

	private void hideCustomView() {
		if (this.myView != null) {
			if (this.myCallback != null) {
				this.myCallback.onCustomViewHidden();
				this.myCallback = null;
			}
			ViewGroup localViewGroup = (ViewGroup) this.myView.getParent();
			localViewGroup.removeView(this.myView);
			localViewGroup.addView(this.webView);
			this.myView = null;
			this.isFullVideo = false;
		}
	}

	private void showCustomView(View paramView,
			WebChromeClient.CustomViewCallback paramCustomViewCallback) {
		if (this.myCallback != null) {
			this.myCallback.onCustomViewHidden();
			this.myCallback = null;
			return;
		}
		ViewGroup localViewGroup = (ViewGroup) this.webView.getParent();
		localViewGroup.removeView(this.webView);
		localViewGroup.addView(paramView);
		this.myView = paramView;
		this.myCallback = paramCustomViewCallback;
		this.isFullVideo = true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (this.webView != null) {
			this.webView.clearHistory();
			this.webView.clearView();
			this.webView.destroy();
			this.webView = null;
		}
	}

	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
		if (this.isFullVideo) {
			hideCustomView();
			return true;
		}
		if ((this.webView != null) && (paramInt == 4)
				&& (this.webView.canGoBack())) {
			this.webView.goBack();
			return true;
		}
		return super.onKeyDown(paramInt, paramKeyEvent);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		webView.onPause();
		MobclickAgent.onPageEnd("ExplainVideoActivity");
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		webView.onResume();
		MobclickAgent.onPageStart("ExplainVideoActivity");
		MobclickAgent.onPause(this);
	}

}
