package com.xyd.student.xydexamanalysis.ui;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.umeng.analytics.MobclickAgent;
import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.view.LoadingHelper;
import com.xyd.student.xydexamanalysis.view.TitleBar;
import com.xyd.student.xydexamanalysis.view.TitleBar.TitleOnClickListener;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class ViewImageActivity extends Activity implements TitleOnClickListener {

	private ImageView imageView;
	private static final String TAG = "PhotoViewer";
	public static final int RESULT_CODE_NOFOUND = 200;
	private LoadingHelper loadingHelper;
	private TitleBar titleBar;

	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	DisplayMetrics dm;
	// //ImageView imgView;
	private Bitmap newbitmap;

	/** 最小缩放比例 */
	float minScaleR = 1.0f;
	/** 最大缩放比例 */
	static final float MAX_SCALE = 10f;

	/** 初始状态 */
	static final int NONE = 0;
	/** 拖动 */
	static final int DRAG = 1;
	/** 缩放 */
	static final int ZOOM = 2;

	/** 当前模式 */
	private int sheight;
	int mode = NONE;

	PointF prev = new PointF();
	PointF mid = new PointF();
	float dist = 1f;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		sheight = 0;
		setContentView(R.layout.activity_viewimage);
		titleBar = (TitleBar) findViewById(R.id.imageviewtitle);
		titleBar.setLeftIcon(R.drawable.x);
		titleBar.setTitle("我的试卷");
		titleBar.setTitleClickListener(this);
		imageView = (ImageView) this.findViewById(R.id.imageView);
		loadingHelper = new LoadingHelper(
				findViewById(R.id.loading_prompt_linear),
				findViewById(R.id.loading_empty_prompt_linear));
		loadingHelper.ShowLoading();
		int resourceid = getResources().getIdentifier("status_bar_height",
				"dimen", "android");
		if (resourceid > 0)
			sheight = getResources().getDimensionPixelSize(resourceid);
		if (intent != null) {
			imageView.setTag(intent.getStringExtra("bitmap"));
			DisplayImageOptions imageOptions = initDisplayImageOptions();
			ImageLoader.getInstance().displayImage(
					intent.getStringExtra("bitmap"), imageView, imageOptions,
					simpleloadlistener);
		} else
			imageView.setImageResource(R.drawable.sjfail);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		// 返回键的时候
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (newbitmap != null && !newbitmap.isRecycled()) {
				newbitmap.recycle();
				newbitmap = null;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private Bitmap drawBitmap(Bitmap back) {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		int[] flags = intent.getIntArrayExtra("flags");
		int[] x = intent.getIntArrayExtra("x");
		int[] y = intent.getIntArrayExtra("y");
		int[] h = intent.getIntArrayExtra("h");
		int[] w = intent.getIntArrayExtra("w");
		double[] personScore = intent.getDoubleArrayExtra("personScore");
		/*
		 * Bitmap right=BitmapFactory.decodeResource(getResources(),
		 * R.drawable.right); Bitmap
		 * r_w=BitmapFactory.decodeResource(getResources(), R.drawable.r_w);
		 * Bitmap wrong=BitmapFactory.decodeResource(getResources(),
		 * R.drawable.wrong);
		 */
		Bitmap right = BitmapFactory.decodeResource(getResources(),
				R.drawable.right);
		Bitmap r_w = BitmapFactory.decodeResource(getResources(),
				R.drawable.r_w);
		Bitmap wrong = BitmapFactory.decodeResource(getResources(),
				R.drawable.wrong);

		int bgWidth = back.getWidth();
		int bgHeight = back.getHeight();

		Bitmap newbmp = Bitmap.createBitmap(bgWidth, bgHeight, Config.RGB_565);// 错在这里
		Canvas cv = new Canvas(newbmp);
		cv.drawBitmap(back, 0, 0, null);

		for (int i = 0; i < flags.length; i++) {
			if (flags[i] == 1) {
				float scaleWidth = ((float) w[i]) / right.getWidth();
				float scaleHeight = ((float) h[i]) / right.getHeight();
				Matrix matrix = new Matrix();
				matrix.postScale(scaleWidth, scaleHeight);
				Bitmap newR = Bitmap.createBitmap(right, 0, 0,
						right.getWidth(), right.getHeight(), matrix, true);
				cv.drawBitmap(newR, x[i], y[i], null);
				drawScore(cv, personScore[i], x[i], y[i], matrix, scaleWidth,
						scaleHeight, newR.getWidth());
				newR.recycle();
				newR = null;
			} else if (flags[i] == 2) {
				float scaleWidth = ((float) w[i]) / r_w.getWidth();
				float scaleHeight = ((float) h[i]) / r_w.getHeight();
				Matrix matrix = new Matrix();
				matrix.postScale(scaleWidth, scaleHeight);
				Bitmap newr_w = Bitmap.createBitmap(r_w, 0, 0, r_w.getWidth(),
						r_w.getHeight(), matrix, true);
				cv.drawBitmap(newr_w, x[i], y[i], null);
				drawScore(cv, personScore[i], x[i], y[i], matrix, scaleWidth,
						scaleHeight, newr_w.getWidth());
				newr_w.recycle();
				newr_w = null;
			} else if (flags[i] == 3) {
				float scaleWidth = ((float) w[i]) / wrong.getWidth();
				float scaleHeight = ((float) h[i]) / wrong.getHeight();
				Matrix matrix = new Matrix();
				matrix.postScale(scaleWidth, scaleHeight);
				Bitmap newwrong = Bitmap.createBitmap(wrong, 0, 0,
						wrong.getWidth(), wrong.getHeight(), matrix, true);
				cv.drawBitmap(newwrong, x[i], y[i], null);
				drawScore(cv, personScore[i], x[i], y[i], matrix, scaleWidth,
						scaleHeight, newwrong.getWidth());
				newwrong.recycle();
				newwrong = null;
			}
			if (intent.getIntExtra("page", 1) == 1) {

				double score = intent.getDoubleExtra("score", 0);
				if (score > 0) {
					String scoreString = String.valueOf(score);
					char[] charArray = scoreString.toCharArray();
					int sX = 100;
					int sY = 100;
					for (int j = 0; j < charArray.length; j++) {
						char c = charArray[j];
						switch (c) {
						case '1':
							Bitmap n1 = BitmapFactory.decodeResource(
									getResources(), R.drawable.no_1);
							// Bitmap
							// n1=ImageBitmapUtil.decodeimagemap.get(Integer.valueOf(R.drawable.no_1));
							cv.drawBitmap(n1, sX, sY, null);
							sX += n1.getWidth();
							n1.recycle();
							n1 = null;
							break;
						case '2':
							Bitmap n2 = BitmapFactory.decodeResource(
									getResources(), R.drawable.no_2);
							// Bitmap
							// n2=ImageBitmapUtil.decodeimagemap.get(Integer.valueOf(R.drawable.no_2));
							cv.drawBitmap(n2, sX, sY, null);
							sX += n2.getWidth();
							n2.recycle();
							n2 = null;
							break;
						case '3':
							Bitmap n3 = BitmapFactory.decodeResource(
									getResources(), R.drawable.no_3);
							// Bitmap
							// n3=ImageBitmapUtil.decodeimagemap.get(Integer.valueOf(R.drawable.no_3));
							cv.drawBitmap(n3, sX, sY, null);
							sX += n3.getWidth();
							n3.recycle();
							n3 = null;
							break;
						case '4':
							Bitmap n4 = BitmapFactory.decodeResource(
									getResources(), R.drawable.no_4);
							cv.drawBitmap(n4, sX, sY, null);
							sX += n4.getWidth();
							n4.recycle();
							n4 = null;
							break;
						case '5':
							Bitmap n5 = BitmapFactory.decodeResource(
									getResources(), R.drawable.no_5);
							cv.drawBitmap(n5, sX, sY, null);
							sX += n5.getWidth();
							n5.recycle();
							n5 = null;
							break;
						case '6':
							Bitmap n6 = BitmapFactory.decodeResource(
									getResources(), R.drawable.no_6);
							cv.drawBitmap(n6, sX, sY, null);
							sX += n6.getWidth();
							n6.recycle();
							n6 = null;
							break;
						case '7':
							Bitmap n7 = BitmapFactory.decodeResource(
									getResources(), R.drawable.no_7);
							cv.drawBitmap(n7, sX, sY, null);
							sX += n7.getWidth();
							n7.recycle();
							n7 = null;
							break;
						case '8':
							Bitmap n8 = BitmapFactory.decodeResource(
									getResources(), R.drawable.no_8);
							// Bitmap
							// n8=ImageBitmapUtil.decodeimagemap.get(Integer.valueOf(R.drawable.no_8));
							cv.drawBitmap(n8, sX, sY, null);
							sX += n8.getWidth();
							// n8.recycle();
							n8 = null;
							break;
						case '9':
							Bitmap n9 = BitmapFactory.decodeResource(
									getResources(), R.drawable.no_9);
							// Bitmap
							// n9=ImageBitmapUtil.decodeimagemap.get(Integer.valueOf(R.drawable.no_9));
							cv.drawBitmap(n9, sX, sY, null);
							sX += n9.getWidth();
							n9.recycle();
							n9 = null;
							break;
						case '0':
							Bitmap n0 = BitmapFactory.decodeResource(
									getResources(), R.drawable.no_0);
							// Bitmap
							// n0=ImageBitmapUtil.decodeimagemap.get(Integer.valueOf(R.drawable.no_0));
							cv.drawBitmap(n0, sX, sY, null);
							sX += n0.getWidth();
							n0.recycle();
							n0 = null;
							break;
						case '.':
							Bitmap nd = BitmapFactory.decodeResource(
									getResources(), R.drawable.no_dian);
							// Bitmap
							// nd=ImageBitmapUtil.decodeimagemap.get(Integer.valueOf(R.drawable.no_dian));
							cv.drawBitmap(nd, sX, sY, null);
							sX += nd.getWidth();
							nd.recycle();
							nd = null;
							break;
						default:
							break;
						}
					}
					Bitmap deng = BitmapFactory.decodeResource(getResources(),
							R.drawable.no_00);
					// Bitmap
					// deng=ImageBitmapUtil.decodeimagemap.get(Integer.valueOf(
					// R.drawable.no_00));
					cv.drawBitmap(deng, 130, sY + 100, null);
					deng.recycle();
					deng = null;
				}
			}
		}

		right.recycle();
		right = null;
		r_w.recycle();
		r_w = null;
		wrong.recycle();
		wrong = null;

		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		cv.restore();// 存储

		return newbmp;
	}

	private void drawScore(Canvas cv, double score, int x, int y,
			Matrix matrix, float scaleWidth, float scaleHeight, int i) {
		// TODO Auto-generated method stub

		String scoreString = String.valueOf(score);
		char[] charArray = scoreString.toCharArray();
		int sX = x + i;
		int sY = y;
		for (int j = 0; j < charArray.length; j++) {
			char c = charArray[j];
			switch (c) {
			case '1':
				// Bitmap
				// n1=ImageBitmapUtil.decodeimagemap.get(Integer.valueOf(R.drawable.no_1));
				Bitmap n1 = BitmapFactory.decodeResource(getResources(),
						R.drawable.no_1);
				Bitmap new1 = Bitmap.createBitmap(n1, 0, 0, n1.getWidth(),
						n1.getHeight(), matrix, true);
				cv.drawBitmap(new1, sX, sY, null);
				sX += new1.getWidth();
				new1.recycle();
				new1 = null;
				break;
			case '2':
				Bitmap n2 = BitmapFactory.decodeResource(getResources(),
						R.drawable.no_2);
				Bitmap new2 = Bitmap.createBitmap(n2, 0, 0, n2.getWidth(),
						n2.getHeight(), matrix, true);
				cv.drawBitmap(new2, sX, sY, null);
				sX += new2.getWidth();
				new2.recycle();
				new2 = null;
				break;
			case '3':
				Bitmap n3 = BitmapFactory.decodeResource(getResources(),
						R.drawable.no_3);
				Bitmap new3 = Bitmap.createBitmap(n3, 0, 0, n3.getWidth(),
						n3.getHeight(), matrix, true);
				cv.drawBitmap(new3, sX, sY, null);
				sX += new3.getWidth();
				new3.recycle();
				new3 = null;
				break;
			case '4':
				Bitmap n4 = BitmapFactory.decodeResource(getResources(),
						R.drawable.no_4);
				Bitmap new4 = Bitmap.createBitmap(n4, 0, 0, n4.getWidth(),
						n4.getHeight(), matrix, true);
				cv.drawBitmap(new4, sX, sY, null);
				sX += new4.getWidth();
				new4.recycle();
				new4 = null;
				break;
			case '5':
				Bitmap n5 = BitmapFactory.decodeResource(getResources(),
						R.drawable.no_5);
				Bitmap new5 = Bitmap.createBitmap(n5, 0, 0, n5.getWidth(),
						n5.getHeight(), matrix, true);
				cv.drawBitmap(new5, sX, sY, null);
				sX += new5.getWidth();
				new5.recycle();
				new5 = null;
				break;
			case '6':
				Bitmap n6 = BitmapFactory.decodeResource(getResources(),
						R.drawable.no_6);
				Bitmap new6 = Bitmap.createBitmap(n6, 0, 0, n6.getWidth(),
						n6.getHeight(), matrix, true);
				cv.drawBitmap(new6, sX, sY, null);
				sX += new6.getWidth();
				new6.recycle();
				new6 = null;
				break;
			case '7':
				Bitmap n7 = BitmapFactory.decodeResource(getResources(),
						R.drawable.no_7);
				Bitmap new7 = Bitmap.createBitmap(n7, 0, 0, n7.getWidth(),
						n7.getHeight(), matrix, true);
				cv.drawBitmap(new7, sX, sY, null);
				sX += new7.getWidth();
				new7.recycle();
				new7 = null;
				break;
			case '8':
				Bitmap n8 = BitmapFactory.decodeResource(getResources(),
						R.drawable.no_8);
				Bitmap new8 = Bitmap.createBitmap(n8, 0, 0, n8.getWidth(),
						n8.getHeight(), matrix, true);
				cv.drawBitmap(new8, sX, sY, null);
				sX += new8.getWidth();
				new8.recycle();
				new8 = null;
				break;
			case '9':
				Bitmap n9 = BitmapFactory.decodeResource(getResources(),
						R.drawable.no_9);
				Bitmap new9 = Bitmap.createBitmap(n9, 0, 0, n9.getWidth(),
						n9.getHeight(), matrix, true);
				cv.drawBitmap(new9, sX, sY, null);
				sX += new9.getWidth();
				new9.recycle();
				new9 = null;
				break;
			case '0':
				Bitmap n0 = BitmapFactory.decodeResource(getResources(),
						R.drawable.no_0);
				Bitmap new0 = Bitmap.createBitmap(n0, 0, 0, n0.getWidth(),
						n0.getHeight(), matrix, true);
				cv.drawBitmap(new0, sX, sY, null);
				sX += new0.getWidth();
				new0.recycle();
				new0 = null;
				break;
			case '.':
				Bitmap nd = BitmapFactory.decodeResource(getResources(),
						R.drawable.no_dian);
				Bitmap newd = Bitmap.createBitmap(nd, 0, 0, nd.getWidth(),
						nd.getHeight(), matrix, true);
				cv.drawBitmap(newd, sX, sY, null);
				sX += newd.getWidth();
				newd.recycle();
				newd = null;
				break;
			default:
				break;
			}
		}
	}

	public void SureOnClick(View v) {

	}

	/**
	 * 触屏监听
	 */

	private View.OnTouchListener ontouchlistener = new View.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			// 主点按下
			case MotionEvent.ACTION_DOWN:
				savedMatrix.set(matrix);
				prev.set(event.getX(), event.getY());
				mode = DRAG;
				break;
			// 副点按下
			case MotionEvent.ACTION_POINTER_DOWN:
				dist = spacing(event);
				// 如果连续两点距离大于10，则判定为多点模式
				if (spacing(event) > 10f) {
					savedMatrix.set(matrix);
					midPoint(mid, event);
					mode = ZOOM;
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
				mode = NONE;
				// savedMatrix.set(matrix);
				break;
			case MotionEvent.ACTION_MOVE:
				if (mode == DRAG) {
					matrix.set(savedMatrix);
					matrix.postTranslate(event.getX() - prev.x, event.getY()
							- prev.y);
				} else if (mode == ZOOM) {
					float newDist = spacing(event);
					if (newDist > 10f) {
						matrix.set(savedMatrix);
						float tScale = newDist / dist;
						matrix.postScale(tScale, tScale, mid.x, mid.y);
					}
				}
				break;
			}
			imageView.setImageMatrix(matrix);
			CheckView();
			return true;
		}
	};

	/**
	 * 限制最大最小缩放比例，自动居中
	 */
	private void CheckView() {
		float p[] = new float[9];
		matrix.getValues(p);
		if (mode == ZOOM) {
			if (p[0] < minScaleR) {
				// Log.d("", "当前缩放级别:"+p[0]+",最小缩放级别:"+minScaleR);
				matrix.setScale(minScaleR, minScaleR);
			}
			if (p[0] > MAX_SCALE) {
				// Log.d("", "当前缩放级别:"+p[0]+",最大缩放级别:"+MAX_SCALE);
				matrix.set(savedMatrix);
			}
		}
		center(newbitmap);
	}

	/**
	 * 最小缩放比例，最大为100%
	 */
	private void minZoom() {
		minScaleR = Math.min(
				(float) dm.widthPixels / (float) newbitmap.getWidth(),
				(float) (dm.heightPixels - titleBar.getHeight())
						/ (float) newbitmap.getHeight());
		if (minScaleR < 1.0) {
			matrix.postScale(minScaleR, minScaleR);
		}
	}

	private void center(Bitmap bitmap) {
		center(true, true, bitmap);
	}

	/**
	 * 横向、纵向居中
	 */
	protected void center(boolean horizontal, boolean vertical, Bitmap bitmap) {

		Matrix m = new Matrix();
		m.set(matrix);
		RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
		m.mapRect(rect);

		float height = rect.height();
		float width = rect.width();

		float deltaX = 0, deltaY = 0;

		if (vertical) {
			// 图片小于屏幕大小，则居中显示。大于屏幕，上方留空则往上移，下方留空则往下移
			int screenHeight = dm.heightPixels - titleBar.getHeight() - sheight;
			if (height < screenHeight) {
				deltaY = (screenHeight - height) / 2 - rect.top;
			} else if (rect.top > 0) {
				deltaY = -rect.top;
			} else if (rect.bottom < screenHeight) {
				deltaY = imageView.getHeight() - rect.bottom;
			}
		}

		if (horizontal) {
			int screenWidth = dm.widthPixels;
			if (width < screenWidth) {
				deltaX = (screenWidth - width) / 2 - rect.left;
			} else if (rect.left > 0) {
				deltaX = -rect.left;
			} else if (rect.right < screenWidth) {
				deltaX = screenWidth - rect.right;
			}
		}
		matrix.postTranslate(deltaX, deltaY);
	}

	/**
	 * 两点的距离
	 */
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	/**
	 * 两点的中点
	 */
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
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

	private SimpleImageLoadingListener simpleloadlistener = new SimpleImageLoadingListener() {

		public void onLoadingStarted(String imageUri, View view) {
			// spinner.setVisibility(View.VISIBLE);
		};

		public void onLoadingFailed(String imageUri, View view,
				FailReason failReason) {

			// CreatDialog.creatUserToast(mcontext, null, "同步图片失败").show();
			// spinner.setVisibility(View.GONE);
			loadingHelper.ShowError("同步图片失败");
		};

		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			// spinner.setVisibility(View.GONE);
			// view.setTag(imageUri);
			// view.setOnClickListener(onclick);
			// imageView.setOnTouchListener(this);// 设置触屏监听
			if (loadedImage != null) {

				dm = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(dm);// 获取分辨率

				ImageView imageview = (ImageView) view;
				Bitmap newBitmap = null;
				try {
					newBitmap = drawBitmap(loadedImage);
				} catch (Exception e) {
					// TODO: handle exception
					loadingHelper.HideLoading(View.INVISIBLE);
				}
				if (newBitmap != null) {
					newbitmap = newBitmap;
					minZoom();
					center(newBitmap);
					imageview.setImageMatrix(matrix);
					imageview.setImageBitmap(newBitmap);
					// bitmap = loadedImage;
					loadingHelper.HideLoading(View.INVISIBLE);
					imageview.setOnTouchListener(ontouchlistener);// 设置触屏监听
				}
			}
			// imageView.setImageBitmap(newBitmap);
		};
	};

	@Override
	public void leftClick() {
		// TODO Auto-generated method stub
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

	/**
	 * 友盟统计
	 */
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("ViewImageActivity");
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("ViewImageActivity");
		MobclickAgent.onPause(this);
	}
}