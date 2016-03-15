package com.inforun.safecitypeople.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.google.gson.reflect.TypeToken;
import com.inforun.safecitypeople.BaseActivity;
import com.inforun.safecitypeople.Constants;
import com.inforun.safecitypeople.R;
import com.inforun.safecitypeople.entity.Coord;
import com.inforun.safecitypeople.entity.Police;
import com.inforun.safecitypeople.receiver.JpushReceiver;
import com.inforun.safecitypeople.request.XRequestListener;
import com.inforun.safecitypeople.request.XRequestManager;
import com.inforun.safecitypeople.request.XResponse;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.util.LogUtils;

public class HelpActivity extends BaseActivity implements
		OnGetPoiSearchResultListener, OnGetSuggestionResultListener {
	private TextView tv;
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	LocationReceiver mReceiver;
	public static final String ACTION_UPDATEUI = "action.updateUI";
	private JpushReceiver receiver;
	double lat, lng;
	private static final String STATUE = "JPUSH";
	PoiSearch mPoiSearch;
	SuggestionSearch mSuggestionSearch;
	ArrayList<MarkerOptions> orgList;
	private Timer mTimer;
	boolean isFirstLoc = true;
	BitmapDescriptor bdOrg = BitmapDescriptorFactory
			.fromResource(R.drawable.police_station_icon);
	BitmapDescriptor bdPoliceTask = BitmapDescriptorFactory
			.fromResource(R.drawable.have_task_icon);
	BitmapDescriptor bdPoliceNoTask = BitmapDescriptorFactory
			.fromResource(R.drawable.no_task_icon);

	class LocationReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			lat = arg1.getDoubleExtra("lat", -1.0);
			lng = arg1.getDoubleExtra("lon", -1.0);
			if (isFirstLoc) {
				mPoiSearch.searchNearby((new PoiNearbySearchOption())
						.location(new LatLng(lat, lng)).radius(1000).pageNum(0)
						.keyword("公安局"));
				isFirstLoc = false;
			}
			displayUserLocation(mBaiduMap, lat, lng);

		}

	}

	private TimerTask mTimerTask = new TimerTask() {

		@Override
		public void run() {
			System.out.println("TimerTask");
			initData();
			mHandler.sendEmptyMessage(1);
		}
	};
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// tv.setText(new Date().toString());
				break;
			}
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		tv = (TextView) findViewById(R.id.alarm_deal);
		IntentFilter filter =new IntentFilter();
		filter.addAction("cn.jpush.android.intent.REGISTRATION");
		filter.addAction("cn.jpush.android.intent.UNREGISTRATION");
		filter.addAction("cn.jpush.android.intent.MESSAGE_RECEIVED");
		filter.addAction("cn.jpush.android.intent.NOTIFICATION_RECEIVED");
		filter.addAction("cn.jpush.android.intent.NOTIFICATION_OPENED");
		filter.addAction("cn.jpush.android.intent.ACTION_RICHPUSH_CALLBACK");
		filter.addAction("cn.jpush.android.intent.CONNECTION");
		filter.addCategory("com.inforun.safecitypeople");
		receiver = new JpushReceiver(tv);
		registerReceiver(receiver, filter);
		//这里只是再次注册广播，更新UI的操作放到广播里面
		SharedPreferences sp = getSharedPreferences(STATUE, Activity.MODE_PRIVATE);
		int i = sp.getInt("status", 0);
		if(i==0){
			tv.setText("报警已发送成功，正在审核，请勿离开报警位置");
		}else if(i ==1){
			tv.setText("报警已发送成功，正在审核，请勿离开报警位置");
		}else{
			System.out.println(i+"sssssssssssssssssss");
			tv.setText("警察正在赶来处理");
		}
		setUpActionBar();
		mMapView = (MapView) findViewById(R.id.map);
		mBaiduMap = mMapView.getMap();
		initLocation();
		// initData();
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
		mSuggestionSearch = SuggestionSearch.newInstance();
		mSuggestionSearch.setOnGetSuggestionResultListener(this);
		System.out.println(lat + " " + lng);

		tv = (TextView) findViewById(R.id.test);
		mTimer = new Timer();
		mTimer.schedule(mTimerTask, 5000, 60000);
	}

	private void setUpActionBar() {
		initActionBar();
		setActionBarVisible(true);
		setActionBarMidTitle("求助");
		setActionBarLeftLayoutVisiable(true);
	}

	private void initData() {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = new HashMap<String, Object>();

		final XRequestManager manager = new XRequestManager(this);
		manager.post(Constants.USER_GETNEARBYPOLICES, map,
				new XRequestListener() {
					@Override
					public void onRequestStart() {
						manager.setProgressMessage("加载中...");
					}

					@Override
					public void onRequestSuccess(XResponse res) {
						int code = res.getStatusCode();
						if (code == 1) {
							ArrayList<Police> list = (ArrayList<Police>) res
									.getEntityList("aroundPolices",
											new TypeToken<List<Police>>() {
											}.getType());
							mBaiduMap.clear();
							if (null != list) {
								System.out.println("aroundPolices:"
										+ list.size());
								for (int i = 0; i < list.size(); i++) {
									Police p = list.get(i);
									Coord c = p.getCoord();
									LatLng point = new LatLng(c.getY(), c
											.getX());
									if (p.getCurrTask() != null) {
										MarkerOptions mo = new MarkerOptions()
												.position(point)
												.icon(bdPoliceTask).zIndex(9);
										mBaiduMap.addOverlay(mo);
									} else {
										MarkerOptions mo = new MarkerOptions()
												.position(point)
												.icon(bdPoliceNoTask).zIndex(9);
										mBaiduMap.addOverlay(mo);
									}
								}
							}
							if (orgList != null) {
								for (int i = 0; i < orgList.size(); i++) {
									mBaiduMap.addOverlay(orgList.get(i));
								}
							}
						} else {

						}

					}

					@Override
					public void onRequestFailure(HttpException error, String msg) {
						// onLoad();
					}
				});
	}

	/**
	 * 显示用户的定位地点
	 * 
	 * @param baiduMap
	 */
	public void displayUserLocation(BaiduMap baiduMap, double latitude,
			double longitude) {

		LogUtils.i("定位地点显示 :  纬度  == " + latitude + " 经度 == " + longitude);
		baiduMap.setMyLocationEnabled(true);
		// 构造定位数据
		MyLocationData locData = new MyLocationData.Builder()
				.accuracy((float) 100).direction(100).latitude(latitude)
				.longitude(longitude).build();

		// 设置定位数据
		baiduMap.setMyLocationData(locData);
		MyLocationConfiguration configuration = new MyLocationConfiguration(
				LocationMode.FOLLOWING, true, null);
		baiduMap.setMyLocationConfigeration(configuration);
		baiduMap.setMyLocationEnabled(false);
	}

	@Override
	public void onDestroy() {
		// activity 销毁时同时销毁地图控件
		if (mMapView != null) {
			mMapView.onDestroy();
		}
		mTimer.cancel();
		super.onDestroy();

	}

	@Override
	public void onPause() {
		// activity 暂停时同时暂停地图控件
		if (mMapView != null) {
			mMapView.onPause();
		}
		unregisterReceiver(mReceiver);
		super.onPause();
	}

	@Override
	public void onResume() {
		if (mMapView != null) {
			mMapView.onResume();
		}
		mReceiver = new LocationReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("location");
		registerReceiver(mReceiver, filter);
		super.onResume();
	}

	@Override
	public void onGetSuggestionResult(SuggestionResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetPoiResult(PoiResult arg0) {
		// TODO Auto-generated method stub
		// arg0.error.
		// System.out.println(arg0.getAllPoi().size());
		// if(arg0.getAllPoi().size() > 0) {
		// for(int i = 0;i <arg0.getAllPoi().size();i ++) {
		// PoiInfo p = arg0.getAllPoi().get(i);
		// System.out.println(p.address);
		// System.out.println(p.location.latitude + "," + p.location.longitude);
		// }
		// }
		if (arg0 == null || arg0.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(this, "未找到结果", Toast.LENGTH_LONG).show();
			return;
		}
		if (arg0.error == SearchResult.ERRORNO.NO_ERROR) {
			System.out.println("共" + arg0.getTotalPageNum() + " pages");
			if (orgList == null) {
				orgList = new ArrayList<MarkerOptions>();
			}
			for (int i = 0; i < arg0.getAllPoi().size(); i++) {
				PoiInfo p = arg0.getAllPoi().get(i);
				System.out.println(p.name);
				System.out.println(p.address);
				System.out.println(p.location.latitude + ","
						+ p.location.longitude);
				MarkerOptions mo = new MarkerOptions().position(p.location)
						.icon(bdOrg).zIndex(9);
				mBaiduMap.addOverlay(mo);
				orgList.add(mo);
				// mBaiduMap.

			}
		}
	}

}
