package com.inforun.safecitypolice.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.inforun.safecitypolice.BaseActivity;
import com.inforun.safecitypolice.LocationService;
import com.inforun.safecitypolice.MyApplication;
import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.SessionManager;
import com.inforun.safecitypolice.activity.AlarmInfoActivity.LocationReceiver;
import com.inforun.safecitypolice.entity.Police;
import com.inforun.safecitypolice.finals.Constants;
import com.inforun.safecitypolice.request.AsyncHttpManager;
import com.inforun.safecitypolice.request.XRequestListener;
import com.inforun.safecitypolice.request.XRequestManager;
import com.inforun.safecitypolice.request.XResponse;
import com.inforun.safecitypolice.utils.StringUtils;
import com.inforun.safecitypolice.utils.UHelper;
import com.inforun.safecitypolice.utils.WtService;
import com.lidroid.xutils.exception.HttpException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;

import org.json.JSONObject;

/**
 * 登录界面
 * 
 * @author xiongchaoxi
 * 
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
	/**
	 * 登录
	 */
	private Button btn_login;
	/**
	 * 注册
	 */
	private TextView btn_register;
	/**
	 * 找回密码
	 */
	private Button btn_forget;
	/**
	 * 用户名
	 */
	private EditText edt_username;
	/**
	 * 密码
	 */
	private EditText edt_password;
	private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
//        autoLogin();
		
		
	}

	/**
	 * 初始化组件
	 */
	private void initView() {

		// TODO Auto-generated method stub

		btn_login = (Button) findViewById(R.id.btn_login);
		btn_register = (TextView)findViewById(R.id.btn_register);
		
		edt_username = (EditText) findViewById(R.id.edt_username);
		edt_password = (EditText) findViewById(R.id.edt_password);
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
	}


	private void setUpActionBar() {
		setActionBarVisible(false);
//		setActionBarMidTitle(getString(R.string.login));
//		setActionBarLeftLayoutVisiable(false);
	}

//	@Override
//	@SuppressLint("NewApi")
//	protected void initActionBar() {
//		// TODO Auto-generated method stub
//		super.initActionBar();
//	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_login:// 登录
//			startActivity(new Intent(LoginActivity.this,MainActivity.class));
//			finish();
			login();
			//policeLogin();
			
			break;

		case R.id.btn_register:// 注册
			startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
			break;


		default:
			break;
		}
	}

	/**
	 * 检查登录名、密码是否为空
	 * 
	 * @return
	 */
	private boolean checkNotNull() {
		String loginName = edt_username.getText().toString().trim();
		String loginPwd = edt_password.getText().toString().trim();
		if (StringUtils.isEmpty(loginName)) {
			UHelper.showToast(LoginActivity.this, R.string.input_username);
			return false;
		} else if (StringUtils.isEmpty(loginPwd)) {
			UHelper.showToast(LoginActivity.this, R.string.input_password);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 警察登录操作
	 */

	private void login() {
		final PersistentCookieStore myCookieStore = ((MyApplication)this.getApplication()).getCookieStore();
		AsyncHttpManager.getAsyncHttpClient().setCookieStore(myCookieStore);
		String loginName = edt_username.getText().toString().trim();
		String loginPwd = edt_password.getText().toString().trim();
		RequestParams params = new RequestParams();
		params.put("policeNo", loginName);
		params.put("pwd", loginPwd);
		AsyncHttpManager.getAsyncHttpClient().post(Constants.POLICE_LOGIN, params, new JsonHttpResponseHandler(){
			public void onSuccess(JSONObject arg0) {
				System.out.println(arg0.toString());
				try{
					int code = arg0.getInt("code");
					String msg = arg0.getString("message");
					if(code == 1) {
						JSONObject police = arg0.getJSONObject("police");
						//要保存在本地，开机自动登录policeNo+token
						String policeNo = police.getString("policeNo");
						String token = arg0.getString("token");
						editor.putString("policeNo", policeNo);
						editor.putString("token", token);
						editor.commit();
						System.out.println(policeNo);
						mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS,
								policeNo));
						SessionManager.getInstance().setCookieStore(myCookieStore);
						startActivity(new Intent(LoginActivity.this,MainActivity.class));
						//startCheckLocationStream();
						Intent service = new Intent(LoginActivity.this, WtService.class);
						startService(service);
						finish();
					} else {
						Toast.makeText(LoginActivity.this, "登录失败，请检查登录信息", Toast.LENGTH_LONG).show();
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
		}
				
				
		);
		
	}
	
	public void startCheckLocationStream() {
		Intent in = new Intent("CheckLocationStreamReceiver");
		PendingIntent pi = PendingIntent.getBroadcast(this,0,in,0);
		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),30*1000,pi); 
	}
	public void startAlarm() {
		Intent in = new Intent(this,LocationReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, in, 0);
		AlarmManager manager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
		manager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),30*1000,pi); 
	}
}
