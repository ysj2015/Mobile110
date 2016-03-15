package com.inforun.safecitypeople.fragment;

import java.util.HashMap;

import org.json.JSONObject;

import com.inforun.safecitypeople.Constants;
import com.inforun.safecitypeople.R;
import com.inforun.safecitypeople.activity.HelpActivity;
import com.inforun.safecitypeople.activity.LoginActivity;
import com.inforun.safecitypeople.activity.PanelActivity;
import com.inforun.safecitypeople.activity.SettingActivity;
import com.inforun.safecitypeople.entity.User;
import com.inforun.safecitypeople.request.AsyncHttpManager;
import com.inforun.safecitypeople.request.XRequestListener;
import com.inforun.safecitypeople.request.XRequestManager;
import com.inforun.safecitypeople.request.XResponse;
import com.lidroid.xutils.exception.HttpException;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonalFragment extends Fragment implements OnClickListener{
	View view;
	TextView tv_name,tv_phone;
	ImageView iv_photo;
	User user;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_personal, null);
		initView(view);
		return view;
	}
	
	private void initView(View view) {
		tv_name = (TextView)view.findViewById(R.id.user_name);
		tv_phone = (TextView)view.findViewById(R.id.user_phone);
		iv_photo = (ImageView)view.findViewById(R.id.user_photo);
		
		view.findViewById(R.id.setting).setOnClickListener(this);
		view.findViewById(R.id.help).setOnClickListener(this);
		view.findViewById(R.id.leftLinear).setVisibility(View.GONE);
		
		TextView title = (TextView)view.findViewById(R.id.mid_title);
		title.setText("个人中心");
		getMyInfo();
	}
	private void getMyInfo() {
		HashMap<String, Object> map = new HashMap<String, Object>();

		final XRequestManager manager = new com.inforun.safecitypeople.request.XRequestManager(getActivity());
		
		//manager.getSessionId();
		manager.post(Constants.GET_USER_INFO, map, new XRequestListener() {
			@Override
			public void onRequestStart() {
				manager.setProgressMessage("加载中...");
			}

			@Override
			public void onRequestSuccess(XResponse res) {
				int code = res.getStatusCode();
				if (code == 1) {
					user = (User) res.getEntity("user", User.class);
					if (null != user) {
						tv_name.setText(user.getDetails().getName());
						tv_phone.setText(user.getDetails().getTel());
						loadImg("http://222.223.170.241:8099/zyhCity160129" + user.getDetails().getPhoto(), iv_photo);
						if(user.getCurrAlarm() == null) {
							view.findViewById(R.id.help).setVisibility(View.GONE);
						}
					}
				}
			}

			@Override
			public void onRequestFailure(HttpException error, String msg) {
				
			}
		});
	}
	
	private void loadImg(String url,final ImageView image){
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));
        
        DisplayImageOptions op = new DisplayImageOptions.Builder()
    	.cacheInMemory(true).cacheOnDisc(true).build();
        ImageLoader.getInstance().loadImage(url, op, new SimpleImageLoadingListener(){
        	@Override
        	public void onLoadingStarted(String imageUri, View view){
        		super.onLoadingStarted(imageUri, view);
        		//image.setBackgroundColor(0x000000);
        	}
        	@Override
        	public void onLoadingComplete(String imageUri, View view, Bitmap bmp){
        		super.onLoadingComplete(imageUri,view,bmp);
        		image.setImageBitmap(bmp);
        	}
        });
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent in;
		switch(arg0.getId()) {
			case R.id.setting:
				in = new Intent(getActivity(),SettingActivity.class);
				getActivity().startActivity(in);
				break;
			case R.id.help:
				in = new Intent(getActivity(),HelpActivity.class);
				getActivity().startActivity(in);
		}
	}
}
