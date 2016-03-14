package com.inforun.safecitypolice.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;


/**
 * Created by wutong on 2014/5/5.
 * URI 工具类
 */
public class URIUtils {

    /**
     * 根据Uri获取选中的图片地址
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getPickPhotoPath(Context context, Uri uri) {
        if (uri == null) {
            return "";
        }
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        final String pickPath = cursor.getString(columnIndex);
        cursor.close();
        return pickPath;
    }

}
