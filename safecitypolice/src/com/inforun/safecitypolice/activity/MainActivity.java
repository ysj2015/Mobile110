package com.inforun.safecitypolice.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import cn.jpush.android.api.JPushInterface;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.inforun.safecitypolice.BaseActivity;
import com.inforun.safecitypolice.MyApplication;
import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.SessionManager;
import com.inforun.safecitypolice.fragment.CurrentTaskFragment;
import com.inforun.safecitypolice.fragment.HistoryTaskFragment;
import com.inforun.safecitypolice.fragment.MineFragment;
import com.inforun.safecitypolice.utils.UHelper;
import com.umeng.analytics.MobclickAgent;


/**
 * 主菜单
 * 
 * @author x
 * 
 */
public class MainActivity extends BaseActivity implements OnTabChangeListener{

	private LocationClient mLocationClient;
	// view
	public FragmentTabHost tabHost;

	// tab
	private Class<?>[] fragmentArray = new Class[]{ CurrentTaskFragment.class,
			HistoryTaskFragment.class,MineFragment.class};
	private int iconArray[] = { R.drawable.current_button_normal,
			R.drawable.historical_button_normal, R.drawable.personal_button_normal };
	private int iconArray2[] = { R.drawable.current_button_press,
			R.drawable.historical_button_press, R.drawable.personal_button_press };
	private String titleArray[] = new String[3];
	public static RelativeLayout relativeLayout;
	private ArrayList<View> tagView = new ArrayList<View>();
	public final String mPageName = "MainActivity";
	
	
	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		super.onCreate(savedInstanceState);
		if(SessionManager.getInstance().getCookieStore() != null) {
			setContentView(R.layout.activity_main);
			initView();
		} else {
			Intent in = new Intent(this,LoginActivity.class);
			startActivity(in);
			finish();
		}
		
	}

	protected void initView() {
		
		//fragmentArray = new Class[]{ CurrentTaskFragment.class,HistoryTaskFragment.class,MineFragment.class};
		titleArray = getResources().getStringArray(R.array.tab_title);
		tabHost = (FragmentTabHost) findViewById(R.id.tabhost);
		tabHost.setup(this, getSupportFragmentManager(), R.id.content);
		tabHost.getTabWidget().setDividerDrawable(null);
		tabHost.clearAllTabs();
		
		tagView.clear();
		int count = fragmentArray.length;
		System.out.println(count+"");
		System.out.println("tagView.size():" + tagView.size() + "");
		for (int i = 0; i < count; i++) {
			TabHost.TabSpec tabSpec = tabHost.newTabSpec(titleArray[i])
					.setIndicator(getTabItemView(i));

				tabHost.addTab(tabSpec, fragmentArray[i], null);
		}
		tabHost.setOnTabChangedListener(this);
	}
	/**
	 * 获取tabItem view
	 * 
	 * @param index
	 * @return
	 */
	private View getTabItemView(int index) {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View view = layoutInflater.inflate(R.layout.tab_bottom_nav, null);

		ImageView imageView = (ImageView) view.findViewById(R.id.iv_icon);
		imageView.setImageResource(iconArray[index]);


		tagView.add(view);
		return view;
	}

	@Override
	public void onResume() {
		
		super.onResume();
        MobclickAgent.onPageStart(mPageName);
        MobclickAgent.onResume(this);
        
	}

	@Override
	public void onPause() {
		super.onPause();
        MobclickAgent.onPageEnd(mPageName);
        MobclickAgent.onPause(this);
	}

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		if (null != mLocationClient) {
			mLocationClient.stop();
		}
	}

	@Override
	public void onNewIntent(Intent in) {
		super.onNewIntent(in);
		initView();
	}
	
	@Override
	public void onTabChanged(String tabId) {
		System.out.println(tabId);
		ImageView imageView = null;
		View view = null;
		for (int i = 0; i < tagView.size(); i++) {
			view = tagView.get(i);
			imageView = (ImageView) view.findViewById(R.id.iv_icon);
			if (tabId.equals(titleArray[i])) {
				imageView.setImageResource(iconArray2[i]);
			} else {
				imageView.setImageResource(iconArray[i]);
			}
		}
	}

	private long fristTime;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long secondTime = System.currentTimeMillis();
			if (secondTime - fristTime > 2000) {
				UHelper.showToast(this, R.string.press_back_again_finish);
				fristTime = secondTime;
				return true;
			} else {
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
