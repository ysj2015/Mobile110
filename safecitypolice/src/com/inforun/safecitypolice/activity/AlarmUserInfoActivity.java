package com.inforun.safecitypolice.activity;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inforun.safecitypolice.BaseActivity;
import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.bitmaploader.BitmapHelper;
import com.inforun.safecitypolice.entity.User;
import com.inforun.safecitypolice.entity.UserDetails;
import com.inforun.safecitypolice.finals.Constants;
import com.inforun.safecitypolice.request.XRequestListener;
import com.inforun.safecitypolice.request.XRequestManager;
import com.inforun.safecitypolice.request.XResponse;
import com.inforun.safecitypolice.utils.StringUtils;
import com.lidroid.xutils.exception.HttpException;

/**
 * 报警人信息
 * 
 * @author xiongchaoxi
 * 
 */
public class AlarmUserInfoActivity extends BaseActivity {

	/**
	 * 用户id
	 */
	private int userId;

	/**
	 * 姓名
	 */
	private TextView tv_name;
	/**
	 * 年龄
	 */
	private TextView tv_age;
	/**
	 * 性别
	 */
	private TextView tv_sex;
	/**
	 * 电话
	 */
	private TextView tv_phone;
	/**
	 * 地址
	 */
	private TextView tv_address;

	/**
	 * 头像
	 */
	private ImageView tv_headPic;
	/**
	 * 拨打电话
	 */
	private ImageView iv_phone;

	/**
	 * 没数据
	 */
	private TextView tv_noDate;

	/**
	 * 父容器
	 */
	private LinearLayout ll_parent;

	/**
	 * 用户对象
	 */
	private User mUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarmuserinfo);
		setUpActionBar();
		userId = getIntent().getIntExtra("userId", -1);
		initView();
	}

	/**
	 * 初始化
	 */
	private void initView() {
		// TODO Auto-generated method stub
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_age = (TextView) findViewById(R.id.tv_age);
		tv_sex = (TextView) findViewById(R.id.tv_sex);
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		tv_address = (TextView) findViewById(R.id.tv_address);
		tv_headPic = (ImageView) findViewById(R.id.tv_headPic);
		iv_phone = (ImageView) findViewById(R.id.iv_phone);
		tv_noDate = (TextView) findViewById(R.id.tv_noDate);
		ll_parent = (LinearLayout) findViewById(R.id.ll_parent);

		/**
		 * 拨打电话
		 */
		iv_phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (null != mUser) {
					if (null != mUser.getDetails()) {
//						Intent intent = new Intent();
//						intent.setAction(Intent.ACTION_DIAL);
//						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//						//用intent启动拨打电话  
//		                Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number));  
//		                startActivity(intent);  
//						intent.setData(Uri.parse(mUser.getDetails().getTel()));

//						startActivity(intent);
						
						Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+mUser.getDetails().getTel())); 
					    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
					    startActivity(intent);
					}
				}

			}
		});
		getAlarmUserInfo();
	}
	/**
	 * 隐藏actionBar上的控件
	 */
	private void setUpActionBar() {
		setActionBarVisible(true);
		setActionBarMidTitle(getString(R.string.alarm_user_info));
	}
	@Override
	public void startActivity(Intent intent) {
		// TODO Auto-generated method stub
		super.startActivity(intent);
	}

	/**
	 * 获取用户信息
	 */
	private void getAlarmUserInfo() {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		final XRequestManager manager = new XRequestManager(this);
		manager.post(Constants.POLICE_GETUSER, map, new XRequestListener() {
			@Override
			public void onRequestStart() {
				manager.setProgressMessage("加载中...");
			}

			@Override
			public void onRequestSuccess(XResponse res) {
				int code = res.getStatusCode();
				if (code == 1) {
					mUser = (User) res.getEntity("user", User.class);
					if (null != mUser) {
						mUser = mUser;
						UserDetails details = mUser.getDetails();
						if (null != details) {
							if (StringUtils.isEmpty(details.getName())) {
								tv_name.setText("无");
							} else {
								tv_name.setText(details.getName() + "");
							}

							if (StringUtils.isEmpty(details.getSex())) {
								tv_sex.setText("无");
							} else {
								tv_sex.setText(details.getSex() + "");
							}
							if (StringUtils.isEmpty(details.getAddress())) {
								tv_address.setText("无");
							} else {
								tv_address.setText(details.getAddress() + "");
							}
							if (StringUtils.isEmpty(details.getTel())) {
								tv_phone.setText("无");
								iv_phone.setVisibility(View.INVISIBLE);
							} else {
								tv_phone.setText(details.getTel() + "");
								iv_phone.setVisibility(View.VISIBLE);
							}
							BitmapHelper.getBitmapUtils().display(tv_headPic,
									Constants.BASE_URL + details.getPhoto());
							noDate(false);
						} else {
							noDate(true);
						}
					} else {
						noDate(true);
					}

				} else {
					noDate(true);
				}
			}

			@Override
			public void onRequestFailure(HttpException error, String msg) {
				noDate(true);
			}
		});
	}

	/**
	 * 没有数据的时候
	 * 
	 * @param b
	 */
	private void noDate(boolean b) {
		// TODO Auto-generated method stub
		if (b) {
			tv_noDate.setVisibility(View.VISIBLE);
			ll_parent.setVisibility(View.GONE);
		} else {
			tv_noDate.setVisibility(View.GONE);
			ll_parent.setVisibility(View.VISIBLE);
		}

	}
}
