package com.inforun.safecitypolice.activity;

import java.util.HashMap;

import com.inforun.safecitypolice.BaseActivity;
import com.inforun.safecitypolice.MyApplication;
import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.finals.Constants;
import com.inforun.safecitypolice.request.XRequestListener;
import com.inforun.safecitypolice.request.XRequestManager;
import com.inforun.safecitypolice.request.XResponse;
import com.lidroid.xutils.exception.HttpException;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class SettingActivity extends BaseActivity implements OnClickListener{
	private Dialog dialog;
	private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		setUpActionBar();
		findViewById(R.id.about).setOnClickListener(this);
		findViewById(R.id.exit).setOnClickListener(this);
	}
	
	private void setUpActionBar() {
		setActionBarVisible(true);
		setActionBarMidTitle("设置");
		setActionBarLeftLayoutVisiable(true);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()) {
		case R.id.about:
			Intent i = new Intent(this,AboutActivity.class);
			startActivity(i);
			break;
		case R.id.exit:
			exitLogin();
		}
	}
	/**
	 * 退出登录dialog
	 */
	private void exitLogin() {
		// 把自定义的View找到
		final View view = LayoutInflater.from(this).inflate(
				R.layout.exists, null);
		// 创建dialog
		dialog = new AlertDialog.Builder(this).create();
		dialog.show();
		dialog.setContentView(view);

		view.findViewById(R.id.btncalcel).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
		view.findViewById(R.id.btn_ok).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						exit();
					}
				});
		// ((MyApplication)getActivity().getApplication()).clearUserInfo(true);
	}

	/**
	 * 退出操作
	 */
	private void exit() {

		final XRequestManager manager = new XRequestManager(this);
		manager.setProgressDialog(false);
		manager.post(Constants.POLICE_EXIT, new HashMap<String, Object>(),
				new XRequestListener() {
					@Override
					public void onRequestStart() {
						//manager.setProgressMessage("正在退出...");
					}

					@Override
					public void onRequestSuccess(XResponse res) {
						preferences = PreferenceManager.getDefaultSharedPreferences(SettingActivity.this);
				        editor = preferences.edit();
				        editor.clear();
				        editor.commit();

				        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
				        Intent intent = new Intent("CheckLocationStreamReceiver");
				        PendingIntent pendIntent = PendingIntent.getBroadcast(SettingActivity.this, 0, intent, 0);
				        alarmMgr.cancel(pendIntent);
				        //editor.
						((MyApplication) SettingActivity.this.getApplication()).exit();
					}

					@Override
					public void onRequestFailure(HttpException error, String msg) {
						
					}
				});
	}
}
