package com.xyd.student.xydexamanalysis.utils;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import com.xyd.student.xydexamanalysis.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 時間倒計時 Created by xyd on 2016/3/1.
 */
public class TimeUtils {

	private int time = 60;

	private Timer timer;

	private Button btnSure;

	private String btnText;

	public TimeUtils(Button btnSure, String btnText) {
		super();
		this.btnSure = btnSure;
		this.btnText = btnText;
	}

	public void RunTimer() {
		timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				time--;
				Message msg = handler.obtainMessage();
				msg.what = 1;
				handler.sendMessage(msg);

			}
		};

		timer.schedule(task, 100, 1000);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:

				if (time > 0) {
					btnSure.setEnabled(false);
					btnSure.setBackgroundResource(R.drawable.daojishi);
					btnSure.setText(time + "s");
					btnSure.setTextSize(14);
				} else {
					btnSure.setBackgroundResource(R.drawable.button_shape);
					timer.cancel();
					btnSure.setText(btnText);
					btnSure.setEnabled(true);
					btnSure.setTextSize(14);
				}

				break;

			default:
				break;
			}

		};
	};
}
