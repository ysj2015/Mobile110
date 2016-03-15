package com.inforun.safecitypeople.adapter;

import java.io.FileInputStream;
import java.util.ArrayList;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.inforun.safecitypeople.R;
import com.inforun.safecitypeople.Constants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class AlarmPictureAdapter extends BaseAdapter {
	private String[] pictures;
	private Context context;
	public AlarmPictureAdapter(Context con, String[] pics) {
		pictures = pics;
		context = con;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pictures.length;
	}

	@Override
	public String getItem(int arg0) {
		// TODO Auto-generated method stub
		return pictures[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.gridview_picture_item,
					null);
			holder = new ViewHolder();
			holder.iv = (ImageView)convertView.findViewById(R.id.alarm_picture);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		loadImg(Constants.BASE_URL + pictures[position],holder.iv);
		return convertView;
	}
	private class ViewHolder {
		ImageView iv;
	}

	private void loadImg(String url,final ImageView image){
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        
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
        		Log.v("occasion","finish loading");
        	}
        });
	}
}
