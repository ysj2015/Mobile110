package com.inforun.safecitypeople;

import java.util.HashMap;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.inforun.safecitypeople.Constants;
import com.inforun.safecitypeople.request.XRequestListener;
import com.inforun.safecitypeople.request.XRequestManager;
import com.inforun.safecitypeople.request.XResponse;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.util.LogUtils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class LocationService extends Service implements BDLocationListener{

	private LocationClient mLocationClient;
	
	@Override
	public void onCreate() {
		Log.v("location","onCreate");
		super.onCreate();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v("location","onStartCommand");
		mLocationClient = new LocationClient(this); // 声明LocationClient类
		mLocationClient.registerLocationListener(this);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02 bd09ll
		int span = 30000;
		option.setScanSpan(span);// 设置发起定位请求的间隔时间为10s
		option.setOpenGps(true); // 打开gps
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
		if (mLocationClient != null) {
			mLocationClient.start();
		} else {
			Log.d("LocSDK4", "locClient is null or not started");
		}
		return super.onStartCommand(intent, flags, startId); 
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onReceiveLocation(BDLocation location) {
		// TODO Auto-generated method stub
		Log.v("location","locationCallBack");
		Log.v("location",location.getLatitude()+","+location.getLongitude());
		refreshCoord(location.getLongitude(),location.getLatitude());
	}
	private void refreshCoord(double x,double y) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("x", x);
		map.put("y", y);
		final XRequestManager manager = new XRequestManager(this);
//		manager.getSessionId();
		manager.setProgressDialog(false);
		manager.post(Constants.USER_REFRESHCOORD, map, new XRequestListener() {
			@Override
			public void onRequestStart() {
				manager.setProgressMessage("加载中...");
			}

			@Override
			public void onRequestSuccess(XResponse res) {
				LogUtils.i("tttttttttttttttttttttttttttt");
			}

			@Override
			public void onRequestFailure(HttpException error, String msg) {
				
			}
		});
	}
}
