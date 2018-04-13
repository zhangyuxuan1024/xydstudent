package com.xyd.student.xydexamanalysis.ui.weike;

import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.ui.QrScanActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class DelwkSelectActivity extends Activity implements OnClickListener {
	private TextView tv_title, tv_left, tv_right;
	private int type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_del_weike);

		Intent intent = getIntent();
		String title = intent.getStringExtra("title");
		String left = intent.getStringExtra("left");
		String right = intent.getStringExtra("right");
		int type = intent.getIntExtra("type", 0);

		tv_title = (TextView) findViewById(R.id.weike_tv_title);
		tv_left = (TextView) findViewById(R.id.weike_tv_left);
		tv_right = (TextView) findViewById(R.id.weike_tv_right);
		tv_title.setText(title);
		tv_left.setText(left);
		tv_right.setText(right);

		tv_left.setOnClickListener(this);
		tv_right.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.weike_tv_left:
			this.finish();
			break;
		case R.id.weike_tv_right:
			if (type == 0) {
				Intent intent = new Intent(DelwkSelectActivity.this,
						SelectWeikeActivity.class);
				setResult(RESULT_OK, intent);
			} else if (type == 1) {
				Intent intent = new Intent(DelwkSelectActivity.this,
						QrScanActivity.class);
				setResult(RESULT_OK, intent);
			}
			this.finish();
			break;
		}
	}
}
