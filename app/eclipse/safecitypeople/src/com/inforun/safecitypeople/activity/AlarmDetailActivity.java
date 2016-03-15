package com.inforun.safecitypeople.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.inforun.safecitypeople.BaseActivity;
import com.inforun.safecitypeople.adapter.AlarmPictureAdapter;
import com.inforun.safecitypeople.entity.AlarmInfo;
import com.inforun.safecitypeople.view.NoScrollGridView;
import com.inforun.safecitypeople.R;

public class AlarmDetailActivity extends BaseActivity implements OnItemClickListener{
	private String picture,text;
	private String[] pList;
	AlarmInfo alarmInfo = null;
	private TextView alarmText;
	private NoScrollGridView picList;;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_detail);
		initView();
		setUpActionBar();
		getData();
	}
	private void initView() {
		alarmText = (TextView)findViewById(R.id.alarm_text);
		picList = (NoScrollGridView)findViewById(R.id.picList);
		picList.setOnItemClickListener(this);
	}
	private void setUpActionBar() {
		initActionBar();
		setActionBarVisible(true);
		setActionBarMidTitle("报警详情");
		setActionBarLeftLayoutVisiable(false);
	}
	
	private void getData() {
		Intent in = getIntent();
		AlarmInfo alarm = (AlarmInfo)(in.getExtras().get("alarm"));
		alarmText.setText(alarm.getAlarmText());
		System.out.println(alarm.getAlarmText());
		picture = alarm.getAlarmImg();
		System.out.println("img:"+picture);
		if(picture == null || picture.equals("")) {
			findViewById(R.id.tv_no_photo).setVisibility(View.VISIBLE);
			picList.setVisibility(View.GONE);
		} else {
			findViewById(R.id.tv_no_photo).setVisibility(View.GONE);
			pList = picture.split(" ");
			System.out.println(pList.length);
			for(int i = 0;i< pList.length;i ++) {
				System.out.println(i+":"+pList[i]);
			}
			AlarmPictureAdapter adapter = new AlarmPictureAdapter(this,pList);
			picList.setAdapter(adapter);
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent i;
		switch(arg0.getId()){
			case R.id.picList:
				i = new Intent(this, HistoryAlarmPictureActivity.class);
				i.putExtra("image", pList[arg2]);
				startActivity(i);
				break;
				
		}
	}
}
