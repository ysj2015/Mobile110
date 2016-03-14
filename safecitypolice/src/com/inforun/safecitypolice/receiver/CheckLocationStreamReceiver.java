package com.inforun.safecitypolice.receiver;

import java.util.Date;

import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.inforun.safecitypolice.LocationService;
import com.inforun.safecitypolice.MyApplication;
import com.inforun.safecitypolice.SessionManager;
import com.inforun.safecitypolice.finals.Constants;
import com.inforun.safecitypolice.request.AsyncHttpManager;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import android.app.ActivityManager;
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
		Log.v("location","onReceive");
		context = arg0;
		mLocationClient = new LocationClient(arg0); // 声明LocationClient类
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02 bd09ll
		option.setOpenGps(true); // 打开gps
		mLocationClient.setLocOption(option);
		mLocationClient.registerLocationListener(this);
		mLocationClient.start();
		mLocationClient.requestLocation();
		// TODO Auto-generated method stub
//		boolean flag = isMyServiceRunning(arg0);
//		if(flag) {
//			//Toast.makeText(arg0, "LocationService正在运行", Toast.LENGTH_SHORT).show();
//		} else {
//			//Toast.makeText(arg0, "LocationService停止运行", Toast.LENGTH_SHORT).show();
//		}
		
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
		Log.v("location","onReceiveLocation," + x + " " + y);
		refreshCoord(x,y);
	}
	private void refreshCoord(double x,double y) {
		Log.v("location","refreshCoord");
		MobclickAgent.onEvent(context,"location");
		RequestParams params = new RequestParams();
		
		params.put("x", x + "");
		params.put("y", y + "");
		SessionManager.getInstance().setCookieStore(((MyApplication)context.getApplicationContext()).getCookieStore());
		AsyncHttpManager.getAsyncHttpClient().setCookieStore(((MyApplication)context.getApplicationContext()).getCookieStore());
		AsyncHttpManager.getAsyncHttpClient().post(Constants.POLICE_REFRESHCOORD, params, new JsonHttpResponseHandler(){
			public void onSuccess(JSONObject arg0) {
				Log.v("location","location success");
				Log.v("location",arg0.toString());

			}
			public void onFailure(Throwable arg0) { // 失败，调用
	            
	        }
	        public void onFinish() { // 完成后调用，失败，成功，都要掉
	        	
	        }
		}
				
				
		);
		
	}
}
