package com.inforun.safecitypolice.activity;

import com.inforun.safecitypolice.BaseActivity;
import com.inforun.safecitypolice.R;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		setUpActionBar();
		getVersion();
	}
	
	private void setUpActionBar() {
		setActionBarVisible(true);
		setActionBarMidTitle("关于系统");
		setActionBarLeftLayoutVisiable(true);
	}
	/**
	 * 获取版本号
	 * @return 当前应用的版本号
	 */
	public void getVersion() {
	    try {
	        PackageManager manager = this.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
	        String version = info.versionName;
	        TextView tv = (TextView)findViewById(R.id.version);
	        tv.setText("V"+version);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
