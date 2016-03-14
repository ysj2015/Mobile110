package com.inforun.safecitypolice.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.inforun.pulltorefresh.library.PullToRefreshListView;
import com.inforun.pulltorefresh.library.PullToRefreshListView.PullToRefreshListViewListener;
import com.inforun.safecitypolice.MyApplication;
import com.inforun.safecitypolice.ParentFragment;
import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.activity.HistoryTaskDetailActivity;
import com.inforun.safecitypolice.adapter.HistoryTaskAdapter;
import com.inforun.safecitypolice.entity.Police;
import com.inforun.safecitypolice.entity.Task;
import com.inforun.safecitypolice.finals.Constants;
import com.inforun.safecitypolice.request.XRequestListener;
import com.inforun.safecitypolice.request.XRequestManager;
import com.inforun.safecitypolice.request.XResponse;
import com.inforun.safecitypolice.utils.DateUtil;
import com.lidroid.xutils.exception.HttpException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 历史任务
 * 
 * @author xiongchaoxi
 */
public class HistoryTaskFragment extends ParentFragment implements
		PullToRefreshListViewListener,OnItemClickListener {
	View view;
	/**
	 * 列表控件
	 */
	private PullToRefreshListView listView;

	/**
	 * 没有数据
	 */
	private TextView tv_noDate;

	/**
	 * 任务列表
	 */
	private ArrayList<Task> listTask = new ArrayList<Task>();

	/**
	 * 历史任务适配器
	 */
	private HistoryTaskAdapter adapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_historytask, null);
		initView(view);
		return view;
	}

	/**
	 * 初始化控件
	 * 
	 * @param view
	 */
	private void initView(View view) {
		listView = (PullToRefreshListView) view.findViewById(R.id.listView);
		tv_noDate = (TextView) view.findViewById(R.id.tv_noDate);
		listView.setPullLoadEnable(false);
		listView.setOnItemClickListener(this);
		adapter = new HistoryTaskAdapter(getActivity());
		adapter.setList(listTask);
		listView.setAdapter(adapter);
		listView.setPullToRefreshListViewListener(this);
		view.findViewById(R.id.leftImageButton).setVisibility(View.GONE);
		TextView title = (TextView)view.findViewById(R.id.mid_title);
		title.setText("历史任务");
		getHisTasks(true,false);
	}

	/**
	 * 加载时
	 */
	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(DateUtil.getSystemDate("yyyy-MM-dd HH:mm"));
	}

	/**
	 * 判断是否有数据显示textview
	 * 
	 * @param isNoDate
	 */
	private void noDate(boolean isNoDate) {
		if (isNoDate) {
			
			listView.setVisibility(View.GONE);
			tv_noDate.setVisibility(View.VISIBLE);
		} else {
			listView.setVisibility(View.VISIBLE);
			tv_noDate.setVisibility(View.GONE);
		}
	}

	/**
     *
     */
	private void getHisTasks(boolean firstLoad,boolean showNewst) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		final XRequestManager manager = new XRequestManager(getActivity());
		if (!firstLoad) {
			manager.setProgressDialog(false);
		}
		manager.post(Constants.POLICE_GETHISTORYTASKS, map,
				new XRequestListener() {
					@Override
					public void onRequestStart() {
						manager.setProgressMessage("加载中...");
					}

					@Override
					public void onRequestSuccess(XResponse res) {
						int code = res.getStatusCode();
						if (code == 1) {
							ArrayList<Task> list = (ArrayList<Task>) res
									.getEntityList("hisTasks",
											new TypeToken<List<Task>>() {
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
	@Override
	public void onRefresh() {
		getHisTasks(false,false);
	}

	@Override
	public void onLoadMore() {
		// if(currentCount<count){
		// getTasks(false, false);
		// }else{
		// onLoad();
		// listView.setPullLoadEnable(false);
		// }
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		System.out.println(arg2);
		Intent intent = new Intent(getActivity(),HistoryTaskDetailActivity.class);
		intent.putExtra("task", listTask.get(arg2-1));
		getActivity().startActivity(intent);
		
	}
}
