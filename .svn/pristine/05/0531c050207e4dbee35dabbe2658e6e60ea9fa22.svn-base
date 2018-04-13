package com.xyd.student.xydexamanalysis.ui;

import com.umeng.analytics.MobclickAgent;
import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.utils.MyHttpUtil;
import com.xyd.student.xydexamanalysis.view.LoadingHelper;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnErrorListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * 
 *		
 */
public class ViewVideoActivity extends Activity {
	private LoadingHelper loadingHelper;
	private VideoView vv;
	private String path, wk_reourceid, wk_url;
	private ProgressBar pb;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.context = this;

		if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this)) {
			return;
		}
		wk_reourceid = getIntent().getStringExtra("wk_reourceid");
		wk_url = getIntent().getStringExtra("wk_url");

		Log.i("info", "wk_reourceid:" + wk_reourceid + ";wk_url:" + wk_url);
		setView();
		getVideoPath();
	}

	private void setView() {
		setContentView(R.layout.vedioxml);
		// path = getIntent().getStringExtra("wk_url");
		loadingHelper = new LoadingHelper(
				findViewById(R.id.loading_prompt_linear),
				findViewById(R.id.loading_empty_prompt_linear));
		loadingHelper.ShowLoading();
		pb = (ProgressBar) findViewById(R.id.head_progressBar);
		vv = (VideoView) findViewById(R.id.surface_view);
	}

	private void getVideoPath() {
		MyHttpUtil httpUtil = MyHttpUtil.getInstance(context);
		httpUtil.request(wk_url, wk_reourceid, new MyHttpUtil.HttpCallback() {
			@Override
			public void success(String result) {
				Log.i("Lichg,success", result);
				// if (result==null) {
				// Toast.makeText(ViewVideoActivity.this, "微课资源不存在！！",
				// Toast.LENGTH_SHORT).show()
				// }else {
				path = result;
				Log.i("info", "播放视频的url:" + path);
				// path =
				// "http://weike.iclassmate.cn:81/WeiKe/201505/1919/d2332ba7f72641a8b0456dba663d4ad1/d2332ba7f72641a8b0456dba663d4ad1.flv";

				System.out.print(result);
				initView();
				loadingHelper.HideLoading(View.INVISIBLE);
				// }

			}

			@Override
			public void error(int state, String errorMsg) {
				System.out.print("state=" + state + "----" + "errorMsg="
						+ errorMsg);
				Log.i("info", "state=" + state + "----" + "errorMsg="
						+ errorMsg);
				loadingHelper.ShowError(errorMsg);
			}
		});
	}

	private void initView() {

		vv.setVideoPath(path);

		vv.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
		vv.setMediaController(new MediaController(this));
		vv.setOnErrorListener(new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				// TODO Auto-generated method stub

				new AlertDialog.Builder(context)
						.setTitle("无法播放")
						.setMessage("对不起，视频暂时无法播放")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										ViewVideoActivity.this.finish();
										// 设置退出

									}
								}).setCancelable(false).show();
				return false;
			}
		});

		vv.setOnInfoListener(new OnInfoListener() {

			@Override
			public boolean onInfo(MediaPlayer arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				switch (arg1) {
				case MediaPlayer.MEDIA_INFO_BUFFERING_START:
					if (vv.isPlaying()) {
						vv.pause();
					}
					pb.setVisibility(View.VISIBLE);
					break;
				case MediaPlayer.MEDIA_INFO_BUFFERING_END:
					vv.start();
					pb.setVisibility(View.GONE);
					break;

				default:
					break;
				}
				return false;
			}
		});
	}

	/**
	 * 友盟统计
	 */
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("ViewVideoActivity");
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("ViewVideoActivity");
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		vv.pause();
		super.onStop();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		if (vv != null)
			vv.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		setContentView(R.layout.nulll);
		System.gc();
	}

}