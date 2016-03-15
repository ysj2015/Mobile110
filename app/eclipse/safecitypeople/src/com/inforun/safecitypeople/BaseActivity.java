package com.inforun.safecitypeople;

import java.util.HashMap;
import java.util.Set;


import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.inforun.safecitypeople.R;
import com.inforun.safecitypeople.request.XRequestListener;
import com.inforun.safecitypeople.request.XRequestManager;
import com.inforun.safecitypeople.request.XResponse;
import com.inforun.safecitypeople.App;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.util.LogUtils;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * activity父类
 * 
 * @author x
 * 
 */
public class BaseActivity extends FragmentActivity implements BDLocationListener{
	/**
	 * 相册里取图片请求码
	 */
	protected static final int ResultCode_DICM = 1008;
	/**
	 * 相机里取图片请求码
	 */
	protected static final int ResultCode_Camera = 10010;
	
	protected Button btn_left;
	protected Button btn_right;
	protected ActionBar actionBar;

	// 后来的
	public String CLASS_TAG;
	protected TextView actionBarMidTitle;
	protected LinearLayout actionBarLeftLayout;
	protected LinearLayout actionBarRightLayout;
	protected Button actionBarRightButton;
	protected ImageButton actionBarLeftImageButton;
	
	protected RelativeLayout actionbar_parent;
	protected LinearLayout leftLinear;
	protected ImageView img_title_left;
	protected LocationClient mLocationClient;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		((App)getApplication()).addActivityToList(this);
		initActionBar();
	}
	protected void initLocation() {
		mLocationClient = new LocationClient(this); // 声明LocationClient类
		mLocationClient.registerLocationListener(this);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02 bd09ll
		int span = 10000;
		option.setScanSpan(span);// 设置发起定位请求的间隔时间为10s
		option.setOpenGps(true); // 打开gps
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
		if (mLocationClient != null) {
			mLocationClient.start();
		} else {
			Log.d("LocSDK4", "locClient is null or not started");
		}
	}
	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	/**
	 * 自定义actionbar
	 */
	protected void initActionBar() {
		ActionBar actionBar = getActionBar();
		if (actionBar == null) {
			System.out.println("actionBar == null");
			return;
		}
		RelativeLayout customActionBarView = (RelativeLayout) LayoutInflater.from(
				this).inflate(R.layout.actionbar_nav, null);
		actionbar_parent = (RelativeLayout) customActionBarView
				.findViewById(R.id.actionbar_parent);
		actionBarMidTitle = (TextView) customActionBarView
				.findViewById(R.id.mid_title);
		actionBarLeftImageButton = (ImageButton) customActionBarView
				.findViewById(R.id.leftImageButton);
		leftLinear = (LinearLayout) customActionBarView
				.findViewById(R.id.leftLinear);
		
				//左上角按钮
				actionBarLeftImageButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						clickLeftImageButton(view, actionBarLeftLayout);
					}
				});
				//左上角按钮点击事件
				leftLinear.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});

				actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
						ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
								| ActionBar.DISPLAY_SHOW_TITLE);
				actionBar.setCustomView(customActionBarView,
						new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
								ViewGroup.LayoutParams.MATCH_PARENT));

	}

	/**
	 * actionBar 隐藏
	 */
	protected void setActionBarVisible(boolean visible) {
		if (actionbar_parent != null) {
			if (visible) {
				actionbar_parent.setVisibility(View.VISIBLE);
			} else {
				actionbar_parent.setVisibility(View.GONE);
			}
		}
	}





	/**
	 * 设置title
	 * 
	 * @param title
	 */
	public void setActionBarMidTitle(String title) {
		if (actionBarMidTitle != null) {
			actionBarMidTitle.setText(title);
		}
	}

	/**
	 * 设置右上角按钮文字
	 * 
	 * @param title
	 */
	protected void setRightTitle(String title) {
		if (actionBarRightButton != null) {
			actionBarRightButton.setText(title);
		}
	}

	/**
	 * 设置右上角的按钮文字大小
	 */
	protected void setRightSize(float size) {
		if (actionBarRightButton != null) {
			actionBarRightButton.setTextSize(size);
		}
	}

	/**
	 * 设置rightbutton显示与不显示
	 */

	protected void setRightButtonVisible(boolean visible) {
		if (actionBarRightButton != null) {
			if (visible) {
				actionBarRightButton.setVisibility(View.VISIBLE);
			} else {
				actionBarRightButton.setVisibility(View.INVISIBLE);
			}
		}
	}

	/**
	 * 设置右上角按钮background
	 * 
	 * @param drawableID
	 */
	protected void setRightButtonBackground(int drawableID) {
		if (actionBarRightButton != null) {
			actionBarRightButton.setBackgroundResource(drawableID);
		}
	}

	/**
	 * 点击右上角按钮
	 */
	protected void clickRightButton(View view, View parent) {

	}

	/**
	 * 点击左上角按钮
	 */
	protected void clickLeftImageButton(View view, View parent) {
		finish();
	}

	/**
	 * 设置左上角按钮resource
	 * 
	 * @param resID
	 */
	protected void setLeftImageButtonResouce(int resID) {
		if (actionBarLeftImageButton != null) {
			actionBarLeftImageButton.setImageResource(resID);
		}
	}

	// 左边的imageview显示与不显示
	protected void setLeftImageViewVisible(boolean visible) {
		if (img_title_left != null && actionBarLeftImageButton != null) {
			if (visible) {
				img_title_left.setVisibility(View.VISIBLE);
				actionBarLeftImageButton.setVisibility(View.GONE);
			} else {
				img_title_left.setVisibility(View.GONE);
				actionBarLeftImageButton.setVisibility(View.VISIBLE);
			}

		}

	}

	/**
	 * 右上角imageview点击事件
	 */
	protected void clickRightImageView() {

	}

	/**
	 * 显示或隐藏左上角View
	 * 
	 * @param visiable
	 */
	protected void setActionBarLeftLayoutVisiable(boolean visiable) {
		if (actionBarLeftLayout != null) {
			actionBarLeftLayout.setVisibility(visiable ? View.VISIBLE
					: View.INVISIBLE);
		}
	}

	/**
	 * 显示或隐藏右上角View
	 * 
	 * @param visiable
	 */
	protected void setActionBarRightLayoutVisiable(boolean visiable) {
		if (actionBarRightLayout != null) {
			actionBarRightLayout.setVisibility(visiable ? View.VISIBLE
					: View.INVISIBLE);
		}
	}



	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.stop();
		}
		super.onDestroy();
	}

	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
	    @Override
	    public void gotResult(int code, String alias, Set<String> tags) {
	        String logs ;
	        switch (code) {
	        case 0:
	            logs = "Set tag and alias success";
	            System.out.println(logs);
	            //Log.i(TAG, logs);
	            // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
	            break;
	        case 6002:
	            logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
	            System.out.println(logs);
	            //Log.i(TAG, logs);
	            // 延迟 60 秒来调用 Handler 设置别名
	            mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
	            break;
	        default:
	            logs = "Failed with errorCode = " + code;
	            System.out.println(logs);
	            //Log.e(TAG, logs);
	        }
	         
	        //ExampleUtil.showToast(logs, getApplicationContext());
	    }
	};
	 
	protected static final int MSG_SET_ALIAS = 1001;
	protected final Handler mHandler = new Handler() {
	    @Override
	    public void handleMessage(android.os.Message msg) {
	        super.handleMessage(msg);
	        switch (msg.what) {
	        case MSG_SET_ALIAS:
	            // 调用 JPush 接口来设置别名。
	        	System.out.println((String) msg.obj);
	            JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
	            break;
	        }
	    }
	};
	@Override
	public void onReceiveLocation(BDLocation arg0) {
		// TODO Auto-generated method stub
		Intent in = new Intent();
		in.setAction("location");
		System.out.println("BaseActivity  " +arg0.getLatitude()+ " "+arg0.getLongitude());
		in.putExtra("lat", arg0.getLatitude());
		in.putExtra("lon", arg0.getLongitude());
		sendBroadcast(in);
	}
	
}
