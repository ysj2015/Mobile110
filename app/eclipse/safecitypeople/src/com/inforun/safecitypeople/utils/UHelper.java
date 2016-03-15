package com.inforun.safecitypeople.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;

import java.util.HashMap;
import java.util.List;


public class UHelper {

    public static void showToast(Context context, int id) {
        Toast.makeText(context, context.getString(id), Toast.LENGTH_SHORT)
                .show();
    }


    public static void showToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    /**
     * 当前运行App
     *
     * @param context
     * @return
     */
    public static boolean appIsTop(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = activityManager.getRunningTasks(100);
        if (list != null && list.size() != 0) {
            if (list.get(0).topActivity.getPackageName().equals(
                    context.getPackageName())) {
                return true;
            }
        }
        return false;
    }



    /**
     * 判断Activity是否处于顶层
     *
     * @param context
     * @return
     */
    public static boolean uiActivityIsTop(Context context,Class<?> cla) {
        boolean isTop = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = activityManager.getRunningTasks(100);
        if (list != null && list.size() != 0) {
            String className = list.get(0).topActivity.getClassName();
            if (className.equals(cla.getName())) {
                isTop = true;
            }
        }
        return isTop;
    }

    /**
     * 获取设备信息
     *
     * @return
     */
    public static HashMap<String, String> getSInfo(Context mContext) {
        HashMap<String, String> sInfomap = new HashMap<String, String>();
        sInfomap.put("app_version", "" + getVersionCode(mContext));
        sInfomap.put("system_version", "android SDK:" + Build.VERSION.SDK_INT);
        sInfomap.put("device_type", "2");//1:ios,2:android
        sInfomap.put("device_info", Build.MODEL);
        return sInfomap;
    }

    /**
     * 获取app versionCode
     *
     * @return
     */
    public static int getVersionCode(Context mContext) {
        // 获取packagemanager的实例
        PackageManager packageManager = mContext.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(mContext.getPackageName(), PackageManager.GET_SERVICES);
            int versionCode = packInfo.versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e(e.getMessage(), e);
        }
        return 0;
    }

    /**
     * 获取app versionCode
     *
     * @return
     */
    public static String getVersionName(Context mContext) {
        // 获取packagemanager的实例
        PackageManager packageManager = mContext.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(mContext.getPackageName(), PackageManager.GET_SERVICES);
            String versionCode = packInfo.versionName;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e(e.getMessage(), e);
        }
        return "";
    }
}
