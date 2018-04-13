package com.xyd.student.xydexamanalysis.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpStatus;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.constant.Constant;
import com.xyd.student.xydexamanalysis.entity.Explain_single_info;
import com.xyd.student.xydexamanalysis.view.MyScoreView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

public class StudentPaperActivity extends Activity implements OnClickListener {
	private Explain_single_info info;
	private ImageView paper_tuichu, paper_paper;
	private String url;
	private Double personScore;
	private int screenWidth, screenHeight, x, y, w, h;
	private String[] xy;
	private MyScoreView scoreView;
	private Bitmap bm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paper);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		initView();
		Intent intent = getIntent();
		info = (Explain_single_info) intent.getSerializableExtra("info");
		String prefixUrl = info.getPrefixUrl();
		String pageUrl = info.getPageUrl();
		String errorXywh = info.getErrorXywh();
		personScore = info.getScore();
		xy = errorXywh.split(",");
		x = Integer.parseInt(xy[0]);
		y = Integer.parseInt(xy[1]);
		w = Integer.parseInt(xy[2]);
		h = Integer.parseInt(xy[3]);
		if (pageUrl == null || pageUrl.equals("null")) {
			Toast.makeText(StudentPaperActivity.this, "暂无试卷", Toast.LENGTH_LONG)
					.show();
			StudentPaperActivity.this.finish();
		} else {
			if (prefixUrl == null || prefixUrl.equals("null")) {
				url = Constant.BASE_FILEURL + pageUrl;
			} else {
				url = prefixUrl + pageUrl;
			}
			boolean flag = checkUrl(url);
			if (flag) {
				bm = getBitmapFromNetWork(url).copy(Bitmap.Config.ARGB_8888,
						true);
				Bitmap bitmap = Bitmap.createBitmap(bm, x, y, w, h);

				paper_paper.setImageBitmap(bitmap);

				Class<?> c = null;
				Object obj = null;
				java.lang.reflect.Field field = null;
				int x = 0, sbar = 0;
				try {
					c = Class.forName("com.android.internal.R$dimen");
					obj = c.newInstance();
					field = c.getField("status_bar_height");
					x = Integer.parseInt(field.get(obj).toString());
					sbar = getResources().getDimensionPixelSize(x);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				scoreView.setScore(personScore + "");
				scoreView.setsX(screenWidth / 2);
				scoreView.setsY((screenHeight - sbar - 20) / 2);
			} else {
				Toast.makeText(StudentPaperActivity.this, "暂无试卷",
						Toast.LENGTH_LONG).show();
				StudentPaperActivity.this.finish();
			}
		}
	}

	public boolean checkUrl(String url) {
		boolean value = false;
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url)
					.openConnection();
			int code = conn.getResponseCode();
			if (code != 200) {
				value = false;
			} else {
				value = true;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	private Bitmap getBitmapFromNetWork(String url) {
		URL imageUrl = null;
		Bitmap bitmap = null;
		InputStream inputStream = null;
		HttpURLConnection httpURLConnection = null;
		ByteArrayOutputStream byteArrayOutputStream = null;

		try {
			imageUrl = new URL(url);
			httpURLConnection = (HttpURLConnection) imageUrl.openConnection();
			httpURLConnection.setConnectTimeout(5 * 1000);
			httpURLConnection.setReadTimeout(10 * 1000);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			if (httpURLConnection.getResponseCode() == HttpStatus.SC_OK) {
				inputStream = httpURLConnection.getInputStream();
				byteArrayOutputStream = new ByteArrayOutputStream();
				int len = 0;
				byte[] buffer = new byte[1024];
				while ((len = inputStream.read(buffer)) != -1) {
					byteArrayOutputStream.write(buffer, 0, len);
					byteArrayOutputStream.flush();
				}
				byte[] imageData = byteArrayOutputStream.toByteArray();
				bitmap = BitmapFactory.decodeByteArray(imageData, 0,
						imageData.length);
			} else {
				System.out.println("图片请求失败");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("e=" + e.toString());
		} finally {
			try {
				if (byteArrayOutputStream != null) {
					byteArrayOutputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				if (httpURLConnection != null) {
					httpURLConnection.disconnect();
				}
			} catch (Exception e) {
				System.out.println("e=" + e.toString());
			}
		}
		return bitmap;
	}

	public void initView() {
		paper_tuichu = (ImageView) findViewById(R.id.paper_tuichu);
		paper_paper = (ImageView) findViewById(R.id.paper_paper);
		scoreView = (MyScoreView) findViewById(R.id.paper_scoreView);
		paper_tuichu.setOnClickListener(this);
		paper_paper.setOnClickListener(this);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
	}

	private DisplayImageOptions initDisplayImageOptions() {
		DisplayImageOptions.Builder options = new DisplayImageOptions.Builder();
		options.showImageForEmptyUri(R.drawable.sjloading);
		options.showImageOnFail(R.drawable.sjfail);
		options.resetViewBeforeLoading(true);
		options.cacheInMemory(true);
		options.cacheOnDisk(true);
		options.imageScaleType(ImageScaleType.NONE);
		options.bitmapConfig(Bitmap.Config.RGB_565);
		options.considerExifParams(true);
		options.displayer(new FadeInBitmapDisplayer(300));
		return options.build();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.paper_tuichu:
			StudentPaperActivity.this.finish();
			break;

		case R.id.paper_paper:
			// Toast.makeText(StudentPaperActivity.this,
			// "再次操作",Toast.LENGTH_SHORT).show();
			break;
		}
	}
}
