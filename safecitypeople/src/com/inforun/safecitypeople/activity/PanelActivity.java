package com.inforun.safecitypeople.activity;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.inforun.safecitypeople.BaseActivity;
import com.inforun.safecitypeople.R;
import com.inforun.safecitypeople.SessionManager;
import com.inforun.safecitypeople.entity.UserDetails;
import com.inforun.safecitypeople.App;
import com.inforun.safecitypeople.Constants;
import com.inforun.safecitypeople.request.XRequestListener;
import com.inforun.safecitypeople.request.XRequestManager;
import com.inforun.safecitypeople.request.XResponse;
import com.lidroid.xutils.exception.HttpException;

public class PanelActivity extends BaseActivity implements OnClickListener {
	
	private TextView tv_type;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        initView();
        initData();
        initListener();
	}
	
	protected void initView() {
		// TODO Auto-generated method stub
		tv_type = (TextView)findViewById(R.id.tv_type);
		
		
	}

	private void initData() {
//		UserDetails detail = SessionManager.getInstance().getUser().getDetails();
//		t_name.setText(detail.getName());
//		String sex = new String(new char[]{detail.getSex()});
//		t_sex.setText(sex);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		t_birthday.setText(sdf.format(detail.getBirthday()));
	}
	
	protected void initListener() {
		// TODO Auto-generated method stub
		//findViewById(R.id.alarm).setOnClickListener(this);
		findViewById(R.id.traffic_accident).setOnClickListener(this);
		findViewById(R.id.qj).setOnClickListener(this);
		findViewById(R.id.other).setOnClickListener(this);
		findViewById(R.id.bj).setOnClickListener(this);
		findViewById(R.id.dj).setOnClickListener(this);
		findViewById(R.id.thief).setOnClickListener(this);
		findViewById(R.id.alarm_button).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent in;
		switch(v.getId()){
//		case R.id.alarm:
//			in = new Intent(this,SendAlarmActivity.class);
//			startActivity(in);
//			break;
		case R.id.traffic_accident:
			tv_type.setText("车祸");
			break;
		case R.id.qj:
			tv_type.setText("抢劫");
			break;
		case R.id.other:
			tv_type.setText("其他");
			break;
		case R.id.bj:
			tv_type.setText("绑架");
			break;
		case R.id.thief:
			tv_type.setText("偷窃");
			break;
		case R.id.dj:
			tv_type.setText("打架");
			break;
		case R.id.alarm_button:
			in = new Intent();
			in.putExtra("type", tv_type.getText().toString());
			in.setClass(this, AlarmInfoActivity.class);
			startActivity(in);
			
			
		}
	}
	
}
