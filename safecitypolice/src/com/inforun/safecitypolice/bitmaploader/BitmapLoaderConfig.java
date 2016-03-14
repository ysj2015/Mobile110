package com.inforun.safecitypolice.bitmaploader;

import android.content.Context;

import com.inforun.safecitypolice.R;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;

/**
 * bitmapUtils config
 *     xiongchaoxi
 */
public class BitmapLoaderConfig {
    private static BitmapDisplayConfig configStyle1;
    private static BitmapDisplayConfig configStyle2;

    /**
     * BitmapDisplayConfig 风格1
     *
     * @param context
     * @return
     */
    public static BitmapDisplayConfig configStyle1(Context context) {
        if (configStyle1 == null) {
            configStyle1 = new BitmapDisplayConfig();
            configStyle1.setLoadingDrawable(context.getResources().getDrawable(
                    R.drawable.default_loader));
        }
        return configStyle1;
    }
    /**
     * BitmapDisplayConfig 风格2
     *
     * @param context
     * @return
     */
    public static BitmapDisplayConfig configStyle2(Context context) {
        if (configStyle2 == null) {
            configStyle2 = new BitmapDisplayConfig();
            configStyle2.setLoadFailedDrawable(context.getResources().getDrawable(
            R.drawable.wt_tra));
        }
        return configStyle2;
    }
}
