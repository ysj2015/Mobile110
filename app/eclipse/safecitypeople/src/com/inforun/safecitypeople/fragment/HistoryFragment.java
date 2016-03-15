package com.inforun.safecitypeople.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.google.gson.reflect.TypeToken;
import com.inforun.pulltorefresh.library.PullToRefreshListView;
import com.inforun.pulltorefresh.library.PullToRefreshListView.PullToRefreshListViewListener;
import com.inforun.safecitypeople.R;
import com.inforun.safecitypeople.activity.AlarmDetailActivity;
import com.inforun.safecitypeople.adapter.HistoryAlarmAdapter;
import com.inforun.safecitypeople.entity.AlarmInfo;
import com.inforun.safecitypeople.Constants;
import com.inforun.safecitypeople.request.XRequestListener;
import com.inforun.safecitypeople.request.XRequestManager;
import com.inforun.safecitypeople.request.XResponse;
import com.lidroid.xutils.exception.HttpException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class HistoryFragment extends Fragment implements PullToRefreshListViewListener, OnItemClickListener {
	View view;
	PullToRefreshListView lv;
	private TextView tv_noDate;
	private ArrayList<AlarmInfo> listTask = new ArrayList<AlarmInfo>();
	private HistoryAlarmAdapter adapter = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_history, null);
		initView(view);
		return view;
	}
	
	private void initView(View view) {
		lv = (PullToRefreshListView) view.findViewById(R.id.listView);
		tv_noDate = (TextView) view.findViewById(R.id.tv_noDate);
		lv.setPullLoadEnable(false);
		lv.setOnItemClickListener(this);
		adapter = new HistoryAlarmAdapter(getActivity());
		adapter.setList(listTask);
		lv.setAdapter(adapter);
		lv.setPullToRefreshListViewListener(this);
		view.findViewById(R.id.leftImageButton).setVisibility(View.GONE);
		TextView title = (TextView)view.findViewById(R.id.mid_title);
		title.setText("报警记录");
		getHisTasks(true,false);
	}
	private void getHisTasks(boolean firstLoad,boolean showNewst) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		final XRequestManager manager = new XRequestManager(getActivity());
		if (!firstLoad) {
			manager.setProgressDialog(false);
		}
		manager.post(Constants.BASE_URL + "/user_getHistoryAlarms", map,
				new XRequestListener() {
					@Override
					public void onRequestStart() {
						manager.setProgressMessage("加载中...");
					}

					@Override
					public void onRequestSuccess(XResponse res) {
						int code = res.getStatusCode();
						if (code == 1) {
							ArrayList<AlarmInfo> list = (ArrayList<AlarmInfo>) res
									.getEntityList("hisAlarms",
											new TypeToken<List<AlarmInfo>>() {
											}.getType());
							if (null != list) {
								listTask.clear();
								listTask = list;
								adapter.setList(listTask);
								adapter.notifyDataSetChanged();
								for(int i = 0;i < listTask.size();i++) {
									System.out.println(listTask.get(i).getId());
//									System.out.println(listTask.get(i).getAlarmInfo().getText());
								}
							} else {
								noDate(true);
							}
						}else{
							noDate(true);
						}
						onLoad();
					}

					@Override
					public void onRequestFailure(HttpException error, String msg) {
						onLoad();
					}
				});
	}
	/**
	 * 加载时
	 */
	private void onLoad() {
		lv.stopRefresh();
		lv.stopLoadMore();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.getDefault());
		lv.setRefreshTime(dateFormat.format(new Date(System.currentTimeMillis())));
	}
	/**
	 * 判断是否有数据显示textview
	 * 
	 * @param isNoDate
	 */
	private void noDate(boolean isNoDate) {
		if (isNoDate) {
			
			lv.setVisibility(View.GONE);
			tv_noDate.setVisibility(View.VISIBLE);
		} else {
			lv.setVisibility(View.VISIBLE);
			tv_noDate.setVisibility(View.GONE);
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		System.out.println(arg2);
		Intent intent = new Intent(getActivity(),AlarmDetailActivity.class);
		intent.putExtra("alarm", listTask.get(arg2-1));
		getActivity().startActivity(intent);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		getHisTasks(false,false);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}
}
