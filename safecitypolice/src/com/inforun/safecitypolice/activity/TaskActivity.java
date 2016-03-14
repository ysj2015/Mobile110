package com.inforun.safecitypolice.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.inforun.safecitypolice.BaseActivity;
import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.fragment.CurrentTaskFragment;
import com.inforun.safecitypolice.fragment.HistoryTaskFragment;
import com.inforun.safecitypolice.fragment.MissTaskFragment;

/**
 * 任务界面
 * @author daniel
 *
 */
public class TaskActivity extends BaseActivity implements OnClickListener{

/**
 * 当前任务
 */
	private TextView tv_currentTask;
	/**
	 * 历史任务
	 */
	private TextView tv_historyTask;
	/**
	 * 待接任务
	 */
	private TextView tv_missTask;
	private int WindowsWidth, viewWidth;
	/**
	 * 下划线
	 */
	private View huaView1,huaView2,huaView3;
	
	/**
	 * 当前项
	 */
	private int current=0;
	
	/**
	 * 上一项
	 */
	private int history=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		setUpActionBar();
		initView();
	}

	/**
	 * 初始化
	 */
	protected void initView() {
		tv_currentTask=(TextView) findViewById(R.id.tv_currentTask);
		tv_historyTask=(TextView) findViewById(R.id.tv_historyTask);
		tv_missTask=(TextView) findViewById(R.id.tv_missTask);
		tv_currentTask.setOnClickListener(this);
		tv_historyTask.setOnClickListener(this);
		tv_missTask.setOnClickListener(this);
	
		huaView1 =(View) findViewById(R.id.huaView1);
		huaView2 = (View)findViewById(R.id.huaView2);
		huaView3 = (View)findViewById(R.id.huaView3);
		transitionFragment(0);
	}
	/**
	 * 隐藏actionBar上的控件
	 */
	private void setUpActionBar() {
		setActionBarVisible(true);
		setActionBarMidTitle(getString(R.string.task));
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_currentTask://当前任务
			transitionFragment(1);
			break;
		case R.id.tv_historyTask://历史任务
			transitionFragment(2);
			break;
		case R.id.tv_missTask://待接任务
			transitionFragment(3);
			break;
		}
	}

	/**
	 * 跳到替换相应的fragment
	 */
	private void transitionFragment(int current) {
		// TODO Auto-generated method stub
		huaViewSelect(current);
		Fragment fragment = null;
		switch (current) {
		case 1:
			fragment=new CurrentTaskFragment();
			huaView1.setVisibility(View.VISIBLE);
			break;
		case 2:
			fragment=new HistoryTaskFragment();
			break;
		case 3:
			fragment=new MissTaskFragment();
			break;

		default:
			fragment=new CurrentTaskFragment();
			huaView1.setVisibility(View.VISIBLE);
			break;
		}
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.realtabcontent, fragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
	}
	
	/**
	 * 下划线的选择
	 * @param current
	 */
	private void huaViewSelect(int current){
		switch (current) {
		case 1:
			huaView1.setVisibility(View.VISIBLE);
			huaView2.setVisibility(View.INVISIBLE);
			huaView3.setVisibility(View.INVISIBLE);
			tv_currentTask.setTextColor(Color.parseColor("#00a0e9"));
			tv_historyTask.setTextColor(Color.parseColor("#B5B5B5"));
			tv_missTask.setTextColor(Color.parseColor("#B5B5B5"));
			break;
		case 2:
			huaView1.setVisibility(View.INVISIBLE);
			huaView2.setVisibility(View.VISIBLE);
			huaView3.setVisibility(View.INVISIBLE);
			tv_currentTask.setTextColor(Color.parseColor("#B5B5B5"));
			tv_historyTask.setTextColor(Color.parseColor("#00a0e9"));
			tv_missTask.setTextColor(Color.parseColor("#B5B5B5"));
			break;
		case 3:
			huaView1.setVisibility(View.INVISIBLE);
			huaView2.setVisibility(View.INVISIBLE);
			huaView3.setVisibility(View.VISIBLE);
			tv_currentTask.setTextColor(Color.parseColor("#B5B5B5"));
			tv_historyTask.setTextColor(Color.parseColor("#B5B5B5"));
			tv_missTask.setTextColor(Color.parseColor("#00a0e9"));
			break;
		default:
			huaView1.setVisibility(View.VISIBLE);
			huaView2.setVisibility(View.INVISIBLE);
			huaView3.setVisibility(View.INVISIBLE);
			tv_currentTask.setTextColor(Color.parseColor("#00a0e9"));
			tv_historyTask.setTextColor(Color.parseColor("#B5B5B5"));
			tv_missTask.setTextColor(Color.parseColor("#B5B5B5"));
			break;
		}
	}
}
