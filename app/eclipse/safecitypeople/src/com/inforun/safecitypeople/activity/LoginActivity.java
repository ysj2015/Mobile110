package com.inforun.safecitypeople.activity;

import java.util.HashMap;
import java.util.List;

import org.apache.http.cookie.Cookie;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.inforun.safecitypeople.BaseActivity;
import com.inforun.safecitypeople.Constants;
import com.inforun.safecitypeople.R;
import com.inforun.safecitypeople.App;
import com.inforun.safecitypeople.SessionManager;
import com.inforun.safecitypeople.entity.Police;
import com.inforun.safecitypeople.entity.User;
import com.inforun.safecitypeople.request.XRequestListener;
import com.inforun.safecitypeople.request.XRequestManager;
import com.inforun.safecitypeople.request.XResponse;
import com.inforun.safecitypeople.App;
import com.inforun.safecitypeople.request.AsyncHttpManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

public class LoginActivity extends BaseActivity implements OnClickListener{

	EditText et_name,et_phone,et_id;
	private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String id;
    private String name;
    private String tel;
	public void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        autoLogin();
        setContentView(R.layout.activity_login);   
        
        
        setActionBarVisible(false);
        initView();
        initListener();
	}
	protected void initView() {
		// TODO Auto-generated method stub
		et_name = (EditText)findViewById(R.id.edt_name);
		et_phone = (EditText)findViewById(R.id.edt_phone);
		et_id = (EditText)findViewById(R.id.edt_id);
	}

	protected void initListener() {
		// TODO Auto-generated method stub
		findViewById(R.id.btn_login).setOnClickListener(this);
		findViewById(R.id.btn_register).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btn_register:
			Intent in = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(in);
			break;
		case R.id.btn_login:
			login();
			
		}
	}
	private void autoLogin() {
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		editor = preferences.edit();
        name = preferences.getString("name", "");
        id = preferences.getString("shenFenId", "");
        tel = preferences.getString("tel", "");
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
    						mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS,
    								id));
    						getSessionId(myCookieStore);
    						startActivity(new Intent(LoginActivity.this,MainActivity.class));
    						finish();						
    					}
    				}
    				catch(Exception e) {
    					
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
	private void login() {
		final PersistentCookieStore myCookieStore = ((App)this.getApplication()).getCookieStore();
		AsyncHttpManager.getAsyncHttpClient().setCookieStore(myCookieStore);
		String name = et_name.getText().toString().trim();
		String tel = et_phone.getText().toString().trim();
		String shenFenId = et_id.getText().toString().trim();
		RequestParams params = new RequestParams();
		params.put("name", name);
		params.put("shenFenId", shenFenId);
		params.put("tel", tel);
		AsyncHttpManager.getAsyncHttpClient().post(Constants.LOGIN, params, new JsonHttpResponseHandler(){
			public void onSuccess(JSONObject arg0) {
				System.out.println(arg0.toString());
				try{
					int code = arg0.getInt("code");
					if(code == 1) {
						JSONObject user = arg0.getJSONObject("user");
						JSONObject detail = user.getJSONObject("details");
						String shenFenId = detail.getString("shenFenId");
						String tel = detail.getString("tel");
						String name = detail.getString("name");
						editor.putString("name", name);
						editor.putString("shenFenId", shenFenId);
						editor.putString("tel", tel);
						editor.commit();
						mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS,
								shenFenId));
						getSessionId(myCookieStore);
						startActivity(new Intent(LoginActivity.this,MainActivity.class));
						finish();						
					} else {
						String msg = arg0.getString("message"); 
						Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
					}
				}
				catch(Exception e) {
					Toast.makeText(LoginActivity.this, "系统异常，请稍候再试", Toast.LENGTH_LONG).show();
				}

			}
			public void onFailure(Throwable arg0) { // 失败，调用
				Toast.makeText(LoginActivity.this, "请检查网络", Toast.LENGTH_LONG).show();
	        }
	        public void onFinish() { // 完成后调用，失败，成功，都要掉
	        	
	        }
		});
	}
	private void getSessionId(PersistentCookieStore cs) {
		List<Cookie> cookies = cs.getCookies();
        String aa = null;
        for (int i = 0; i < cookies.size(); i++) {
            if ("JSESSIONID".equals(cookies.get(i).getName())) {
                aa = cookies.get(i).getValue();
                break;
            }
        }
        System.out.println("login--"+aa);
        SessionManager.getInstance().setSessionId(aa);
        SessionManager.getInstance().setCookieStore(cs);
	}

}
