package com.inforun.safecitypolice.bitmaploader;

import android.content.Context;

import com.inforun.safecitypolice.SessionManager;
import com.lidroid.xutils.BitmapUtils;


/**
 * 单例: 获取图片加载实例
 *   xiongchaoxi
 */
public class BitmapHelper {
    private static BitmapUtils bitmapUtils;
    private static BitmapUtils bitmapUtils2;//图片无缓存
    private static Context mContext;

    /**
     * 请在Application 中調用该方法
     *
     * @param context
     */
    public static void init(Context context) {
        mContext = context;
    }

    public static BitmapUtils getBitmapUtils() {
        if (bitmapUtils == null) {
            bitmapUtils = new BitmapUtils(mContext, SessionManager
                    .getInstance().getDiskCachePath());
            bitmapUtils.configMemoryCacheEnabled(true);
            bitmapUtils.configDefaultShowOriginal(false);
            bitmapUtils.configDiskCacheEnabled(true);
        }

        return bitmapUtils;
    }

    public static BitmapUtils getBitmapUtilsWithOutMemory() {
        if (bitmapUtils2 == null) {
            bitmapUtils2 = new BitmapUtils(mContext, SessionManager
                    .getInstance().getDiskCachePath());
            bitmapUtils2.configMemoryCacheEnabled(false);

        }
        return bitmapUtils2;
    }


}
