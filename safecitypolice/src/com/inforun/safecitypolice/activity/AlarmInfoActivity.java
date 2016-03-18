package com.inforun.safecitypolice.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.adapter.BaiduNaviManager.NaviInitListener;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.inforun.safecitypolice.BaseActivity;
import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.adapter.AlarmPictureAdapter;
import com.inforun.safecitypolice.entity.AlarmInfo;
import com.inforun.safecitypolice.entity.Police;
import com.inforun.safecitypolice.entity.Task;
import com.inforun.safecitypolice.finals.Constants;
import com.inforun.safecitypolice.request.AsyncHttpManager;
import com.inforun.safecitypolice.request.XRequestListener;
import com.inforun.safecitypolice.request.XRequestManager;
import com.inforun.safecitypolice.request.XResponse;
import com.inforun.safecitypolice.utils.StringUtils;
import com.inforun.safecitypolice.utils.UHelper;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.inforun.safecitypolice.view.NoScrollGridView;
/**
 * 报警信息界面
 * 
 * @author daniel
 * 
 */
public class AlarmInfoActivity extends BaseActivity implements
		OnClickListener,OnItemClickListener{

	private String picture,text,coord;
	private String[] pList;
	//百度导航
	private static final String APP_FOLDER_NAME = "BNSDKSimpleDemo";
	public static List<Activity> activityList = new LinkedList<Activity>();
	private String mSDCardPath = null;
//	BNRoutePlanNode sNode = null;
//	BNRoutePlanNode eNode = null;
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
		setContentView(R.layout.activity_alarminfo);
		initView();
		setUpActionBar();
		getData();
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
		setActionBarMidTitle("当前任务");
		setActionBarLeftLayoutVisiable(true);
	}
	/**
	 * 初始化
	 */
	private void initView() {
		// TODO Auto-generated method stub
		tv_alarm_text = (TextView)findViewById(R.id.tv_alarm_text);
		
		picList = (NoScrollGridView)findViewById(R.id.picList);
		findViewById(R.id.accept).setOnClickListener(this);
		findViewById(R.id.finish).setOnClickListener(this);
		picList.setOnItemClickListener(this);
		//findViewById(R.id.watch_video).setOnClickListener(this);
		
		
	}
	private void getData() {
		AsyncHttpManager.getAsyncHttpClient().post(Constants.POLICE_GETCURRTASK, new JsonHttpResponseHandler(){
			public void onSuccess(JSONObject arg0) {
				System.out.println(arg0.toString());
				try {
					int code = arg0.getInt("code");
					if(code == 1) {
						JSONObject currTask = arg0.getJSONObject("currTask");
						int accept = currTask.getInt("acceptStatus");
						if(accept == 0) {
							findViewById(R.id.accept).setVisibility(View.VISIBLE);
							findViewById(R.id.accept_disabled).setVisibility(View.GONE);
							findViewById(R.id.finish).setVisibility(View.GONE);
							findViewById(R.id.finish_disabled).setVisibility(View.VISIBLE);
		                }else if(accept == 1) {
		                	findViewById(R.id.accept).setVisibility(View.GONE);
		                	findViewById(R.id.accept_disabled).setVisibility(View.VISIBLE);
		                	findViewById(R.id.finish).setVisibility(View.VISIBLE);
							findViewById(R.id.finish_disabled).setVisibility(View.GONE);
		                }
						JSONObject alarm = currTask.getJSONObject("alarmInfo");
						//报警地点
						coord = alarm.getString("coordStr");
						System.out.println(coord);
						String[] loc = coord.split(" ");
						alarmLat = Double.parseDouble(loc[1]);
						alarmLng = Double.parseDouble(loc[0]);
						
		                //报警信息、图片
		                text = alarm.getString("alarmText");
		                if(null == text || text.equals("")) {
							findViewById(R.id.tv_no_text).setVisibility(View.VISIBLE);			
						} else {
							tv_alarm_text.setText(text);
						}
		                picture = alarm.getString("alarmImg");
		                if(null == picture || picture.equals("")) {
							findViewById(R.id.tv_no_photo).setVisibility(View.VISIBLE);
						} else {
							pList = picture.split(" ");
							AlarmPictureAdapter adapter = new AlarmPictureAdapter(AlarmInfoActivity.this,pList);
							picList.setAdapter(adapter);
						}
					}
				} catch(Exception e) {
					
					Toast.makeText(AlarmInfoActivity.this, "数据解析失败", Toast.LENGTH_LONG).show();
				}
			}
			public void onFailure(Throwable arg0) { // 失败，调用
				Toast.makeText(AlarmInfoActivity.this, "请求失败,请检查网络", Toast.LENGTH_LONG).show();
				
	        }
	        public void onFinish() { // 完成后调用，失败，成功，都要掉
	        	
	        }
		}
				
				
		);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		Intent in = new Intent();
		switch (v.getId()) {
			case R.id.accept:			
				
				acceptTask();
				break;
			case R.id.finish:
				Intent i = new Intent(this,SubmitTaskActivity.class);
				startActivity(i);
				
		}
	}
	
	private void acceptTask() {
		final XRequestManager manager = new XRequestManager(this);
		HashMap<String,Object> map = new HashMap<String,Object>();
		manager.post(Constants.POLICE_ACCEPTTASK, map, new XRequestListener() {
			@Override
			public void onRequestStart() {
				manager.setProgressMessage("加载中...");
			}

			@Override
			public void onRequestSuccess(XResponse res) {
				int code = res.getStatusCode();
				if (code == 1) {
					UHelper.showToast(AlarmInfoActivity.this, "接受任务成功");
					setResult(100);
					finish();
				} else
					UHelper.showToast(AlarmInfoActivity.this, res.getMessage());
			}

			@Override
			public void onRequestFailure(HttpException error, String msg) {
				//noDate(false);
			}
		});
	}

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
