package com.inforun.safecitypeople.activity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.inforun.safecitypeople.R;
import com.inforun.safecitypeople.camera.CameraInterface;
import com.inforun.safecitypeople.camera.CameraInterface.CamOpenOverCallback;
import com.inforun.safecitypeople.camera.CameraOpenOverCallback;
import com.inforun.safecitypeople.camera.preview.CameraSurfaceView;
import com.inforun.safecitypeople.utils.CamParaUtil;
import com.inforun.safecitypeople.utils.DisplayUtil;
import com.inforun.safecitypeople.utils.FileUtil;
import com.inforun.safecitypeople.utils.ImageUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;

public class AlarmCameraActivity extends Activity implements CameraOpenOverCallback,OnClickListener{
	private static final String TAG = "AlarmCameraActivity";
	CameraSurfaceView surfaceView = null;
	ImageButton shutterBtn;

	private Camera mCamera;
	private Camera.Parameters mParams;
	private boolean isPreviewing = false;
	private float mPreviwRate = -1f;
	float previewRate = -1f;
	
	private static final File parentPath = Environment.getExternalStorageDirectory();
	private static String storagePath = "";
	private static final String DST_FOLDER_NAME = "PlayCamera";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread openThread = new Thread(){
			@Override
			public void run() {
				doOpenCamera();
			}
		};
		openThread.start();
		setContentView(R.layout.activity_camera);
		initUI();
		initViewParams();
		
