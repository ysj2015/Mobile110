package com.inforun.safecitypeople.activity;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.inforun.safecitypeople.BaseActivity;
import com.inforun.safecitypeople.Constants;
import com.inforun.safecitypeople.LocationService;
import com.inforun.safecitypeople.R;
import com.inforun.safecitypeople.adapter.AlarmPictureAdapter;
import com.inforun.safecitypeople.request.SocketHttpRequester;
import com.inforun.safecitypeople.utils.FormFile;
import com.inforun.safecitypeople.utils.RegExpUtils;

public class AlarmInfoActivity extends BaseActivity implements OnClickListener,BDLocationListener {
	private ArrayList<File> fileList;
	private ArrayList<String> filePathList;
	private EditText alarmText;
	final String PHOTO_PATH = Environment.getExternalStorageDirectory().getPath()+"/people";
	String path;
	String alarm_type;
	int[] ids = new int[]{R.id.img0, R.id.img1, R.id.img2, R.id.img3,
			R.id.img4, R.id.img5};
	int index = 0;
	double latitude = -1.0;
	double longitude = -1.0;
	LocationClient mLocClient;
	FormFile[] mFileList;
	Thread th;
	Runnable rb = new Runnable(){
		@Override
		public void run(){
			sendAlarm();
		}
	};
	Handler hd = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int code = msg.what;
			if(code == 500) {
				startService(new Intent(AlarmInfoActivity.this,LocationService.class));
				Intent in = new Intent(AlarmInfoActivity.this,HelpActivity.class);
				in.putExtra("status", 0);
				startActivity(in);
				finish();
			} else {
				Toast.makeText(AlarmInfoActivity.this, "已有进行中的报警", Toast.LENGTH_LONG).show();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarminfo);
		initView();
		initData();
		initLocation();
		setUpActionBar();
		//System.out.println(getResources().getDimension(R.dimen.thumb_size));
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
	private void setUpActionBar() {
		initActionBar();
		setActionBarVisible(true);
		setActionBarMidTitle("报警详情");
		setActionBarLeftLayoutVisiable(false);
	}
	private void initView() {
		alarmText = (EditText)findViewById(R.id.alarm_text);
		findViewById(R.id.take_photo).setOnClickListener(this);
		findViewById(R.id.submit).setOnClickListener(this);
		
		findViewById(R.id.img0).setOnClickListener(this);
		findViewById(R.id.img1).setOnClickListener(this);
		findViewById(R.id.img2).setOnClickListener(this);
		findViewById(R.id.img3).setOnClickListener(this);
		findViewById(R.id.img4).setOnClickListener(this);
		findViewById(R.id.img5).setOnClickListener(this);
	}
	private void initData() {
		fileList = new ArrayList<File>();
		filePathList = new ArrayList<String>();
		alarm_type = getIntent().getExtras().getString("type");
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()) {
		case R.id.take_photo:
			long now = System.currentTimeMillis();
			path = PHOTO_PATH + "/" + now + ".jpg";
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//调用相机
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(path)));
	        startActivityForResult(intent, 1);
			
	        break;
		case R.id.submit:
			if(alarmText.getText().toString().length() <= 100 && 
					!RegExpUtils.isContainSpecial(alarmText.getText().toString())) {
				th = new Thread(rb);
				th.start();
			} else {
				Toast.makeText(AlarmInfoActivity.this, "报警描述不能超过100个字符,且不得含有特殊字符", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.img0:
			showBigPicture(0);
			break;
		case R.id.img1:
			showBigPicture(1);
			break;
		case R.id.img2:
			showBigPicture(2);
			break;
		case R.id.img3:
			showBigPicture(3);
			break;
		case R.id.img4:
			showBigPicture(4);
			break;
		case R.id.img5:
			showBigPicture(5);
		}
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		File fp = new File(path);
		if(fp.exists() && index < 6) {
			ImageView iv = (ImageView)findViewById(ids[index]);
			//iv.setImageBitmap(showLocalPicture(path));
			
			int size = (int)(getResources().getDimension(R.dimen.thumb_size));
			iv.setImageBitmap(getImageThumbnail(path,size,size));
//			iv.setImageBitmap(getImageThumbnail(path,))
			index ++;
			fileList.add(fp);
		}
	}
	private void showBigPicture(int i) {
		if(fileList.size() > i) {
			Intent in = new Intent(this,AlarmPictureActivity.class);
			in.putExtra("picture", fileList.get(i).getAbsolutePath());
			startActivity(in);
		}
	}
	private Bitmap showLocalPicture(String file){
		try {
			FileInputStream is = new FileInputStream(file);
			return BitmapFactory.decodeStream(is);
		} catch(Exception e) {
			Log.v("exception",e.getMessage());
			return null;
		}
	}
	private Bitmap getImageThumbnail(String imagePath, int width, int height) {  
        Bitmap bitmap = null;  
        BitmapFactory.Options options = new BitmapFactory.Options();  
        options.inJustDecodeBounds = true;  
        // 获取这个图片的宽和高，注意此处的bitmap为null  
        bitmap = BitmapFactory.decodeFile(imagePath, options);  
        options.inJustDecodeBounds = false; // 设为 false  
        // 计算缩放比  
        int h = options.outHeight;  
        int w = options.outWidth;  
        int beWidth = w / width;  
        int beHeight = h / height;  
        int be = 1;  
        if (beWidth < beHeight) {  
            be = beWidth;  
        } else {  
            be = beHeight;  
        }  
        if (be <= 0) {  
            be = 1;  
        }  
        options.inSampleSize = be;  
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false  
        bitmap = BitmapFactory.decodeFile(imagePath, options);  
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象  
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,  
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);  
        return bitmap;  
    }
	private void sendAlarm() {
		if(latitude > 0.0 && longitude > 0.0) {			
			Map<String,String> data = new HashMap<String,String>();
			if("".equals(alarm_type)) {
				data.put("text", alarmText.getText().toString().trim());
			} else {
				data.put("text", "[" + alarm_type + "]" 
						+ alarmText.getText().toString().trim());
			}
			longitude=longitude-0.04;
			data.put("coordStr", longitude+" "+latitude);
			int length = fileList.size();
			System.out.println("--------1");
			FormFile[] fList = new FormFile[length];
			for(int i = 0;i < length;i ++) {
				fList[i] = new FormFile(fileList.get(i).getName(), fileList.get(i), "files");				
			}
			System.out.println("--------2");
			try {
				
				boolean flag = SocketHttpRequester.post(Constants.SEND_ALARM, data, fList);
				System.out.println(flag);
				if(flag){
					hd.sendEmptyMessage(500);
				} else {
					hd.sendEmptyMessage(800);
				}
				System.out.println("success");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}
	}
	@Override
	public void onReceiveLocation(BDLocation arg0) {
		// TODO Auto-generated method stub
		latitude = arg0.getLatitude();
		longitude = arg0.getLongitude();
		System.out.println(arg0.getLatitude() + "," + arg0.getLongitude());
	}
}
