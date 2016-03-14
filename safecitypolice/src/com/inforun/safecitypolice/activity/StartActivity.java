package com.inforun.safecitypolice.activity;

import org.json.JSONObject;

import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.SessionManager;
import com.inforun.safecitypolice.finals.Constants;
import com.inforun.safecitypolice.request.AsyncHttpManager;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;

public class StartActivity extends Activity {
	private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		System.out.println("start");
        autoLogin();
		
		
	}
	private void autoLogin() {
		System.out.println("autoLogin");
		final PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
		AsyncHttpManager.getAsyncHttpClient().setCookieStore(myCookieStore);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		editor = preferences.edit();
		String policeNo = preferences.getString("policeNo", "");
		String token = preferences.getString("token", "");
		if(policeNo.equals("") || token.equals("")) {
			System.out.println("login");
			Intent in = new Intent(this,LoginActivity.class);
			startActivity(in);
			finish();
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
						startActivity(new Intent(StartActivity.this,MainActivity.class));
						//context.startService(new Intent(context,LocationService.class));
						startCheckLocationStream();
						finish();
						//finish();
					}
				}
				catch(Exception e) {
					startActivity(new Intent(StartActivity.this,LoginActivity.class));
					finish();
//					Toast.makeText(LoginActivity.this, "系统异常，请稍候再试", Toast.LENGTH_LONG).show();
				}

			}
			public void onFailure(Throwable arg0) { // 失败，调用
				startActivity(new Intent(StartActivity.this,LoginActivity.class));
				finish();
	        }
	        public void onFinish() { // 完成后调用，失败，成功，都要掉
	        	
	        }
		}
				
				
		);
	}
	public void startCheckLocationStream() {
		Intent in = new Intent("CheckLocationStreamReceiver");
		PendingIntent pi = PendingIntent.getBroadcast(this,0,in,0);
		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),30*1000,pi); 
	}
}
