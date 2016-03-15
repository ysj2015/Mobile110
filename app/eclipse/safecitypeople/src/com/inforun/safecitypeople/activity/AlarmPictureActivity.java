package com.inforun.safecitypeople.activity;

import java.io.FileInputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.inforun.safecitypeople.BaseActivity;
import com.inforun.safecitypeople.R;

public class AlarmPictureActivity extends BaseActivity {
	ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_simple_picture);
		String path = getIntent().getExtras().getString("picture");
		iv = (ImageView)findViewById(R.id.big_picture);
		iv.setImageBitmap(showLocalPicture(path));
		
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
}
