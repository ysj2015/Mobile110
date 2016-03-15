package com.inforun.safecitypeople.activity;

import org.json.JSONObject;

import com.inforun.safecitypeople.App;
import com.inforun.safecitypeople.R;
import com.inforun.safecitypeople.SessionManager;
import com.inforun.safecitypeople.Constants;
import com.inforun.safecitypeople.request.AsyncHttpManager;
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
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		editor = preferences.edit();
        String name = preferences.getString("name", "");
        String id = preferences.getString("shenFenId", "");
        String tel = preferences.getString("tel", "");
        if(!name.equals("") && !id.equals("") && !tel.equals("")) {
        	final PersistentCookieStore myCookieStore = ((App)this.getApplication()).getCookieStore();
    		AsyncHttpManager.getAsyncHttpClient().setCookieStore(myCookieStore);
    		RequestParams params = new RequestParams();
    		params.put("name", name);
    		params.put("shenFenId", id);
    		params.put("tel", tel);
    		AsyncHttpManager.getAsyncHttpClient().post(Constants.LOGIN, params, new JsonHttpResponseHandler(){
    			public void onSuccess(JSONObject arg0) {
    				System.out.println(arg0.toString());
    				try{
    					int code = arg0.getInt("code");
    					if(code == 1) {
    						
    						//getSessionId(myCookieStore);
    						startActivity(new Intent(StartActivity.this,MainActivity.class));
    						finish();						
    					}
    				}
    				catch(Exception e) {
    					startActivity(new Intent(StartActivity.this,LoginActivity.class));
						finish();
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
        } else {
        	startActivity(new Intent(StartActivity.this,LoginActivity.class));
			finish();
        }
	}
	public void startCheckLocationStream() {
		Intent in = new Intent("CheckLocationStreamReceiver");
		PendingIntent pi = PendingIntent.getBroadcast(this,0,in,0);
		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),30*1000,pi); 
	}
}
