package com.inforun.safecitypolice.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.inforun.safecitypolice.BaseActivity;
import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.finals.Constants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class AlarmPictureActivity extends BaseActivity {
	ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_simple_picture);
		String image = getIntent().getExtras().getString("image");
		System.out.println(image);
		iv = (ImageView)findViewById(R.id.big_picture);
		loadImg(Constants.BASE_URL + image,iv);
	}
	private void loadImg(String url,final ImageView image){
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        
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
}
