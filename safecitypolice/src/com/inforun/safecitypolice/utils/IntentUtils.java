package com.inforun.safecitypolice.utils;

/**
 * Created by wutong on 2014/5/5.
 *
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;

/**
 * Android IntentUtils
 */
public final class IntentUtils {
    public static final String JPEG_MIME_TYPE = "image/jpeg";
    /**
     * Used to tag logs
     */
    @SuppressWarnings("unused")
    private static final String TAG = "IntentUtils";

    //------------------------------------------------------
    // Private constructor for utility class
    private IntentUtils() {
        throw new UnsupportedOperationException("Sorry, you cannot instantiate an utility class!");
    }
    //------------------------------------------------------

    /**
     * 分享Intent
     *
     * @param url
     * @param subject
     * @return
     */
    public static Intent buildShareUrlIntent(String url, String subject) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
        if (subject != null) {
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        shareIntent.setType("text/plain");
        return shareIntent;
    }

    /**
     * 分享图片Intent
     *
     * @param context
     * @param photoFile
     * @param title
     * @param text
     * @return
     */
    public static Intent buildSharePhotoIntent(Context context, File photoFile, String title, String text) {
        Intent shareIntent = null;
        if (photoFile.exists()) {
            shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType(JPEG_MIME_TYPE);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));

            // Subject and Text are used by the Gmail app
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, title);
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);

            // Title is used by the Facebook app (TODO: doesn't seams to work in latest version of the Facebook app)
            shareIntent.putExtra(Intent.EXTRA_TITLE, title);
        }
        return shareIntent;
    }

    /**
     * 拍照Intent
     *
     * @param path 图片保存路径
     * @return
     */
    public static Intent buildTakePhotoIntent(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Intent intentTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(path);
        if (!file.exists()) {
            String dirPath = path.substring(0, path.lastIndexOf("/"));
            File dir = new File(dirPath);
            dir.mkdir();
        }
        // 把文件地址转换成Uri格式
        Uri uri = Uri.fromFile(file);
        // 设置系统相机拍摄照片完成后图片文件的存放地址
        intentTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intentTakePhoto;
    }

    /**
     * 是否为拍照的Intent
     *
     * @param intent
     * @return
     */
    public static boolean isTakePhotoIntent(Intent intent) {
        String action = intent.getAction();
        return (action != null
                && (MediaStore.ACTION_IMAGE_CAPTURE.equals(action)
                || MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA.equals(action))
                && intent.hasExtra(MediaStore.EXTRA_OUTPUT));

    }

    /**
     * 从相册获取图片Intent
     *
     * @return
     */
    public static Intent buildPickPhotoIntent() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickPhotoIntent.addCategory(Intent.CATEGORY_OPENABLE);
        pickPhotoIntent.setType("image/*");
        return pickPhotoIntent;
    }

    /**
     * 打开网页 Intent
     *
     * @param context
     * @param url
     * @return
     */
    public static Intent buildWebIntent(Context context, String url) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    }


}
