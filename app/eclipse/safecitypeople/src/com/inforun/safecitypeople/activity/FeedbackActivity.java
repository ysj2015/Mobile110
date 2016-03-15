package com.inforun.safecitypeople.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.inforun.safecitypeople.BaseActivity;
import com.inforun.safecitypeople.R;

public class FeedbackActivity extends BaseActivity implements OnClickListener{
	TextView content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		setUpActionBar();
		initView();
		initData();
//		findViewById(R.id.about).setOnClickListener(this);
//		findViewById(R.id.exit).setOnClickListener(this);
	}
	
	private void setUpActionBar() {
		setActionBarVisible(true);
		setActionBarMidTitle("报警反馈");
		setActionBarLeftLayoutVisiable(true);
	}
	
	private void initView() {
		content = (TextView)findViewById(R.id.content);
		findViewById(R.id.help).setOnClickListener(this);
	}
	private void initData() {
		Intent i = getIntent();
		Bundle b = i.getExtras();
		String s = b.getString("status");
		System.out.println(s);
		try {
			JSONObject json = new JSONObject(s);
			String alarmStatus = json.getString("alarmStatus");
			String alarmType = json.getString("alarmType");
			if(alarmType.equals("1")) {
				content.setText("经审核你的报警为假警，请慎重报警");
			} else if(alarmType.equals("2")) {
				content.setText("您的报警信息已经有人上报");
			} else {
				if(alarmStatus.equals("1")) {
					content.setText("警察正在赶来处理");
					findViewById(R.id.help).setVisibility(View.VISIBLE);
				} else {
					content.setText("您提交的报警已处理完毕");
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		switch(status) {
//			case -2:
//				content.setText("您的报警已有别人上报");
//				break;
//			case -1:
//				content.setText("经鉴定，您的报警为假报警，请慎重报警");
//				break;
//		}
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()) {
		case R.id.help:
			Intent in = new Intent(this,HelpActivity.class);
			in.putExtra("status", 1);
			startActivity(in);
			finish();
		}
	}

}
