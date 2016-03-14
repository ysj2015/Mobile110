package com.inforun.safecitypolice.receiver;

import org.json.JSONObject;

import com.inforun.safecitypolice.LocationService;
import com.inforun.safecitypolice.MyApplication;
import com.inforun.safecitypolice.finals.Constants;
import com.inforun.safecitypolice.request.AsyncHttpManager;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;

public class LocationReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		PreferenceManager.getDefaultSharedPreferences(arg0);
		
	}
	private void refreshCoord(double x,double y,Context arg0) {
		Log.v("location", "refreshCoord");
		RequestParams params = new RequestParams();
		params.put("x", x + "");
		params.put("y", y + "");
		AsyncHttpManager.getAsyncHttpClient().setCookieStore(((MyApplication)arg0.getApplicationContext()).getCookieStore());
		AsyncHttpManager.getAsyncHttpClient().post(Constants.POLICE_REFRESHCOORD, params, new JsonHttpResponseHandler(){
			public void onSuccess(JSONObject arg0) {
				try {
					int code = arg0.getInt("code");
					if(code == -1) {
						//LocationService.this.stopSelf();
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
}
