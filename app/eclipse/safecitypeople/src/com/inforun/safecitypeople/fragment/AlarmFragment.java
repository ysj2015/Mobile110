package com.inforun.safecitypeople.fragment;

import com.inforun.safecitypeople.R;
import com.inforun.safecitypeople.activity.AlarmInfoActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class AlarmFragment extends Fragment implements OnClickListener {

	View view;
	private TextView tv_type;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_alarm, null);
		initView(view);
		return view;
	}
	
	private void initView(View view) {
		view.findViewById(R.id.leftLinear).setVisibility(View.GONE);
		((TextView)view.findViewById(R.id.mid_title)).setText("报警");
		tv_type = (TextView)(view.findViewById(R.id.tv_type));
		view.findViewById(R.id.traffic_accident).setOnClickListener(this);
		view.findViewById(R.id.qj).setOnClickListener(this);
		view.findViewById(R.id.other).setOnClickListener(this);
		view.findViewById(R.id.bj).setOnClickListener(this);
		view.findViewById(R.id.dj).setOnClickListener(this);
		view.findViewById(R.id.thief).setOnClickListener(this);
		view.findViewById(R.id.alarm_button).setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent in;
		switch(arg0.getId()){
		case R.id.traffic_accident:
			tv_type.setText("车祸");
			break;
		case R.id.qj:
			tv_type.setText("抢劫");
			break;
		case R.id.other:
			tv_type.setText("其他");
			break;
		case R.id.bj:
			tv_type.setText("绑架");
			break;
		case R.id.thief:
			tv_type.setText("偷窃");
			break;
		case R.id.dj:
			tv_type.setText("打架");
			break;
		case R.id.alarm_button:
			in = new Intent();
			in.putExtra("type", tv_type.getText().toString());
			in.setClass(getActivity(), AlarmInfoActivity.class);
			startActivity(in);
			
			
		
		}
	}
}
