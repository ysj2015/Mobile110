package com.inforun.safecitypeople.activity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.inforun.safecitypeople.BaseActivity;
import com.inforun.safecitypeople.Constants;
import com.inforun.safecitypeople.R;
import com.inforun.safecitypeople.request.AsyncHttpManager;
import com.inforun.safecitypeople.utils.RegExpUtils;
import com.inforun.safecitypeople.utils.Wt_UtilsValidateTx;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.loopj.android.http.JsonHttpResponseHandler;

public class RegisterActivity extends BaseActivity implements OnClickListener{

	EditText et_id,et_name,et_phone,et_address;
	TextView et_birthday;
	
	File photo;
	String uploadFile;
	final String PHOTO_PATH = Environment.getExternalStorageDirectory().getPath()+"/people";
	String sex;
	RadioGroup sex_rg;
	ImageView iv_userphoto;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUpActionBar();
        initView();
        initListener();
        System.out.println(PHOTO_PATH);
	}
	private void setUpActionBar() {
		setActionBarVisible(true);
		setActionBarMidTitle("注册");
		setActionBarLeftLayoutVisiable(true);
	}
	protected void initView() {
		iv_userphoto = (ImageView)findViewById(R.id.user_photo);
		
		et_id = (EditText)findViewById(R.id.edt_id);
		et_name = (EditText)findViewById(R.id.edt_name);
		et_phone = (EditText)findViewById(R.id.edt_phone);
		et_address = (EditText)findViewById(R.id.edt_address);
		sex_rg = (RadioGroup) this.findViewById(R.id.sex_rg);
	}


	protected void initListener() {
		// TODO Auto-generated method stub
		findViewById(R.id.take_photo).setOnClickListener(this);
		findViewById(R.id.register).setOnClickListener(this);
		sex_rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.male) {
					sex = "男";
//					if(photo == null) {
//						iv_userphoto.setImageResource(R.drawable.man);
//						createPhotoBySex(R.drawable.man);
//					}
				}
				if (checkedId == R.id.female) {
					sex = "女";
//					if(photo == null) {
//						iv_userphoto.setImageResource(R.drawable.woman);
//						createPhotoBySex(R.drawable.woman);
//					}
				}
			}
		});
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
			case R.id.take_photo:
				
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//调用相机
		        startActivityForResult(intent, 1);
				
		        break;
			case R.id.register:
				if(dataValid()){
					register();
				}
				break;
				
		}
	}
	private void createPhotoBySex(int resID) {
		long dataTake = System.currentTimeMillis();
		String jpegName = PHOTO_PATH + "/" + dataTake +".jpg";
//		Log.i(TAG, "saveBitmap:jpegName = " + jpegName);
		Bitmap b = BitmapFactory.decodeResource(getResources(), resID);
		try {
			FileOutputStream fout = new FileOutputStream(jpegName);
			BufferedOutputStream bos = new BufferedOutputStream(fout);
			b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
			photo = new File(jpegName);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	private boolean dataValid() {
//		if(null == photo) {
//			Toast.makeText(this, "请选择头像", Toast.LENGTH_LONG).show();
//			return false;
//		} 
//		if(null == sex) {
//			Toast.makeText(this, "请填写性别", Toast.LENGTH_LONG).show();
//			return false;
//		}
		if(!RegExpUtils.isChinese(et_name.getText().toString().trim())) {
			Toast.makeText(this, "必须输入中文姓名",
					Toast.LENGTH_LONG).show();
			return false;
		}
		if(Wt_UtilsValidateTx.isMobile(et_phone.getText().toString().trim())==0) {
			Toast.makeText(this, "手机号内容不能为空！",
					Toast.LENGTH_LONG).show();
			return false;
		}else if(Wt_UtilsValidateTx.isMobile(et_phone.getText().toString().trim())==1){
		}else{
			Toast.makeText(this, "请输入正确的手机号！",
					Toast.LENGTH_LONG).show();
			return false;
		}
		if(!RegExpUtils.isSFZ(et_id.getText().toString().trim())) {
			Toast.makeText(this, "身份证必须由18位数字或17位数字加x组成",
					Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}
	private void register() {
		//实例化HttpUtils对象， 参数设置链接超时
		HttpUtils HTTP_UTILS = new HttpUtils(10 * 1000);
		//实例化RequestParams对象
		RequestParams params = new RequestParams();
		
		params.addBodyParameter("name", et_name.getText().toString().trim());		
		params.addBodyParameter("tel", et_phone.getText().toString().trim());
		params.addBodyParameter("shenFenId", et_id.getText().toString().trim());

		params.addBodyParameter("address", et_address.getText().toString().trim());
		if(sex != null) {

			params.addBodyParameter("sex", sex);
		}
		if(photo != null) {
			params.addBodyParameter("file", photo, "image/jpg");
		}
			//通过HTTP_UTILS来发送post请求， 并书写回调函数
		HTTP_UTILS.send(HttpMethod.POST, Constants.USER_REGISTER, params, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException httpException, String arg1) {
				Log.v("result", "fail");
				Log.v("result", httpException.getMessage());
				Toast.makeText(RegisterActivity.this, "请检查网络", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				Log.v("result", responseInfo.result);
				try {
					JSONObject json = new JSONObject(responseInfo.result);
					int code = json.getInt("code");
					if(code == 1) {
						Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
						finish();
					} else {
						Toast.makeText(RegisterActivity.this, json.getString("message"), Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Toast.makeText(RegisterActivity.this, "系统异常，请稍候再试", Toast.LENGTH_LONG).show();
					//e.printStackTrace();
				}
				
			}
		});
		
//		System.out.println("register");
//		RequestParams params = new RequestParams();
//		params.put("shenFenId", et_id.getText().toString().trim());
//		params.put("tel", et_phone.getText().toString().trim());
//		params.put("name", et_name.getText().toString().trim());
//		if(sex != null) {
//			params.put("sex", sex);
//		}
//		String address = et_address.getText().toString().trim();
//		if(!address.equals("")) {
//			params.put("address", address);
//		}
//		System.out.println("register");
//		if(photo != null) {
//			try {
//				photo.
//				params.put("file", photo);
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				System.out.println(e.getMessage());
//				e.printStackTrace();
//			}
//		}
//		AsyncHttpManager.getAsyncHttpClient().post(Constants.USER_REGISTER, params, new JsonHttpResponseHandler(){
//			public void onSuccess(JSONObject arg0) {
//				System.out.println(arg0.toString());
//				try{
//					int code = arg0.getInt("code");
//					String msg = arg0.getString("message");
//					if(code == 1) {
//						Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
//						finish();
//					} else {
//						Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_LONG).show();
//					}
//				}
//				catch(Exception e) {
//					Toast.makeText(RegisterActivity.this, "系统异常，请稍候再试", Toast.LENGTH_LONG).show();
//				}
//
//			}
//			public void onFailure(Throwable arg0) { // 失败，调用
//	            System.out.println("fail");
//	        }
//	        public void onFinish() { // 完成后调用，失败，成功，都要掉
//	        	
//	        }
//		}
//				
//				
//		);
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    if (resultCode == Activity.RESULT_OK) {
	    	
	    	String sdStatus = Environment.getExternalStorageState();
	    	if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
	    		Toast.makeText(this, "SD卡不可用", Toast.LENGTH_LONG).show();
	    		return;
	    	}

	    	Bundle bundle = data.getExtras();//获取数据
	    	Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
	    	FileOutputStream b = null;
	    	File file = new File(PHOTO_PATH);
	    	file.mkdirs();// 创建文件夹
	    	long dataTake = System.currentTimeMillis();
	    	uploadFile = PHOTO_PATH + "/" + dataTake+".jpg";
	    	photo = new File(uploadFile);
	    	try {
	    		b = new FileOutputStream(uploadFile);//将数据写入/sdcard/myImage/文件夹下111.jpg中
	    		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
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
	    	iv_userphoto.setImageBitmap(bitmap);
	    	//((ImageView) findViewById(R.id.photo)).setImageBitmap(bitmap);// 将图片显示在ImageView里
  
	      
	    }
	}
 
}
