package com.inforun.safecitypolice.activity;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import com.inforun.safecitypolice.BaseActivity;
import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.adapter.AlarmPictureAdapter;
import com.inforun.safecitypolice.finals.Constants;
import com.inforun.safecitypolice.request.SocketHttpRequester;
import com.inforun.safecitypolice.utils.FormFile;
import com.inforun.safecitypolice.LocationService;


public class SendAlarmActivity extends BaseActivity 
		implements OnClickListener,BDLocationListener{

	GridView picGridView;
	EditText alarmText;
	final String PHOTO_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/safecitypolice/";
	String simplePhotoFile;
	ArrayList<String> pictureList;
	ArrayList<File> fileList;//图片列表
	File videoFile;//视频文件
	AlarmPictureAdapter adapter;
	ImageView[] imgList = new ImageView[10];
	int[] ids = new int[]{R.id.image0,R.id.image1,R.id.image2,R.id.image3,R.id.image4,
			R.id.image5,R.id.image6,R.id.image7,R.id.image8,R.id.image9};
	int count = 0;
	double latitude = -1.0;
	double longitude = -1.0;
	LocationClient mLocClient;
	FormFile[] mFileList;
	Thread th;
	TextView picture_count,video_count;
	Runnable rb = new Runnable(){
		@Override
		public void run(){
			sendAlarm();
		}
	};
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        pictureList = new ArrayList<String>();
        fileList = new ArrayList<File>();
        System.out.println(Environment.getExternalStorageDirectory().getAbsolutePath());
        //adapter = new AlarmPictureAdapter(this, pictureList);
        initView();
        initListener();
        initLocation();
	}
	

	protected void initView() {
		// TODO Auto-generated method stub
		picture_count = (TextView)findViewById(R.id.picture_count);
		alarmText = (EditText)findViewById(R.id.alarm_text);
		for(int i = 0;i < 10;i ++){
			imgList[i] = (ImageView)findViewById(ids[i]);
		}
	}


	protected void initListener() {
		// TODO Auto-generated method stub
		findViewById(R.id.take_photo).setOnClickListener(this);
		findViewById(R.id.take_video).setOnClickListener(this);
		findViewById(R.id.submit).setOnClickListener(this);
	}
	
	protected void initLocation() {
		mLocClient = new LocationClient(this); // 声明LocationClient类
		mLocClient.registerLocationListener(this);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02 bd09ll
		int span = 10000;
		option.setScanSpan(span);// 设置发起定位请求的间隔时间为10s
		option.setOpenGps(true); // 打开gps
		option.setIsNeedAddress(true);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
			case R.id.take_photo:
				if(count<10) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//调用相机
					startActivityForResult(intent, 1);
				} else {
					Toast.makeText(this, "最多上传10张图片", Toast.LENGTH_LONG).show();
				}
		        break;
			case R.id.take_video:
				Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
				startActivityForResult(intent, 2);
				break;
			case R.id.submit:
				th = new Thread(rb);
				th.start();
			
		}
	}
	private void sendAlarm() {
		if(latitude > 0.0 && longitude > 0.0) {			
			Map<String,String> data = new HashMap<String,String>();
			data.put("text", alarmText.getText().toString().trim());
			data.put("coordStr", longitude+" "+latitude);
			int length = fileList.size();
			FormFile[] fList;
			if(videoFile == null) {
				fList = new FormFile[length];
				for(int i = 0;i < length;i ++) {
					fList[i] = new FormFile(fileList.get(i).getName(), fileList.get(i), "files");
					
				}
			} else {
				fList = new FormFile[length+1];
				for(int i = 0;i < length;i ++){
					fList[i] = new FormFile(fileList.get(i).getName(), fileList.get(i), "files");
					
				}
				FormFile vf = new FormFile(videoFile.getName(), videoFile, "files");
				vf.setContentType("video/mpeg4");
				fList[length] = vf;
			}
			try {
				SocketHttpRequester.post(Constants.SEND_ALARM, data, fList);
				//startService(new Intent(this,LocationService.class));
				System.out.println("success");
				Intent i = new Intent(this,HelpActivity.class);
				startActivity(i);
				finish();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				Intent i = new Intent(this,HelpActivity.class);
				startActivity(i);
				finish();
			}
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    System.out.println("requestCode:" + requestCode +"resultCode:"+resultCode);
	    if (resultCode == Activity.RESULT_OK) {

	    	String sdStatus = Environment.getExternalStorageState();
	    	if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
	    		Toast.makeText(this, "SD卡不可用", Toast.LENGTH_LONG).show();
	    		return;
	    	}
	    	if(requestCode == 1) {
		    	Bundle bundle = data.getExtras();//获取数据
		    	Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
		    	FileOutputStream b = null;
		    	File dir = new File(PHOTO_PATH);
		    	dir.mkdirs();// 创建文件夹
		    	long time = new Date().getTime();
		    	String uploadFile = PHOTO_PATH + time + ".jpg";
		    	try {
		    		b = new FileOutputStream(uploadFile);//将数据写入/sdcard/myImage/文件夹下111.jpg中
		    		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
		    		File f = new File(uploadFile);
		    		if(fileList == null) {
		    			fileList = new ArrayList<File>();
		    		}
		    		fileList.add(f);
		    		pictureList.add(uploadFile);
		    	} catch (FileNotFoundException e) {
		    		e.printStackTrace();
		    	} finally {
		    		try {
		    			b.flush();
		    			b.close();
		    		} catch (IOException e) {
		    			e.printStackTrace();
		    		}
		    	}
		    	imgList[count].setImageBitmap(bitmap);
		    	count ++;
		    	picture_count.setText("共"+count+"张图片");
	    	} else if(requestCode == 2) {
	    		if (null != data) {
	    			Uri uri = data.getData();
	    			if (uri == null) {
	    				return;
	    			} else {
	    				Cursor c = getContentResolver().query(uri,
	    						new String[] { MediaStore.MediaColumns.DATA }, null,
	    						null, null);
	    				if (c != null && c.moveToFirst()) {
	    					String filPath = c.getString(0);
	    					videoFile = new File(filPath);
	    					System.out.println(filPath);
	    					//new Upload(filPath).start();
	    				}
	    			}
	    		}
	    	}
  
	      
	    }
	}
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mLocClient.stop();
        System.out.println("onDestroy");
    }
	@Override
	public void onReceiveLocation(BDLocation arg0) {
		// TODO Auto-generated method stub
		latitude = arg0.getLatitude();
		longitude = arg0.getLongitude();
		System.out.println(arg0.getLatitude() + "," + arg0.getLongitude());
	}
	
}
