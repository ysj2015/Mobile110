package com.inforun.safecitypolice.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.inforun.safecitypolice.BaseActivity;
import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.entity.Task;
import com.inforun.safecitypolice.finals.Constants;
import com.inforun.safecitypolice.other.ImagePagerActivity;
import com.inforun.safecitypolice.request.XRequestListener;
import com.inforun.safecitypolice.request.XRequestManager;
import com.inforun.safecitypolice.request.XResponse;
import com.inforun.safecitypolice.utils.DateUtil;
import com.inforun.safecitypolice.utils.StringUtils;
import com.inforun.safecitypolice.utils.UHelper;
import com.lidroid.xutils.exception.HttpException;

public class TaskInfoActivity extends BaseActivity implements OnClickListener{
	private int taskId;
	private Task task;
	private TextView tv_alarmInfo,tv_alarmTime;
	private Dialog dialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);
        taskId = getIntent().getExtras().getInt("taskId");
        initView();
        initListener();
        Log.v("taskId", taskId + "");
        getCurrentTask();

    }
    private void initView() {
    	tv_alarmInfo = (TextView) findViewById(R.id.tv_alarmInfo);
		tv_alarmTime = (TextView) findViewById(R.id.tv_alarmTime);
    }
    private void initListener() {
    	findViewById(R.id.btn_picture).setOnClickListener(this);
    	findViewById(R.id.btn_video).setOnClickListener(this);
    	findViewById(R.id.btn_accept).setOnClickListener(this);
    }
	private void getCurrentTask() {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", taskId);
		final XRequestManager manager = new XRequestManager(this);
		manager.post(Constants.POLICE_GETTASKINFO, map, new XRequestListener() {
			@Override
			public void onRequestStart() {
				manager.setProgressMessage("加载中...");
			}

			@Override
			public void onRequestSuccess(XResponse res) {
				int code = res.getStatusCode();
				if (code == 1) {
					task = (Task) res.getEntity("task", Task.class);
					if(task != null){
//						tv_alarmInfo.setText(task.getContent());
//						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//						tv_alarmTime.setText(df.format(task.getAlarmInfo().getTime()));
					}
//					System.out.println(task.getContent());
//					System.out.println(task.getAlarmInfo().getTime());
//					System.out.println(res.get)
//					currentTask = (Task) res.getEntity("currTask", Task.class);
//					if (null != currentTask) {
//						tv_alarmInfo.setText(currentTask.getContent() + "");
//						tv_alarmTime.setText(DateUtil.getDate(currentTask
//								.getAcceptTime() + "")
//								+ "");
//						noDate(false);
//					} else {
//						noDate(true);
//					}
				} else {
//					noDate(true);
				}
			}

			@Override
			public void onRequestFailure(HttpException error, String msg) {
				//noDate(false);
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=null;
		switch (v.getId()) {
		case R.id.btn_picture://查看图片
			ArrayList<String> urls = new ArrayList<String>();
			if(task != null) {
//				if(!StringUtils.isEmpty(task.getAlarmInfo().getVideo())){
//					String[] img_arr = task.getAlarmInfo().getImg().split(" ");
//					for(int i = 0;i < img_arr.length;i ++) {
//						urls.add(img_arr[i]);
//					}
//					imageBrower(urls);
//				}
			}
			break;
		case R.id.btn_video://查看视频
			//ArrayList<String> urls = new ArrayList<String>();
//			if(task != null) {
//				if (!StringUtils.isEmpty(task.getAlarmInfo().getVideo())) {
//					intent=new Intent( this,VideoPlayActivity.class);
//					intent.putExtra("videoUrl",task.getAlarmInfo().getVideo());
//					startActivity(intent);
//				}
//			}
			break;
		case R.id.btn_accept:
			acceptTask(taskId);
		}
	}
	private void imageBrower(ArrayList<String> urls2) {
		Intent intent = new Intent(this, ImagePagerActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
		startActivity(intent);
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
		View view = LayoutInflater.from(this).inflate(
				R.layout.dialog_tishi, null);
		dialog = new AlertDialog.Builder(this).create();
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
		final XRequestManager manager = new XRequestManager(this);
		manager.post(Constants.POLICE_ACCEPTTASK, map, new XRequestListener() {
			@Override
			public void onRequestStart() {

			}

			@Override
			public void onRequestSuccess(XResponse res) {

				int code = res.getStatusCode();
				if (code == 1) {
					UHelper.showToast(TaskInfoActivity.this, res.getMessage());
				} else
					UHelper.showToast(TaskInfoActivity.this, res.getMessage());
			}

			@Override
			public void onRequestFailure(HttpException error, String msg) {

			}
		});
	}
}
