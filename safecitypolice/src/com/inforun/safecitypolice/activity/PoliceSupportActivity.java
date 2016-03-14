package com.inforun.safecitypolice.activity;

import android.os.Bundle;

import com.inforun.safecitypolice.BaseActivity;
import com.inforun.safecitypolice.R;

public class PoliceSupportActivity extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_police_support);
		setUpActionBar();
	}
	private void setUpActionBar() {
		setActionBarVisible(true);
		setActionBarMidTitle(getString(R.string.support));
		setActionBarLeftLayoutVisiable(false);
	}
}
