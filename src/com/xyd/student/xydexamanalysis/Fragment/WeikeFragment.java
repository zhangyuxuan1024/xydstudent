package com.xyd.student.xydexamanalysis.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.umeng.analytics.MobclickAgent;
import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.application.BaseApplication;
import com.xyd.student.xydexamanalysis.ui.QrScanActivity;
import com.xyd.student.xydexamanalysis.ui.weike.ScanRecordActivity;
import com.xyd.student.xydexamanalysis.utils.UIUtils;

public class WeikeFragment extends Fragment implements OnClickListener {
	private Context context;
	private BaseApplication baseApplication;
	private ImageView imageScan;
	private LinearLayout weike_record_linear;

	public WeikeFragment() {
		super();
	}

	public WeikeFragment(Context context) {
		super();
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View wkView = View.inflate(UIUtils.getContext(),
				R.layout.weike_frame_layout, null);
		context = getActivity();
		initView(wkView);

		return wkView;
	}

	private void initView(View wkView) {
		imageScan = (ImageView) wkView.findViewById(R.id.weike_scan);
		weike_record_linear = (LinearLayout) wkView
				.findViewById(R.id.weike_record_linear);

		imageScan.setOnClickListener(this);
		weike_record_linear.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.weike_scan:
			Intent intent1 = new Intent(getActivity(), QrScanActivity.class);
			startActivity(intent1);
			getActivity().overridePendingTransition(R.anim.in_anim,
					R.anim.out_anim2);
			break;
		case R.id.weike_record_linear:
			Intent intent2 = new Intent(getActivity(), ScanRecordActivity.class);
			startActivity(intent2);
			getActivity().overridePendingTransition(R.anim.in_anim, R.anim.out_anim2);
			break;
		default:
			break;
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("WeikeFragment");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("WeikeFragment");
	}
}