		shutterBtn.setOnClickListener(this);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btn_shutter:
			doTakePicture();
			break;
		default:
			break;
		}
	}
	/**打开Camera
	 * @param callback
	 */
	public void doOpenCamera(){
		Log.i(TAG, "Camera open....");
		mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
		Log.i(TAG, "Camera open over....");
		cameraHasOpened();
	}
	@Override
	public void cameraHasOpened() {
		// TODO Auto-generated method stub
		SurfaceHolder holder = surfaceView.getSurfaceHolder();
		doStartPreview(holder, previewRate);
	}
	/**开启预览
	 * @param holder
	 * @param previewRate
	 */
	public void doStartPreview(SurfaceHolder holder, float previewRate){
		Log.i(TAG, "doStartPreview...");
		if(isPreviewing){
			mCamera.stopPreview();
			return;
		}
		if(mCamera != null){

			mParams = mCamera.getParameters();
			mParams.setPictureFormat(PixelFormat.JPEG);//设置拍照后存储的图片格式
			CamParaUtil.getInstance().printSupportPictureSize(mParams);
			CamParaUtil.getInstance().printSupportPreviewSize(mParams);
			//设置PreviewSize和PictureSize
			Size pictureSize = CamParaUtil.getInstance().getPropPictureSize(
					mParams.getSupportedPictureSizes(),previewRate, 800);
			mParams.setPictureSize(pictureSize.width, pictureSize.height);
			Size previewSize = CamParaUtil.getInstance().getPropPreviewSize(
					mParams.getSupportedPreviewSizes(), previewRate, 800);
			mParams.setPreviewSize(previewSize.width, previewSize.height);

			mCamera.setDisplayOrientation(90);

			CamParaUtil.getInstance().printSupportFocusMode(mParams);
			List<String> focusModes = mParams.getSupportedFocusModes();
			if(focusModes.contains("continuous-video")){
				mParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
			}
			mCamera.setParameters(mParams);	

			try {
				mCamera.setPreviewDisplay(holder);
				mCamera.startPreview();//开启预览
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			isPreviewing = true;
			mPreviwRate = previewRate;

			mParams = mCamera.getParameters(); //重新get一次
			Log.i(TAG, "最终设置:PreviewSize--With = " + mParams.getPreviewSize().width
					+ "Height = " + mParams.getPreviewSize().height);
			Log.i(TAG, "最终设置:PictureSize--With = " + mParams.getPictureSize().width
					+ "Height = " + mParams.getPictureSize().height);
		}
	}
	/**
	 * 拍照
	 */
	public void doTakePicture(){
		if(isPreviewing && (mCamera != null)){
			mCamera.takePicture(mShutterCallback, null, mJpegPictureCallback);
		}
	}
	/**
	 * 停止预览，释放Camera
	 */
	public void doStopCamera(){
		if(null != mCamera)
		{
			mCamera.setPreviewCallback(null);
			mCamera.stopPreview(); 
			isPreviewing = false; 
			mPreviwRate = -1f;
			mCamera.release();
			mCamera = null;     
		}
	}
	/*为了实现拍照的快门声音及拍照保存照片需要下面三个回调变量*/
	ShutterCallback mShutterCallback = new ShutterCallback() 
	//快门按下的回调，在这里我们可以设置类似播放“咔嚓”声之类的操作。默认的就是咔嚓。
	{
		public void onShutter() {
			// TODO Auto-generated method stub
			Log.i(TAG, "myShutterCallback:onShutter...");
		}
	};
	PictureCallback mRawCallback = new PictureCallback() 
	// 拍摄的未压缩原数据的回调,可以为null
	{

		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			Log.i(TAG, "myRawCallback:onPictureTaken...");

		}
	};
	PictureCallback mJpegPictureCallback = new PictureCallback() 
	//对jpeg图像数据的回调,最重要的一个回调
	{
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			Log.i(TAG, "myJpegCallback:onPictureTaken...");
			Bitmap b = null;
			if(null != data){
				b = BitmapFactory.decodeByteArray(data, 0, data.length);//data是字节数据，将其解析成位图
				mCamera.stopPreview();
				isPreviewing = false;
			}
			//保存图片到sdcard
			if(null != b)
			{
				//设置FOCUS_MODE_CONTINUOUS_VIDEO)之后，myParam.set("rotation", 90)失效。
				//图片竟然不能旋转了，故这里要旋转下
				Bitmap rotaBitmap = ImageUtil.getRotateBitmap(b, 90.0f);
				saveBitmap(rotaBitmap);
//				FileUtil fu = new FileUtil(mContext);
//				fu.saveBitmap(rotaBitmap);
			}
			//再次进入预览
			mCamera.startPreview();
			isPreviewing = true;
		}
	};
	/**初始化保存路径
	 * @return
	 */
	private static String initPath(){
		if(storagePath.equals("")){
			storagePath = parentPath.getAbsolutePath()+"/" + DST_FOLDER_NAME;
			File f = new File(storagePath);
			if(!f.exists()){
				f.mkdir();
			}
		}
		return storagePath;
	}

	/**保存Bitmap到sdcard
	 * @param b
	 */
	public void saveBitmap(Bitmap b){

		String path = initPath();
		long dataTake = System.currentTimeMillis();
		String jpegName = path + "/" + dataTake +".jpg";
		Log.i(TAG, "saveBitmap:jpegName = " + jpegName);
		try {
			FileOutputStream fout = new FileOutputStream(jpegName);
			BufferedOutputStream bos = new BufferedOutputStream(fout);
			b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
			Log.i(TAG, "saveBitmap成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i(TAG, "saveBitmap:失败");
			e.printStackTrace();
		}

	}
	static final class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
		private static final String TAG = "yanzi";
		CameraInterface mCameraInterface;
		Context mContext;
		SurfaceHolder mSurfaceHolder;
		public CameraSurfaceView(Context context, AttributeSet attrs) {
			super(context, attrs);
			// TODO Auto-generated constructor stub
			mContext = context;
			mSurfaceHolder = getHolder();
			mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);//translucent半透明 transparent透明
			mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			mSurfaceHolder.addCallback(this);
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			Log.i(TAG, "surfaceCreated...");
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub
			Log.i(TAG, "surfaceChanged...");
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			Log.i(TAG, "surfaceDestroyed...");
			//doStopCamera();
		}
		public SurfaceHolder getSurfaceHolder(){
			return mSurfaceHolder;
		}
		
	}

}
