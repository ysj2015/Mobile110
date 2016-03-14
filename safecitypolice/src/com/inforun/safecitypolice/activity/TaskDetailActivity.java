package com.inforun.safecitypolice.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.inforun.safecitypolice.BaseActivity;
import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.other.MyOrientationListener;


/**
 * 任务详情界面
 *     xiongchaoxi
 */
public class TaskDetailActivity extends BaseActivity implements BDLocationListener ,View.OnClickListener{
    /**
     * 定位的客户端
     */
    private LocationClient mLocationClient;
    /**
     * 地图实例
     */
    private BaiduMap mBaiduMap;
    /**
     * 地图控件
     */
    private MapView mMapView;
    /**
     * 方向传感器的监听器
     */
    private MyOrientationListener myOrientationListener;
    /**
     * 方向传感器X方向的值
     */
    private int mXDirection;
    /**
     * 经纬度
     */
    private double longitude = 0, latitude = 0;
    /**
     * 当前的精度
     */
    private float mCurrentAccracy;
    /**
     * 是否是第一次定位
     */
    boolean isFirstLoc = true;
    /**
     * 当前定位的模式
     */
    private MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;


        /**
         * 最新一次的经纬度
         */
        private double mCurrentLantitude;
        private double mCurrentLongitude;

        /**
         * 地图定位的模式
         */
        private String[] mStyles = new String[] { "地图模式(正常)", "地图模式(跟随)",
                "地图模式(罗盘)" };
        private int mCurrentStyle = 0;


       private TextView tv_1,tv_2,tv_3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        setUpActionBar();
        initView();

    }

    /**
     * 初始化组件
     */
    private void initView() {
        mMapView = (MapView) findViewById(R.id.map);
        tv_1= (TextView) findViewById(R.id.tv_1);
        tv_2= (TextView) findViewById(R.id.tv_2);
        tv_3= (TextView) findViewById(R.id.tv_3);
        tv_1.setOnClickListener(this);
        tv_2.setOnClickListener(this);
        tv_3.setOnClickListener(this);
        initBaiDuMap();
    }
	/**
	 * 隐藏actionBar上的控件
	 */
	private void setUpActionBar() {
		setActionBarVisible(true);
		setActionBarMidTitle("地图");
	}
    /**
     * 初始化百度地图实例
     */
    private void initBaiDuMap() {
        mBaiduMap = mMapView.getMap();


//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
        mBaiduMap
                .setMyLocationConfigeration(new MyLocationConfiguration(
                        mCurrentMode,
                        true, null));
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void onMapClick(LatLng arg0) {
                // TODO Auto-generated method stub
                double longitude2 = arg0.longitude;
                double latitude2 = arg0.latitude;

            }
        });
    }

    /**
     * 百度定位
     */
//    private void initLocation() {
//        mLocationClient = new LocationClient(this); // 声明LocationClient类
//        mLocationClient.registerLocationListener(this);
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
//        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02 bd09ll
//        int span = 1000;
//        option.setScanSpan(span);// 设置发起定位请求的间隔时间为5000ms
//        option.setOpenGps(true); // 打开gps
//        option.setIsNeedAddress(true);
//        mLocationClient.setLocOption(option);
//
//        if (mLocationClient != null) {
//            mLocationClient.start();
//        } else {
//            Log.d("LocSDK4", "locClient is null or not started");
//        }
//
//    }


    @Override
    public void onReceiveLocation(BDLocation location) {
        // TODO Auto-generated method stub
        if (location == null || mMapView == null)
            return;
        mCurrentAccracy = location.getRadius();
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(mXDirection).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
// 设置自定义图标
        MyLocationConfiguration config = new MyLocationConfiguration(
                mCurrentMode, true, null
        );
        mBaiduMap.setMyLocationConfigeration(config);

        if (isFirstLoc) {
            isFirstLoc = false;
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());


            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(u);
        }

    }

    /**
     * 初始化方向传感器
     */
    private void initOritationListener() {
        myOrientationListener = new MyOrientationListener(
                getApplicationContext());
        myOrientationListener
                .setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
                    @Override
                    public void onOrientationChanged(float x) {
                        mXDirection = (int) x;

                        // 构造定位数据
                        MyLocationData locData = new MyLocationData.Builder()
                                .accuracy(mCurrentAccracy)
                                        // 此处设置开发者获取到的方向信息，顺时针0-360
                                .direction(mXDirection)
                                .latitude(latitude)
                                .longitude(longitude).build();
                        // 设置定位数据
                        mBaiduMap.setMyLocationData(locData);
                        // 设置自定义图标
//                        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
//                                .fromResource(R.mipmap.navi_map_gps_locked);
//
                        MyLocationConfiguration config = new MyLocationConfiguration(
                                mCurrentMode, true, null);
                        mBaiduMap.setMyLocationConfigeration(config);
                    }
                });
    }

    @Override
    protected void onStart() {
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        super.onStart();
    }

    @Override
    protected void onStop() {
        // 关闭方向传感器
        myOrientationListener.stop();
        super.onStop();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub

        Log.i("sssssssssssssss", "onResume()");
        if (mMapView != null) {
            mMapView.onResume();
        }
        isFirstLoc = true;
        initLocation();
        // 初始化传感器
        initOritationListener();
        // 开启方向传感器
        myOrientationListener.start();
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i("sssssssssssssss", "onPause()");
        super.onPause();
        // activity 暂停时同时暂停地图控件
        if (mMapView != null) {
            mMapView.onPause();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i("sssssssssssssss", "onDestroy()");
        // activity 销毁时同时销毁地图控件
        mBaiduMap.setMyLocationEnabled(false);
        if (mMapView != null) {
            mMapView.onDestroy();
            mMapView = null;
        }

        if (null != mLocationClient) {
            mLocationClient.stop();
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_1:
                // 开启交通图
                if (mBaiduMap.isTrafficEnabled()) {
                    tv_1.setText("开启实时交通");
                    mBaiduMap.setTrafficEnabled(false);
                } else {
                    tv_1.setText("关闭实时交通");
                    mBaiduMap.setTrafficEnabled(true);
                }
                break;
            case R.id.tv_2:
                String text=tv_2.getText().toString().trim();

                if ("普通地图".equals(text)){
                    // 普通地图
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    tv_2.setText("卫星地图");
                }else{
                    //卫星地图
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                    tv_2.setText("普通地图");
                }
                break;
            case R.id.tv_3:
                mCurrentStyle = (++mCurrentStyle) % mStyles.length;
                tv_3.setText(mStyles[mCurrentStyle]);
                // 设置自定义图标
                switch (mCurrentStyle) {
                    case 0:
                        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                        break;
                    case 1:
                        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                        break;
                    case 2:
                        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                        break;
                }
//                BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
//                        .fromResource(R.mipmap.navi_map_gps_locked);
                MyLocationConfiguration config = new MyLocationConfiguration(
                        mCurrentMode, true, null);
                mBaiduMap.setMyLocationConfigeration(config);

                break;
        }
    }
}
