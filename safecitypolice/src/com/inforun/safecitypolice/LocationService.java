package com.inforun.safecitypolice;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.inforun.safecitypolice.finals.Constants;
import com.inforun.safecitypolice.request.AsyncHttpManager;
import com.inforun.safecitypolice.request.SocketHttpRequester;
import com.inforun.safecitypolice.request.XRequestListener;
import com.inforun.safecitypolice.request.XRequestManager;
import com.inforun.safecitypolice.request.XResponse;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.util.LogUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

public class LocationService extends Service implements BDLocationListener{

	private LocationClient mLocationClient;
	double x,y;
	private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
	@Override
	public void onCreate() {
		super.onCreate();
		
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v("location","onStartCommand");
		initLocation();
		mLocationClient.requestLocation();
		return START_STICKY; 
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
		x = location.getLongitude();
		y = location.getLatitude();
//		preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        editor = preferences.edit();
//        editor.putString("x", x+"");
//        editor.putString("y", y+"");
		refreshCoord(x,y);
	}
	private void initLocation() {
		if (mLocationClient == null) {
			mLocationClient = new LocationClient(this); // 声明LocationClient类
			mLocationClient.registerLocationListener(this);
		}
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02 bd09ll
		int span = 30000;
		option.setScanSpan(span);// 设置发起定位请求的间隔时间为30s
		option.setOpenGps(true); // 打开gps
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
		if (mLocationClient != null) {
			mLocationClient.start();
		} else {
			Log.d("LocSDK4", "locClient is null or not started");
		}
	}
	private void refreshCoord(double x,double y) {
		Log.v("location", "refreshCoord");
		
		RequestParams params = new RequestParams();
		
		params.put("x", x + "");
		params.put("y", y + "");
		AsyncHttpManager.getAsyncHttpClient().setCookieStore(((MyApplication)getApplication()).getCookieStore());
		AsyncHttpManager.getAsyncHttpClient().post(Constants.POLICE_REFRESHCOORD, params, new JsonHttpResponseHandler(){
			public void onSuccess(JSONObject arg0) {
				try {
					int code = arg0.getInt("code");
					if(code == -1) {
						LocationService.this.stopSelf();
					}
				} catch(Exception e) {
					
				}
			}
			public void onFailure(Throwable arg0) { // 失败，调用
	            
	        }
	        public void onFinish() { // 完成后调用，失败，成功，都要掉
	        	
	        }
		}
				
				
		);
		
	}
	@Override
	public void onDestroy() { 
		Intent in = new Intent(this, LocationService.class);
		startService(in);
		super.onDestroy();
	}
}
