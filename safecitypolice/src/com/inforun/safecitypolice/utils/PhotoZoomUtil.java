package com.inforun.safecitypolice.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;


import com.inforun.safecitypolice.SessionManager;

import java.io.File;


public class PhotoZoomUtil {
	/**
	 * 保存裁剪之后的图片数据
	 */
	public static Drawable setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			@SuppressWarnings("deprecation")
			Drawable drawable = new BitmapDrawable(photo);
			return drawable;
		}
		// 否则返回默认图片
		return null;
	}

	/**
	 * 裁剪图片方法实现
	 */
	public static Intent startPhotoZoom(Uri uri,String path) {
		File customBgImage=new File(path);
		Intent intents = new Intent("com.android.camera.action.CROP");
		intents.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intents.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intents.putExtra("aspectX", 1);
		intents.putExtra("aspectY", 1);
		intents.putExtra("scale", true);
		// outputX outputY 是裁剪图片宽高
		 intents.putExtra("outputX", 150);
		 intents.putExtra("outputY", 150);
		 intents.putExtra("noFaceDetection", true);
		 intents.putExtra("output", Uri.fromFile(customBgImage));// 输出到文件
		intents.putExtra("return-data", true);
		return intents;
	}

	

	/**
	 * 照相
	 */
	public static Intent takePhotos(String path,String fileName) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 下面这句指定调用相机拍照后的照片存储的路径 首先判断是否可用
		// if (FileTool.SDCardIsExsit()) {
		FileUtils.createDirectory(SessionManager.getInstance().getDiskCachePath());
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
				path, fileName)));
		return intent;
		// } else {
		// Toast.makeText(this, "sd卡不可用！", 500).show();
		// }
	}

	/**
	 * 选择系统图册
	 */
	public static Intent selectDICM() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		/**
		 * 下面这句话，与其它方式写是一样的效果，如果： intent.setData(MediaStore.Images
		 * .Media.EXTERNAL_CONTENT_URI); intent.setType(""image/*");设置数据类型
		 * 如果要限制上传到服务器的图片类型时可以直接写如 ："image/jpeg 、 image/png等的类型"
		 */
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		return intent;
	}
}
