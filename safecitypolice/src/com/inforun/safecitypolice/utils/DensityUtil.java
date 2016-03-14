package com.inforun.safecitypolice.utils;

import android.content.Context;

/**
 * 屏幕分辨率
 *
 * @author 
 */
public class DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕宽度px
     *
     * @param context
     * @return
     */
    public static int deviceWidthPX(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度px
     *
     * @param context
     * @return
     */
    public static int deviceHeightPX(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

}
