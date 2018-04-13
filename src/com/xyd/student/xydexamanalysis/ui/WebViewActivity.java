package com.xyd.student.xydexamanalysis.ui;

import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.view.TitleBar;
import com.xyd.student.xydexamanalysis.view.TitleBar.TitleOnClickListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public class WebViewActivity extends Activity implements TitleOnClickListener {
	private WebView webView;
	private LinearLayout linearLayout;
	private TitleBar titleBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		webView = (WebView) findViewById(R.id.webView);
		linearLayout = (LinearLayout) findViewById(R.id.view_linear);
		titleBar = (TitleBar) findViewById(R.id.title_bar);
		titleBar.getTitle().setCompoundDrawables(null, null, null, null);
		titleBar.setTitle("播放视频");
		titleBar.setLeftIcon(R.drawable.back_icon);
		titleBar.setTitleClickListener(this);

		try {
			WebSettings settings = webView.getSettings();
			// 设置 缓存模式
			settings.setCacheMode(WebSettings.LOAD_DEFAULT);
			// 开启 DOM storage API 功能
			settings.setDomStorageEnabled(true);
			// 是否使用缓存
			settings.setAppCacheEnabled(true);

			// 支持javascript
			settings.setJavaScriptEnabled(true);
			// 设置可以支持缩放
			settings.setSupportZoom(true);
			// 设置出现缩放工具
			settings.setBuiltInZoomControls(true);
			settings.setDisplayZoomControls(false);
			// 扩大比例的缩放
			webView.getSettings().setUseWideViewPort(true);
			// 自适应屏幕
			// webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			// webView.getSettings().setLoadWithOverviewMode(true);

			webView.getSettings().setUseWideViewPort(true);
			webView.getSettings().setLoadWithOverviewMode(true);
			webView.setWebViewClient(new WebViewClient() {
				@Override
				public void onPageFinished(WebView view, String url) {
					linearLayout.setVisibility(View.GONE);
				}

				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
					view.loadUrl(url);
					return true;
				}
			});
			webView.setWebChromeClient(new WebChromeClient() {
				@Override
				public void onProgressChanged(WebView view, int newProgress) {
					super.onProgressChanged(view, newProgress);
					if (newProgress > 90) {
						linearLayout.setVisibility(View.GONE);
					}
				}
			});
			// webView.getSettings().setPluginState(PluginState.ON);
			webView.setWebChromeClient(new WebChromeClient());
			WindowManager wm = (WindowManager) this
					.getSystemService(Context.WINDOW_SERVICE);
			int width = wm.getDefaultDisplay().getWidth();
			if (width > 650) {
				this.webView.setInitialScale(190);
			} else if (width > 520) {
				this.webView.setInitialScale(160);
			} else if (width > 450) {
				this.webView.setInitialScale(140);
			} else if (width > 300) {
				this.webView.setInitialScale(120);
			} else {
				this.webView.setInitialScale(100);
			}
			settings.setUserAgentString("0"); // 0为手机默认, 1为PC台机，2为IPHONE
			settings.setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.2.1; zh-cn; MB525 Build/3.4.2-117) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");

			Intent intent = getIntent();
			String url = intent.getStringExtra("url");
			webView.loadUrl(url);
		} catch (Exception e) {
			this.finish();
		}
	}

	@Override
	public void leftClick() {
		finish();
	}

	@Override
	public void onBackPressed() {
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

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (webView != null) {
			try {
				webView.destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (webView != null) {
			webView.onResume();
		}
	}

	@Override
	protected void onPause() {
		if (webView != null) {
			webView.reload();
			webView.onPause();
		}
		super.onPause();
	}
}