package com.inforun.safecitypolice.adapter;

import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.finals.Constants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.graphics.Bitmap;

public class AlarmPictureAdapter extends BaseAdapter {
	private Context context;
	private String[] pictures;
	private LayoutInflater inflater;
	public AlarmPictureAdapter(Context context, String[] pictures) {
		this.context = context;
		this.pictures = pictures;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pictures.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return pictures[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		// Inflate the views from XML
		
		View rowView = convertView;
		ViewHolder holder;
		if (rowView == null) {
			rowView = inflater.inflate(R.layout.alarm_picture_item, null);
			holder = new ViewHolder();
			holder.iv = (ImageView)rowView.findViewById(R.id.alarm_img);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		loadImg(Constants.BASE_URL + pictures[position],holder.iv);
		return rowView;
	}
	static class ViewHolder {
		public ImageView iv;
	}
	
	private void loadImg(String url,final ImageView image){
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        
        DisplayImageOptions op = new DisplayImageOptions.Builder()
    	.cacheInMemory(true).cacheOnDisc(true).build();
        ImageLoader.getInstance().loadImage(url, op, new SimpleImageLoadingListener(){
        	@Override
        	public void onLoadingStarted(String imageUri, View view){
        		super.onLoadingStarted(imageUri, view);
        		image.setImageResource(R.drawable.loading);
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
