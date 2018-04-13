package com.xyd.student.xydexamanalysis.ui;

import com.xyd.student.xydexamanalysis.R;
import com.xyd.student.xydexamanalysis.utils.NetWorkUtils;
import com.xyd.student.xydexamanalysis.view.TitleBar;
import com.xyd.student.xydexamanalysis.view.TitleBar.TitleOnClickListener;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyPasswordActivity extends Activity implements
		OnClickListener, TitleOnClickListener {
	private Context mContext;
	private TitleBar titleBar;
	private EditText old_password, new_password, confirm_password;
	private Button modify_confirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.modify_password_layout);
		mContext = this;
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		titleBar = (TitleBar) findViewById(R.id.title_bar);
		titleBar.setTitle(getResources().getString(R.string.modify_pw));
		titleBar.setLeftIcon(R.drawable.x);
		titleBar.setTitleClickListener(this);
		old_password = (EditText) findViewById(R.id.old_password);
		new_password = (EditText) findViewById(R.id.new_password);
		confirm_password = (EditText) findViewById(R.id.confirm_password);
		modify_confirm = (Button) findViewById(R.id.modify_confirm);
		modify_confirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.modify_confirm:
			String oldPs = old_password.getText().toString();
			String newPs = new_password.getText().toString();
			String confirmPs = confirm_password.getText().toString();
			if (!newPs.equals(confirmPs)) {
				Toast.makeText(mContext, "密码输入不一致！", Toast.LENGTH_SHORT).show();
			} else {
				if (!NetWorkUtils.isNetworkAvailable(mContext)) {
					Dialog dialog = NetworkNotAvilableDialog(mContext);
					dialog.show();
					return;
				}
			}
			break;
		default:
			break;
		}
	}

	private Dialog NetworkNotAvilableDialog(Context mContext) {
		final Dialog dialog = new Dialog(mContext, R.style.dialog);
		dialog.setCanceledOnTouchOutside(false);
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.networknotavilable_dialog, null);
		Button confirm_btn = (Button) view
				.findViewById(R.id.netwroknotavil_confirm_btn);
		dialog.setContentView(view);
		confirm_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		return dialog;
	}

	@Override
	public void leftClick() {
		// TODO Auto-generated method stub
		ModifyPasswordActivity.this.finish();
	}

	@Override
	public void rightClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void titleClick() {
		// TODO Auto-generated method stub

	}
}
