package com.inforun.safecitypolice.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.inforun.pulltorefresh.library.PullToRefreshListView;
import com.inforun.pulltorefresh.library.PullToRefreshListView.PullToRefreshListViewListener;
import com.inforun.safecitypolice.BaseActivity;
import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.activity.AlarmInfoActivity.LocationReceiver;
import com.inforun.safecitypolice.adapter.AlarmPictureAdapter;
import com.inforun.safecitypolice.adapter.HistoryTaskAdapter;
import com.inforun.safecitypolice.entity.AlarmInfo;
import com.inforun.safecitypolice.entity.Task;
import com.inforun.safecitypolice.finals.Constants;
import com.inforun.safecitypolice.request.AsyncHttpManager;
import com.inforun.safecitypolice.request.XRequestListener;
import com.inforun.safecitypolice.request.XRequestManager;
import com.inforun.safecitypolice.request.XResponse;
import com.inforun.safecitypolice.utils.DateUtil;
import com.inforun.safecitypolice.utils.UHelper;
import com.lidroid.xutils.exception.HttpException;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.inforun.safecitypolice.view.NoScrollGridView;

public class HistoryTaskDetailActivity extends BaseActivity
		implements OnItemClickListener {
	private String picture,text,coord;
	private String[] pList;
	//百度导航
	private static final String APP_FOLDER_NAME = "BNSDKSimpleDemo";
	public static List<Activity> activityList = new LinkedList<Activity>();
	private String mSDCardPath = null;
	//BNRoutePlanNode sNode = null;
	//BNRoutePlanNode eNode = null;
	double curLat,curLng;
	double alarmLat,alarmLng;
	LocationReceiver mReceiver;
	/**
	 * 报警信息
	 */
	AlarmInfo alarmInfo = null;
	
	Task task = null;
	
	
	/**
	 * 没数据
	 */
	private TextView tv_noDate;
	
	
	private NoScrollGridView picList;
	private TextView tv_alarm_text;
	private ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historytask);
		initView();
		setUpActionBar();
		initData();
//		getData();
	}
	@Override
	public void onResume() {
		mReceiver = new LocationReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("location");
		registerReceiver(mReceiver, filter);
		super.onResume();
	}
	@Override
	public void onPause() {
		unregisterReceiver(mReceiver);
		super.onPause();
	}
	private void setUpActionBar() {
		initActionBar();
		setActionBarVisible(true);
		setActionBarMidTitle("历史任务详情");
		setActionBarLeftLayoutVisiable(true);
	}
	/**
	 * 初始化
	 */
	private void initView() {
		// TODO Auto-generated method stub
		tv_alarm_text = (TextView)findViewById(R.id.tv_alarm_text);
		
		picList = (NoScrollGridView)findViewById(R.id.picList);
		picList.setOnItemClickListener(this);	
	}
	private void initData() {
		Intent in = getIntent();
		Task t = (Task)(in.getExtras().get("task"));
		text = t.getAlarmInfo().getAlarmText();
		if(null == text || text.equals("")) {
			findViewById(R.id.tv_no_text).setVisibility(View.VISIBLE);			
		} else {
			tv_alarm_text.setText(text);
		}
		picture = t.getAlarmInfo().getAlarmImg();
		System.out.println(text);
		System.out.println(picture);
		if(null == picture || picture.equals("")) {
			findViewById(R.id.tv_no_photo).setVisibility(View.VISIBLE);
		} else {
			pList = picture.split(" ");
			AlarmPictureAdapter adapter = new AlarmPictureAdapter(HistoryTaskDetailActivity.this,pList);
			picList.setAdapter(adapter);
		}
	}
//	private void getData() {
//		AsyncHttpManager.getAsyncHttpClient().post(Constants.POLICE_GETCURRTASK, new JsonHttpResponseHandler(){
//			public void onSuccess(JSONObject arg0) {
//				System.out.println(arg0.toString());
//				try {
//					int code = arg0.getInt("code");
//					if(code == 1) {
//						JSONObject currTask = arg0.getJSONObject("currTask");
//						int accept = currTask.getInt("acceptStatus");
//						if(accept == 0) {
//							findViewById(R.id.accept).setVisibility(View.VISIBLE);
//							findViewById(R.id.accept_disabled).setVisibility(View.GONE);
//		                }else if(accept == 1) {
//		                	findViewById(R.id.accept).setVisibility(View.GONE);
//		                	findViewById(R.id.accept_disabled).setVisibility(View.VISIBLE);
//		                }
//						JSONObject alarm = currTask.getJSONObject("alarmInfo");
//						//报警地点
//						coord = alarm.getString("coordStr");
//						System.out.println(coord);
//						String[] loc = coord.split(" ");
//						alarmLat = Double.parseDouble(loc[1]);
//						alarmLng = Double.parseDouble(loc[0]);
//						
//		                //报警信息、图片
//		                text = alarm.getString("alarmText");
//		                if(null == text || text.equals("")) {
//							findViewById(R.id.tv_no_text).setVisibility(View.VISIBLE);			
//						} else {
//							tv_alarm_text.setText(text);
//						}
//		                picture = alarm.getString("alarmImg");
//		                if(null == picture || picture.equals("")) {
//							findViewById(R.id.tv_no_photo).setVisibility(View.VISIBLE);
//						} else {
//							pList = picture.split(" ");
////							AlarmPictureAdapter adapter = new AlarmPictureAdapter(AlarmInfoActivity.this,pList);
//							picList.setAdapter(adapter);
//						}
//					}
//				} catch(Exception e) {
//					
//					Toast.makeText(AlarmInfoActivity.this, "数据解析失败", Toast.LENGTH_LONG).show();
//				}
//			}
//			public void onFailure(Throwable arg0) { // 失败，调用
//				Toast.makeText(AlarmInfoActivity.this, "请求失败,请检查网络", Toast.LENGTH_LONG).show();
//				
//	        }
//	        public void onFinish() { // 完成后调用，失败，成功，都要掉
//	        	
//	        }
//		}
//				
//				
//		);
//	}
	
	
	
	
	
	class LocationReceiver extends BroadcastReceiver {
	
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			curLat = arg1.getDoubleExtra("lat", -1.0);
			curLng = arg1.getDoubleExtra("lon", -1.0);
		}
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent i;
		switch(arg0.getId()){
			case R.id.picList:
				i = new Intent(this, AlarmPictureActivity.class);
				i.putExtra("image", pList[arg2]);
				startActivity(i);
				break;
				
		}
	}
}
