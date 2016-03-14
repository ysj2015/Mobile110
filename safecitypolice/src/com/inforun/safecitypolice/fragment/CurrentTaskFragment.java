package com.inforun.safecitypolice.fragment;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.inforun.safecitypolice.ParentFragment;
import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.activity.AlarmInfoActivity;
import com.inforun.safecitypolice.activity.LoginActivity;
import com.inforun.safecitypolice.activity.MainActivity;
import com.inforun.safecitypolice.activity.PoliceSupportActivity;
import com.inforun.safecitypolice.activity.RouteActivity;
import com.inforun.safecitypolice.activity.SubmitTaskActivity;
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

/**
 * 当前任务
 * 
 * @author xiongchaoxi
 */
public class CurrentTaskFragment extends ParentFragment implements
		OnClickListener {

	/**
	 * 当前任务
	 */
	private Task currentTask = null;
	
	private ImageView navi,navi_disabled;
	private ImageView no_task,curr_task,curr_task_accepted;
	private TextView alarm_text;
	
	private MapView mapView;
	BitmapDescriptor bdAlarm = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_mark_alarm);

	private double alarmLat,alarmLng;
	private String taskContent;
	private String mCoord;
	View view;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_currenttask, null);
		initView(view);
		return view;
	}

	/**
	 * 初始化组件
	 * 
	 * @param view
	 */
	private void initView(View view) {
		
		mapView = (MapView) view.findViewById(R.id.map);
		
		curr_task = (ImageView)view.findViewById(R.id.curr_task);
		curr_task_accepted = (ImageView)view.findViewById(R.id.curr_task_accepted);
		no_task = (ImageView)view.findViewById(R.id.no_task);
		
		
		navi = (ImageView)view.findViewById(R.id.navi);
		navi_disabled = (ImageView)view.findViewById(R.id.navi_disabled);
		
		alarm_text = (TextView)view.findViewById(R.id.alarm_text);
		
		curr_task.setOnClickListener(this);	
		curr_task_accepted.setOnClickListener(this);	
		navi.setOnClickListener(this);
		
		view.findViewById(R.id.leftImageButton).setVisibility(View.GONE);
		TextView title = (TextView)view.findViewById(R.id.mid_title);
		title.setText(R.string.app_name);
		getTask();
	}

	@Override
	public void onClick(View v) {

		Intent in = new Intent();
		switch (v.getId()) {
		
		case R.id.curr_task:
			in = new Intent(getActivity(),AlarmInfoActivity.class);
			
			startActivityForResult(in,200);
			break;
		case R.id.curr_task_accepted:
			in = new Intent(getActivity(),AlarmInfoActivity.class);
			
			startActivityForResult(in,200);
			break;
		case R.id.navi:
			in.putExtra("lat", 31.186236);
			in.putExtra("lng", 121.43782);
			in.setClass(getActivity(), RouteActivity.class);
			getActivity().startActivity(in);
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//getCurrentTask();
		System.out.println("resultCode:"+resultCode);
		getTask();
		super.onActivityResult(requestCode, resultCode, data);
	}
	private void getTask() {
		AsyncHttpManager.getAsyncHttpClient().post(Constants.POLICE_GETCURRTASK, new JsonHttpResponseHandler(){
			public void onSuccess(JSONObject arg0) {
				System.out.println(arg0.toString());
				try {
					int code = arg0.getInt("code");
					if(code == 1) {
						JSONObject currTask = arg0.getJSONObject("currTask");
						int accept = currTask.getInt("acceptStatus");
						JSONObject alarm = currTask.getJSONObject("alarmInfo");
						//报警地点
						mCoord = alarm.getString("coordStr");
						System.out.println(mCoord);
						String[] loc = mCoord.split(" ");
						alarmLat = Double.parseDouble(loc[1]);
						alarmLng = Double.parseDouble(loc[0]);
						LatLng ll = new LatLng(alarmLat,alarmLng);
						MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
		                mapView.getMap().animateMapStatus(u);
		                MarkerOptions ooA = new MarkerOptions().position(ll).icon(bdAlarm)
		        				.zIndex(9).draggable(true);
		                mapView.getMap().addOverlay(ooA);
		                //报警信息、图片
		                taskContent = currTask.getString("taskContent");
		                alarm_text.setText(taskContent);
		                if(accept == 0) {
		                	curr_task.setVisibility(View.VISIBLE);
		                	curr_task_accepted.setVisibility(View.GONE);
		                }else if(accept == 1) {
		                	curr_task.setVisibility(View.GONE);
		                	curr_task_accepted.setVisibility(View.VISIBLE);
		                }
						no_task.setVisibility(View.GONE);
						
						navi_disabled.setVisibility(View.GONE);
						navi.setVisibility(View.VISIBLE);
					}else if(code == 0) {
						curr_task.setVisibility(View.GONE);
						curr_task_accepted.setVisibility(View.GONE);
						no_task.setVisibility(View.VISIBLE);
						
						navi.setVisibility(View.GONE);
						navi_disabled.setVisibility(View.VISIBLE);
						alarm_text.setText(arg0.getString("message"));
						
						LatLng ll = new LatLng(40.784538,114.903622);
						MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
		                mapView.getMap().animateMapStatus(u);
					}
				} catch(Exception e) {
					curr_task.setVisibility(View.GONE);
					view.findViewById(R.id.no_task).setVisibility(View.VISIBLE);
					navi.setVisibility(View.GONE);
					Toast.makeText(getActivity(), "数据解析失败", Toast.LENGTH_LONG).show();
				}
			}
			public void onFailure(Throwable arg0) { // 失败，调用
				Toast.makeText(getActivity(), "请求失败,请检查网络", Toast.LENGTH_LONG).show();
				curr_task.setEnabled(false);
				navi.setEnabled(false);
	        }
	        public void onFinish() { // 完成后调用，失败，成功，都要掉
	        	
	        }
		}
				
				
		);
	}
	
	
	
}
