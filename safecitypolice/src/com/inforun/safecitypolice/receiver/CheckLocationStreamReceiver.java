package com.inforun.safecitypolice.receiver;

import java.util.Date;

import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.inforun.safecitypolice.LocationService;
import com.inforun.safecitypolice.LogUtil;
import com.inforun.safecitypolice.MyApplication;
import com.inforun.safecitypolice.SessionManager;
import com.inforun.safecitypolice.activity.SettingActivity;
import com.inforun.safecitypolice.finals.Constants;
import com.inforun.safecitypolice.request.AsyncHttpManager;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class CheckLocationStreamReceiver extends BroadcastReceiver
	implements BDLocationListener{

	private LocationClient mLocationClient;
	private Context context;
	@Override
	public void onReceive(Context arg0, Intent arg1) {
//		Log.v("location","onReceive");
//		LogUtil.write("CheckLocationStreamReceiver onReceive\n");
//		context = arg0;
//		mLocationClient = new LocationClient(arg0); // 声明LocationClient类
//		LogUtil.write("new LocationClient");
//		LocationClientOption option = new LocationClientOption();
//		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
//		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02 bd09ll
//		option.setScanSpan(0);
//		option.setOpenGps(true); // 打开gps
//		mLocationClient.setLocOption(option);
//		LogUtil.write("LocationClientOption");
//		mLocationClient.registerLocationListener(this);
//		LogUtil.write("registerLocationListener");
//		mLocationClient.start();
//		LogUtil.write("start");
//		mLocationClient.requestLocation();
//		LogUtil.write("requestLocation");
		// TODO Auto-generated method stub
//		boolean flag = isMyServiceRunning(arg0);
//		if(flag) {
//			//Toast.makeText(arg0, "LocationService正在运行", Toast.LENGTH_SHORT).show();
//		} else {
//			//Toast.makeText(arg0, "LocationService停止运行", Toast.LENGTH_SHORT).show();
//		}
//		context = arg0;
//		mLocationClient = new LocationClient(arg0); // 声明LocationClient类
//		mLocationClient.registerLocationListener(this);
//		LocationClientOption option = new LocationClientOption();
//		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
//		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02 bd09ll
//		option.setOpenGps(true); // 打开gps
//		option.setIsNeedAddress(true);
//		mLocationClient.setLocOption(option);
//		mLocationClient.start();
//		LogUtil.write("LocationClient.start");
//		LogUtil.write(new Date().toString());
	}
	private boolean isMyServiceRunning(Context arg0) {
	    ActivityManager manager = (ActivityManager) arg0.getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if ("com.inforun.safecitypolice.LocationService".equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	@Override
	public void onReceiveLocation(BDLocation arg0) {
		// TODO Auto-generated method stub
		double x = arg0.getLongitude();
		double y = arg0.getLatitude();
//		arg0.getLocType();
//		LogUtil.write("CheckLocationStreamReceiver onReceiveLocation" + x+","+y+"\n");
//		refreshCoord(x,y);
		int result = arg0.getLocType();
		LogUtil.write("-----result:" + result+"-----\n");
		if(arg0.getLocType() == BDLocation.TypeGpsLocation) {//61
			LogUtil.write("GPS success " + x + " " + y+"\n");
			refreshCoord(x,y);
		} else if (arg0.getLocType() == BDLocation.TypeNetWorkLocation) {//161
			LogUtil.write("NetWork success " + x + " " + y+"\n");
			refreshCoord(x,y);
		} else if (arg0.getLocType() == BDLocation.TypeOffLineLocation) {//66
			LogUtil.write("OffLine success " + x + " " + y+"\n");
			refreshCoord(x,y);
		} else if (arg0.getLocType() == BDLocation.TypeServerError) {//167
			LogUtil.write("ServerError\n");
		} else if (arg0.getLocType() == BDLocation.TypeNetWorkException) {//63
			LogUtil.write("NetWorkException\n");
		} else if (arg0.getLocType() == BDLocation.TypeCriteriaException) {//62
			LogUtil.write("CriteriaException\n");
        }
		mLocationClient.stop();
	}
	private void refreshCoord(double x,double y) {
		Log.v("location","refreshCoord");
		LogUtil.write("CheckLocationStreamReceiver refreshCoord" + x+","+y+"\n");
		RequestParams params = new RequestParams();
		
		params.put("x", x + "");
		params.put("y", y + "");
		SessionManager.getInstance().setCookieStore(((MyApplication)context.getApplicationContext()).getCookieStore());
		AsyncHttpManager.getAsyncHttpClient().setCookieStore(((MyApplication)context.getApplicationContext()).getCookieStore());
		AsyncHttpManager.getAsyncHttpClient().post(Constants.POLICE_REFRESHCOORD, params, new JsonHttpResponseHandler(){
			public void onSuccess(JSONObject arg0) {
				LogUtil.write("CheckLocationStreamReceiver "+arg0.toString()+"\n");
				try {
					int code = arg0.getInt("code");
					if(code == -1) {
						AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
				        Intent intent = new Intent("CheckLocationStreamReceiver");
				        PendingIntent pendIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
				        alarmMgr.cancel(pendIntent);
					}
				} catch(Exception e) {}
			}
			public void onFailure(Throwable arg0) { // 失败，调用
	            
	        }
	        public void onFinish() { // 完成后调用，失败，成功，都要掉
	        	
	        }
		}
				
				
		);
		
	}
}
