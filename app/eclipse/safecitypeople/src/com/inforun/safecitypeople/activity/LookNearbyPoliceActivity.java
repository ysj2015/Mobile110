package com.inforun.safecitypeople.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapTouchListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.reflect.TypeToken;
import com.inforun.safecitypeople.BaseActivity;
import com.inforun.safecitypeople.Constants;
import com.inforun.safecitypeople.R;
import com.inforun.safecitypeople.entity.Coord;
import com.inforun.safecitypeople.entity.Police;
import com.inforun.safecitypeople.request.XRequestListener;
import com.inforun.safecitypeople.request.XRequestManager;
import com.inforun.safecitypeople.request.XResponse;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.core.BitmapSize;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.util.LogUtils;

/**
 * 查看周围警力
 * 
 * @author xiongchaoxi
 * 
 */
public class LookNearbyPoliceActivity extends BaseActivity{
	LocationClient mLocClient;
	private LocationMode mCurrentMode;
	// 百度地图
	private MapView mapView;

	private LatLng curLoc;// 经纬度对象

	private BaiduMap mBaiduMap;
	private MapView mMapView;
	private Marker markerA;
	private Button button;// 地图上“点”按钮

	private ArrayList<Button> btns = new ArrayList<Button>();
	private ArrayList<Marker> markers = new ArrayList<Marker>();
	private View view, view_ditu;// 城市dialog view
	private Bitmap bitmap;

	private boolean isFirstLoc = true; 
	/**
	 * 附近警察列表
	 */
	private ArrayList<Police> list = new ArrayList<Police>();
	private ArrayList<LatLng> pList = new ArrayList<LatLng>();

	private OnInfoWindowClickListener listener = null;

	private LatLng start_LatLng;

	private LatLng end_LatLng;
	LocationReceiver mReceiver;
	/**
	 * 判断是否有网络
	 * 
	 * @param context
	 * @return
	 */
	public boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_looknearbypolice);
		initView();
		initListener();
		initLocation();
//		String r = "move:"+mBaiduMap.getUiSettings().isScrollGesturesEnabled();
//		String r1 = "zoom:"+mBaiduMap.getUiSettings().isZoomGesturesEnabled();
//		Toast.makeText(this, r + r1, Toast.LENGTH_LONG).show();
//		System.out.println("move:"+mBaiduMap.getUiSettings().isScrollGesturesEnabled());
//		System.out.println("zoom:"+mBaiduMap.getUiSettings().isZoomGesturesEnabled());
	}


	/**
	 * 初始化
	 */
	private void initView() {
		initMap();
		//getNearbyPolice();
		//displayUserLocation(mBaiduMap, 31.191802, 121.591021);		
	}
	
	private void initListener() {
		findViewById(R.id.mylocation).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
//				if(!mLocClient.isStarted()) {
//					mLocClient.start();
//				}
			}
		});
	}

	/**
	 * 隐藏actionBar上的控件
	 */
	private void setUpActionBar() {
//		setActionBarVisible(true);
//		setRightButtonVisible(false);
//		setRightShouCangFenXiangVisibility(false);
//		setRightImageViewVisible(true);
//		setActionBarMidTitle(getString(R.string.look_nearbypolice));
//		setRightImageViewResource(R.drawable.refresh_view);
	}


	@Override
	protected void clickRightImageView() {
		// TODO Auto-generated method stub
//		getNearbyPolice();
//		super.clickRightImageView();
	}
	private void initMap() {

		mMapView = (MapView) findViewById(R.id.map);
		mBaiduMap = mMapView.getMap();


		mBaiduMap.setOnMapTouchListener(new OnMapTouchListener(){

			@Override
			public void onTouch(MotionEvent arg0) {
				// TODO Auto-generated method stub
				//mLocClient.stop();
			}
			
		});

		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {
				
				System.out.println("onMarkerClick");
//				LatLng p = marker.getPosition();
//				View mInfoView = View.inflate(LookNearbyPoliceActivity.this,
//						R.layout.nearby_police_info, null);
//				mInfoWindow = new NearbyPoliceInfoWindow(mInfoView,
//						marker, -67);
//				mBaiduMap.showInfoWindow(mInfoWindow);

				//System.out.println(marker.getExtraInfo().getString("policeName"));
				return true;
			}
		});

	}
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
				com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.FOLLOWING, true, null);
		baiduMap.setMyLocationConfigeration(configuration);
		baiduMap.setMyLocationEnabled(false);
	}
	/**
	 * 在地图上标点
	 */
	private void initOverlay(LatLng latLng) {
//		BitmapDescriptor bitmap = BitmapDescriptorFactory
//				.fromResource(R.drawable.icon_marka);
//		MarkerOptions ooA = new MarkerOptions().position(latLng).icon(bitmap)
//				.zIndex(9);
//		mBaiduMap.addOverlay(ooA);
//		System.out.println("addOverlay");
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
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
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
	public void startActivity(Intent intent) {
		// TODO Auto-generated method stub
		super.startActivity(intent);
	}

	/**
	 * 获取附近警员
	 */
	private void getNearbyPolice() {

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
							list = (ArrayList<Police>) res.getEntityList(
									"aroundPolices",
									new TypeToken<List<Police>>() {
									}.getType());
							Log.v("nearby", list.size() + "");
							if (null != list && list.size() != 0) {
								for (int i = 0; i < list.size(); i++) {

									Coord loc = list.get(i).getCoord();
									if (null != loc) {

										LatLng lng = new LatLng(loc.getY(), loc.getX());
										initOverlay(lng);
									}
									System.out.println("NO:" + list.get(i).getPoliceNo());
									System.out.println("Name:" + list.get(i).getDetails().getName());
									System.out.println("Location:" + list.get(i).getCoord().getX()
										+ "," + list.get(i).getCoord().getY());
								}
							}
						} else {

						}

					}

					@Override
					public void onRequestFailure(HttpException error, String msg) {

					}
				});
	}
	/**
	 * 没有数据的时候
	 * 
	 * @param b
	 */
	// private void noDate(boolean b) {
	// // TODO Auto-generated method stub
	// if (b) {
	// tv_noDate.setVisibility(View.VISIBLE);
	// ll_parent.setVisibility(View.GONE);
	// } else {
	// tv_noDate.setVisibility(View.GONE);
	// ll_parent.setVisibility(View.VISIBLE);
	// }
	//
	// }

	class LocationReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			double lat = arg1.getDoubleExtra("lat", -1.0);
			double lon = arg1.getDoubleExtra("lon", -1.0);
			displayUserLocation(mBaiduMap, lat, lon);
//			LatLng p1 = new LatLng(lat+0.001,lon+0.001);
//			LatLng p2 = new LatLng(lat-0.001,lon-0.001);
//			initOverlay(p1);
//			initOverlay(p2);
			getNearbyPolice();
		}
		
	}

}
