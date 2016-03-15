package com.inforun.safecitypolice.receiver;

import org.json.JSONObject;

import com.inforun.safecitypolice.LocationService;
import com.inforun.safecitypolice.MyApplication;
import com.inforun.safecitypolice.SessionManager;
import com.inforun.safecitypolice.activity.LoginActivity;
import com.inforun.safecitypolice.activity.MainActivity;
import com.inforun.safecitypolice.finals.Constants;
import com.inforun.safecitypolice.request.AsyncHttpManager;
import com.inforun.safecitypolice.utils.WtService;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			
			login(context); 
			
		}
	}
	private void login(final Context context) {
		final PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
		AsyncHttpManager.getAsyncHttpClient().setCookieStore(myCookieStore);
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		editor = preferences.edit();
		String policeNo = preferences.getString("policeNo", "");
		String token = preferences.getString("token", "");
		if(policeNo.equals("") || token.equals("")) {
			return;
		}
		RequestParams params = new RequestParams();
		params.put("policeNo", policeNo);
		params.put("token", token);
		AsyncHttpManager.getAsyncHttpClient().post(Constants.POLICE_LOGIN, params, new JsonHttpResponseHandler(){
			public void onSuccess(JSONObject arg0) {
				System.out.println(arg0.toString());
				try{
					int code = arg0.getInt("code");
					if(code == 1) {
						JSONObject police = arg0.getJSONObject("police");
						//要保存在本地，开机自动登录policeNo+token
						String policeNo = police.getString("policeNo");
						String token = arg0.getString("token");
						editor.putString("policeNo", policeNo);
						editor.putString("token", token);
						editor.commit();
//						mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS,
//								policeNo));
						SessionManager.getInstance().setCookieStore(myCookieStore);
						Intent service = new Intent(context, WtService.class);
						context.startService(service);
					}
				}
				catch(Exception e) {
//					Toast.makeText(LoginActivity.this, "系统异常，请稍候再试", Toast.LENGTH_LONG).show();
				}

			}
			public void onFailure(Throwable arg0) { // 失败，调用
	            
	        }
	        public void onFinish() { // 完成后调用，失败，成功，都要掉
	        	
	        }
		}
				
				
		);
	}
	public void startCheckLocationStream(Context context) {
		Intent in = new Intent("CheckLocationStreamReceiver");
		PendingIntent pi = PendingIntent.getBroadcast(context,0,in,0);
		AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),30*1000,pi); 
	}
}
