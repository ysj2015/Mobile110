package com.inforun.safecitypolice.widget;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.inforun.safecitypolice.R;

public class NearbyPoliceInfoWindow extends InfoWindow implements OnClickListener{

	private View mView;
	public NearbyPoliceInfoWindow(View arg0, LatLng arg1, int arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}
	public NearbyPoliceInfoWindow(View view, Marker marker, int distance){
		super(view,	marker.getPosition(), distance);
		mView = view;
		TextView tv = (TextView)mView.findViewById(R.id.tv_currentTask);
		tv.setText(marker.getExtraInfo().getString("policeName"));
		mView.findViewById(R.id.iv_sms).setOnClickListener(this);
		mView.findViewById(R.id.iv_phone).setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.iv_sms:
			System.out.println("sms");
			break;
		case R.id.iv_phone:
			System.out.println("phone");
			break;
		}
	}
}
