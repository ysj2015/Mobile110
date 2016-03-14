package com.inforun.safecitypolice.other;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.bitmaploader.BitmapHelper;
import com.inforun.safecitypolice.other.photoview.PhotoViewAttacher;
import com.inforun.safecitypolice.other.photoview.PhotoViewAttacher.OnPhotoTapListener;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

/**
 * 单张图片显示Fragment x5
 */
public class ImageDetailFragment extends Fragment {
	private String mImageUrl;
	private ImageView mImageView;
	private ProgressBar progressBar;
	private PhotoViewAttacher mAttacher;

	public static ImageDetailFragment newInstance(String imageUrl) {
		final ImageDetailFragment f = new ImageDetailFragment();

		final Bundle args = new Bundle();
		args.putString("url", imageUrl);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageUrl = getArguments() != null ? getArguments().getString("url")
				: null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.image_detail_fragment,
				container, false);
		mImageView = (ImageView) v.findViewById(R.id.image);
		mAttacher = new PhotoViewAttacher(mImageView);

		mAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {

			@Override
			public void onPhotoTap(View arg0, float arg1, float arg2) {
				getActivity().finish();
			}
		});

		progressBar = (ProgressBar) v.findViewById(R.id.loading);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));
        
        DisplayImageOptions op = new DisplayImageOptions.Builder()
    	.cacheInMemory(true).cacheOnDisc(true).build();
        ImageLoader.getInstance().loadImage(mImageUrl, op, new SimpleImageLoadingListener(){
        	@Override
        	public void onLoadingStarted(String imageUri, View view){
        		super.onLoadingStarted(imageUri, view);
        	}
        	@Override
        	public void onLoadingComplete(String imageUri, View view, Bitmap bmp){
        		super.onLoadingComplete(imageUri, view, bmp);
        		mImageView.setImageBitmap(bmp);
        		Log.v("occasion","finish loading");
        	}
        });
		
//		BitmapHelper.getBitmapUtils().display(mImageView, mImageUrl,new BitmapLoadCallBack<View>() {
//
//			@Override
//			public void onLoadCompleted(View arg0, String arg1, Bitmap arg2,
//					BitmapDisplayConfig arg3, BitmapLoadFrom arg4) {
//				// TODO Auto-generated method stub
//				
//				progressBar.setVisibility(View.GONE);
//				mImageView.setImageBitmap(arg2);
//				mAttacher.update();
//				
//			}
//
//			@Override
//			public void onLoadFailed(View arg0, String arg1, Drawable arg2) {
//				// TODO Auto-generated method stub
//				
//				Toast.makeText(getActivity(), "下载失败！", Toast.LENGTH_SHORT).show();
//				progressBar.setVisibility(View.GONE);
//			}
//		});
//		ImageLoader.getInstance().displayImage(mImageUrl, mImageView, new SimpleImageLoadingListener() {
//			@Override
//			public void onLoadingStarted(String imageUri, View view) {
//				progressBar.setVisibility(View.VISIBLE);
//			}
//
//			@Override
//			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//				String message = null;
//				switch (failReason.getType()) {
//				case IO_ERROR:
//					message = "下载错误";
//					break;
//				case DECODING_ERROR:
//					message = "图片无法显示";
//					break;
//				case NETWORK_DENIED:
//					message = "网络有问题，无法下载";
//					break;
//				case OUT_OF_MEMORY:
//					message = "图片太大无法显示";
//					break;
//				case UNKNOWN:
//					message = "未知的错误";
//					break;
//				}
//				Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//				progressBar.setVisibility(View.GONE);
//			}
//
//			@Override
//			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//				progressBar.setVisibility(View.GONE);
//				mAttacher.update();
//			}
//			
//		});
	}
}
