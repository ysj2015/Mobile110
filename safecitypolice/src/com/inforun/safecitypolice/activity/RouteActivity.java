package com.inforun.safecitypolice.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.adapter.BaiduNaviManager.NaviInitListener;
import com.baidu.navisdk.adapter.BaiduNaviManager.RoutePlanListener;
import com.baidu.navisdk.adapter.BaiduNaviManager.RoutePlanPreference;
import com.inforun.safecitypolice.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.inforun.safecitypolice.BaseActivity;
import com.inforun.safecitypolice.overlay.DrivingRouteOverlay;
import com.inforun.safecitypolice.overlay.MyDrivingRouteOverlay;
import com.inforun.safecitypolice.overlay.MyWalkingRouteOverlay;
import com.inforun.safecitypolice.overlay.OverlayManager;
import com.inforun.safecitypolice.overlay.WalkingRouteOverlay;
import com.lidroid.xutils.util.LogUtils;

public class RouteActivity extends BaseActivity
		implements OnGetRoutePlanResultListener, OnClickListener {
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private TextView route_tv;
	LocationReceiver mReceiver;
	RoutePlanSearch mSearch = null;
	RouteLine route = null;
	OverlayManager routeOverlay = null;
	boolean isFirstLoc = true;
	double alarmLat,alarmLng;
	DrivingRouteLine drl;
	int step = 0;
	
	LatLng startPoint,endPoint;
	
	//百度导航
	String mSDCardPath = null;
	String authinfo;
	String APP_FOLDER_NAME = "BNSDKSimpleDemo";
	public static final String ROUTE_PLAN_NODE = "routePlanNode";
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route);
		
		initView();
		setUpActionBar();
		initData();
		initLocation();
		initListener();
		mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
        
        
	}
	private void initView() {
		mMapView = (MapView)findViewById(R.id.map);
		mBaiduMap = mMapView.getMap();
		route_tv = (TextView)findViewById(R.id.route_tv);
		
	}
	private void setUpActionBar() {
		setActionBarVisible(true);
		setActionBarMidTitle("导航");
		setActionBarLeftLayoutVisiable(true);
	}
	private void initData() {
		alarmLat = getIntent().getDoubleExtra("lat", -1.0);
		alarmLng = getIntent().getDoubleExtra("lng", -1.0);
		
	}
	private void initListener() {
		findViewById(R.id.pre).setOnClickListener(this);
		findViewById(R.id.next).setOnClickListener(this);
//		findViewById(R.id.finish).setOnClickListener(this);
//		findViewById(R.id.navi).setOnClickListener(this);
	}
	@Override
	public void onDestroy() {
		// activity 销毁时同时销毁地图控件
		if (mMapView != null) {
			mMapView.onDestroy();
		}
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
		if(isFirstLoc) {
			startPoint = new LatLng(latitude, longitude);
			Log.v("location",latitude + " " + longitude);
			endPoint = new LatLng(alarmLat, alarmLng);
			Log.v("location",alarmLat + " " + alarmLng);
			PlanNode stNode = PlanNode.withLocation(startPoint);
			PlanNode enNode = PlanNode.withLocation(endPoint);
			mSearch.drivingSearch(new DrivingRoutePlanOption()
                    .from(stNode)
                    .to(enNode));
			isFirstLoc = false;
		}
	}
	
	class LocationReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			double lat = arg1.getDoubleExtra("lat", -1.0);
			double lon = arg1.getDoubleExtra("lon", -1.0);
			displayUserLocation(mBaiduMap, lat, lon);

		}
		
	}

	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {
		// TODO Auto-generated method stub
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			findViewById(R.id.pre).setVisibility(View.VISIBLE);
			findViewById(R.id.next).setVisibility(View.VISIBLE);
            Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			mBaiduMap.clear();
            route = result.getRouteLines().get(0);
            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
            routeOverlay = overlay;
            mBaiduMap.setOnMarkerClickListener(overlay);
            drl = result.getRouteLines().get(0);
            overlay.setData(drl);
            for(int i = 0;i < drl.getAllStep().size(); i ++){
            	System.out.println(drl.getAllStep().get(i).getInstructions());
            }
            findViewById(R.id.route_layout).setVisibility(View.VISIBLE);
            route_tv.setText(drl.getAllStep().get(0).getInstructions());
            overlay.addToMap();
            overlay.zoomToSpan();
            
        }
	}
	@Override
	public void onGetTransitRouteResult(TransitRouteResult arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult result) {
		// TODO Auto-generated method stub
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
        	mBaiduMap.clear();
        	route = result.getRouteLines().get(0);
        	WalkingRouteLine drl = result.getRouteLines().get(0);
        	for(int i = 0;i < drl.getAllStep().size(); i ++){
            	System.out.println(drl.getAllStep().get(i).getInstructions());
            }
        	WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            routeOverlay = overlay;
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
            findViewById(R.id.route_layout).setVisibility(View.VISIBLE);
        }
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.pre:
			if(step > 0) {
				step--;
				route_tv.setText(drl.getAllStep().get(step).getInstructions());
				LatLng p = drl.getAllStep().get(step).getEntrance().getLocation();
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(p);
				mBaiduMap.animateMapStatus(u);
			}
			break;
		case R.id.next:
			if(step < drl.getAllStep().size()-1) {
				step++;
				route_tv.setText(drl.getAllStep().get(step).getInstructions());
				LatLng p = drl.getAllStep().get(step).getEntrance().getLocation();
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(p);
				mBaiduMap.animateMapStatus(u);
			}
			break;
//		case R.id.finish:
//			Intent i = new Intent(this,SubmitTaskActivity.class);
//			startActivity(i);
//			break;

		}
	}

}
