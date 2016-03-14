package com.inforun.safecitypolice.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.inforun.pulltorefresh.library.PullToRefreshListView;
import com.inforun.pulltorefresh.library.PullToRefreshListView.PullToRefreshListViewListener;
import com.inforun.safecitypolice.MyApplication;
import com.inforun.safecitypolice.ParentFragment;
import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.activity.MainActivity;
import com.inforun.safecitypolice.activity.TaskInfoActivity;
import com.inforun.safecitypolice.adapter.HistoryTaskAdapter;
import com.inforun.safecitypolice.adapter.MissTaskAdapter;
import com.inforun.safecitypolice.entity.Police;
import com.inforun.safecitypolice.entity.Task;
import com.inforun.safecitypolice.finals.Constants;
import com.inforun.safecitypolice.request.XRequestListener;
import com.inforun.safecitypolice.request.XRequestManager;
import com.inforun.safecitypolice.request.XResponse;
import com.inforun.safecitypolice.utils.DateUtil;
import com.inforun.safecitypolice.utils.UHelper;
import com.lidroid.xutils.exception.HttpException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 待接任务
 * 
 * @author xiongchaoxi
 */
public class MissTaskFragment extends ParentFragment implements
		PullToRefreshListViewListener,OnItemClickListener {
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
	 * 待接任务适配器
	 */
	private MissTaskAdapter adapter = null;

	private Dialog dialog = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_misstask, null);
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
		listView.setPullToRefreshListViewListener(this);
		listView.setRefreshTime(DateUtil.getSystemDate("yyyy-MM-dd HH:mm"));
		listView.setOnItemClickListener(this);
		adapter = new MissTaskAdapter(getActivity());
		adapter.setList(listTask);
		listView.setAdapter(adapter);
		adapter.setAcceptListener(new MissTaskAdapter.AcceptListener() {
			@Override
			public void accept(int taskId) {
				acceptTask(taskId);
			}
		});

		getMissTasks(true,false);
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
	 * 获取待接任务
	 */
	private void getMissTasks(boolean firstLoad,boolean showNewst) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();

		final XRequestManager manager = new XRequestManager(getActivity());
		if (!firstLoad) {
			manager.setProgressDialog(false);
		}
		manager.post(Constants.POLICE_NOACCEPTASKLIST, map,
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
									.getEntityList("taskList",
											new TypeToken<List<Task>>() {
											}.getType());
							if (null != list) {
								noDate(false);
								// listTask.clear();
								listTask = list;
								adapter.setList(listTask);
								adapter.notifyDataSetChanged();
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
		getMissTasks(false,false);
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

	/**
	 * 接受任务
	 * 
	 * @param taskId
	 *            任务编号
	 */
	private void acceptTask(final int taskId) {
		if (dialog != null && dialog.isShowing()) {
			return;
		}
		TextView tv_no, tv_ok, tv_message, tv_message2;
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.dialog_tishi, null);
		dialog = new AlertDialog.Builder(getActivity()).create();
		dialog.show();
		dialog.setContentView(view);
		tv_no = (TextView) view.findViewById(R.id.tv_no);
		tv_ok = (TextView) view.findViewById(R.id.tv_ok);
		tv_message2 = (TextView) view.findViewById(R.id.tv_message2);
		tv_message = (TextView) view.findViewById(R.id.tv_message);
		tv_message2.setText("你确定要接这个任务吗？");
		tv_message.setText("接警提示");

		tv_ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				acceptTaskAction(taskId);
				dialog.dismiss();
			}
		});
		tv_no.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

	}

	/**
	 * 接警操作
	 * 
	 * @param taskId
	 */
	private void acceptTaskAction(int taskId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", taskId);
		final XRequestManager manager = new XRequestManager(getActivity());
		manager.post(Constants.POLICE_ACCEPTTASK, map, new XRequestListener() {
			@Override
			public void onRequestStart() {

			}

			@Override
			public void onRequestSuccess(XResponse res) {

				int code = res.getStatusCode();
				if (code == 1) {
					UHelper.showToast(getActivity(), res.getMessage());
				} else
					UHelper.showToast(getActivity(), res.getMessage());
			}

			@Override
			public void onRequestFailure(HttpException error, String msg) {

			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent in = new Intent();
		in.putExtra("taskId", listTask.get(arg2 - 1).getId());
		in.setClass(getActivity(), TaskInfoActivity.class);
		getActivity().startActivity(in);
	}
}
