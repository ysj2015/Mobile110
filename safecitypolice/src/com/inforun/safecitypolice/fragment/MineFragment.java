package com.inforun.safecitypolice.fragment;

import java.util.HashMap;

import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.activity.SendAlarmActivity;
import com.inforun.safecitypolice.activity.SettingActivity;
import com.inforun.safecitypolice.entity.Police;
import com.inforun.safecitypolice.entity.Task;
import com.inforun.safecitypolice.finals.Constants;
import com.inforun.safecitypolice.request.XRequestListener;
import com.inforun.safecitypolice.request.XRequestManager;
import com.inforun.safecitypolice.request.XResponse;
import com.lidroid.xutils.exception.HttpException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MineFragment extends Fragment implements OnClickListener {
	View view;
	Police police;
	TextView tv_name,tv_phone;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_mine, null);
		initView(view);
		getMyInfo();
		return view;
	}
	
	private void initView(View view) {
		tv_name = (TextView)view.findViewById(R.id.police_name);
		tv_phone = (TextView)view.findViewById(R.id.phone);
		view.findViewById(R.id.setting).setOnClickListener(this);
		
		view.findViewById(R.id.leftImageButton).setVisibility(View.GONE);
		TextView title = (TextView)view.findViewById(R.id.mid_title);
		title.setText("个人中心");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i;
		switch(v.getId()) {		
			case R.id.setting:
				i = new Intent(getActivity(),SettingActivity.class);
				startActivity(i);
				break;

		}
	}
	private void getMyInfo() {
		HashMap<String, Object> map = new HashMap<String, Object>();

		final XRequestManager manager = new XRequestManager(getActivity());
		manager.post(Constants.POLICE_GETMYINFO, map, new XRequestListener() {
			@Override
			public void onRequestStart() {
				manager.setProgressMessage("加载中...");
			}

			@Override
			public void onRequestSuccess(XResponse res) {
				int code = res.getStatusCode();
				if (code == 1) {
					police = (Police) res.getEntity("police", Police.class);
					if (null != police) {
						tv_name.setText(police.getDetails().getName());
						tv_phone.setText(police.getDetails().getTel());
					}
				}
			}

			@Override
			public void onRequestFailure(HttpException error, String msg) {
				
			}
		});
	}
}
