package com.inforun.safecitypolice.activity;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.inforun.safecitypolice.BaseActivity;
import com.inforun.safecitypolice.LocationService;
import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.SessionManager;
import com.inforun.safecitypolice.finals.Constants;
import com.inforun.safecitypolice.request.AsyncHttpManager;
import com.inforun.safecitypolice.utils.RegExpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class RegisterActivity extends BaseActivity implements OnClickListener {
	EditText edit_policeno,edit_mobile,edit_name;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		setUpActionBar();
		initView();
	}
	private void setUpActionBar() {
		setActionBarVisible(true);
		setActionBarMidTitle("注册");
		setActionBarLeftLayoutVisiable(true);
	}
	private void initView() {
		// TODO Auto-generated method stub
		edit_policeno = (EditText)findViewById(R.id.edt_policeno);
		edit_policeno.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {  
		    @Override  
		    public void onFocusChange(View v, boolean hasFocus) {  
		        if(hasFocus) {
		        	findViewById(R.id.policeno_hint).setVisibility(View.VISIBLE);
				} else {
					findViewById(R.id.policeno_hint).setVisibility(View.INVISIBLE);
				}
		    }
		});

		edit_mobile = (EditText)findViewById(R.id.edt_mobile);
		edit_mobile.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {  
		    @Override  
		    public void onFocusChange(View v, boolean hasFocus) {  
		        if(hasFocus) {
		        	findViewById(R.id.mobile_hint).setVisibility(View.VISIBLE);
				} else {
					findViewById(R.id.mobile_hint).setVisibility(View.INVISIBLE);
				}
		    }
		});
		edit_name = (EditText)findViewById(R.id.edt_name);
		edit_name.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {  
		    @Override  
		    public void onFocusChange(View v, boolean hasFocus) {  
		        if(hasFocus) {
		        	findViewById(R.id.name_hint).setVisibility(View.VISIBLE);
				} else {
					findViewById(R.id.name_hint).setVisibility(View.INVISIBLE);
				}
		    }
		});
		findViewById(R.id.register).setOnClickListener(this);
	}
	public void register() {
		RequestParams params = new RequestParams();
		params.put("policeNo", edit_policeno.getText().toString().trim());
		params.put("tel", edit_mobile.getText().toString().trim());
		params.put("name", edit_name.getText().toString().trim());
		params.put("address", "");
		AsyncHttpManager.getAsyncHttpClient().post(Constants.POLICE_REGIST, params, new JsonHttpResponseHandler(){
			public void onSuccess(JSONObject arg0) {
				System.out.println(arg0.toString());
				try{
					int code = arg0.getInt("code");
					String msg = arg0.getString("message");
					if(code == 1) {
						Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
						finish();
					} else {
						Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_LONG).show();
					}
				}
				catch(Exception e) {
					Toast.makeText(RegisterActivity.this, "系统异常，请稍候再试", Toast.LENGTH_LONG).show();
				}

			}
			public void onFailure(Throwable arg0) { // 失败，调用
				Toast.makeText(RegisterActivity.this, "请检查网络", Toast.LENGTH_LONG).show();
	        }
	        public void onFinish() { // 完成后调用，失败，成功，都要掉
	        	
	        }
		}
				
				
		);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()) {
		case R.id.register:
			String policeno = edit_policeno.getText().toString().trim();
			String mobile = edit_mobile.getText().toString().trim();
			String name = edit_name.getText().toString().trim();
			if(RegExpUtils.isPoliceNo(policeno) && RegExpUtils.isChinese(name)
					&& RegExpUtils.isMobile(mobile)) {
				register();
			} else {
				Toast.makeText(RegisterActivity.this, "输入信息不正确，请重新输入！", Toast.LENGTH_LONG).show();
			}
			break;
		}
	}
}
