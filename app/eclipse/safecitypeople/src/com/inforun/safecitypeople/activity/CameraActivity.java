package com.inforun.safecitypeople.activity;

import com.inforun.safecitypeople.camera.CameraInterface;
import com.inforun.safecitypeople.camera.CameraInterface.CamOpenOverCallback;
import com.inforun.safecitypeople.camera.preview.CameraSurfaceView;
import com.inforun.safecitypeople.R;
import com.inforun.safecitypeople.utils.DisplayUtil;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;

public class CameraActivity extends Activity implements CamOpenOverCallback {
	private static final String TAG = "yanzi";
	CameraSurfaceView surfaceView = null;
	ImageButton shutterBtn;
	float previewRate = -1f;
	BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			Log.i(TAG,"onReceive");
			Bundle bundle = arg1.getExtras(); 
            if (bundle != null){ 
            	String photo = bundle.getString("photoPath");
            	Log.i(TAG,photo);
            	Intent in = new Intent();
            	in.putExtra("photo", photo);
            	CameraActivity.this.setResult(RESULT_OK, in);
            	CameraActivity.this.finish();
            }
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread openThread = new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				CameraInterface.getInstance().doOpenCamera(CameraActivity.this);
			}
		};
		openThread.start();
		setContentView(R.layout.activity_camera);
		initUI();
		initViewParams();
		
		shutterBtn.setOnClickListener(new BtnListeners());
	}
	@Override
	public void onResume() {
		IntentFilter filter = new IntentFilter();
		filter.addAction("SaveBitmap");
		registerReceiver(receiver,filter);
		Log.i(TAG,"registerReceiver");
		super.onResume();
	}
	@Override
	public void onPause() {
		unregisterReceiver(receiver);
		Log.i(TAG,"unregisterReceiver");
		super.onPause();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}

	private void initUI(){
		surfaceView = (CameraSurfaceView)findViewById(R.id.camera_surfaceview);
		shutterBtn = (ImageButton)findViewById(R.id.btn_shutter);
	}
	private void initViewParams(){
		LayoutParams params = surfaceView.getLayoutParams();
		Point p = DisplayUtil.getScreenMetrics(this);
		params.width = p.x;
		params.height = p.y;
		previewRate = DisplayUtil.getScreenRate(this); //默认全屏的比例预览
		surfaceView.setLayoutParams(params);

		//手动设置拍照ImageButton的大小为120dip×120dip,原图片大小是64×64
		LayoutParams p2 = shutterBtn.getLayoutParams();
		p2.width = DisplayUtil.dip2px(this, 80);
		p2.height = DisplayUtil.dip2px(this, 80);;		
		shutterBtn.setLayoutParams(p2);	

	}

	@Override
	public void cameraHasOpened() {
		// TODO Auto-generated method stub
		SurfaceHolder holder = surfaceView.getSurfaceHolder();
		CameraInterface.getInstance().doStartPreview(holder, previewRate);
	}
	private class BtnListeners implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.btn_shutter:
				CameraInterface.getInstance().setContext(CameraActivity.this);
				CameraInterface.getInstance().doTakePicture();
				break;
			default:
				break;
			}
		}

	}

}
