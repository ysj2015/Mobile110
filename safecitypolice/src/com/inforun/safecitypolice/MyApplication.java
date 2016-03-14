package com.inforun.safecitypolice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.inforun.safecitypolice.activity.LoginActivity;
import com.inforun.safecitypolice.bitmaploader.BitmapHelper;
import com.inforun.safecitypolice.entity.Location;
import com.inforun.safecitypolice.entity.Police;
import com.inforun.safecitypolice.entity.PoliceDetails;
import com.inforun.safecitypolice.finals.Constants;
import com.inforun.safecitypolice.request.XRequestListener;
import com.inforun.safecitypolice.request.XRequestManager;
import com.inforun.safecitypolice.request.XResponse;
import com.inforun.safecitypolice.utils.FileUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.util.LogUtils;
import com.loopj.android.http.PersistentCookieStore;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

/**
 * Application
 *
 * @author xiongchaoxi
 */
public class MyApplication extends Application {
    private LinkedList<Activity> activityList;
//    private SharedPreferences preferences;
//    private SharedPreferences.Editor editor;
    private PersistentCookieStore myCookieStore;
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        
//        myCookieStore = new PersistentCookieStore(this);
        
//        preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        editor = preferences.edit();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(getApplicationContext());

        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        //初始化登录信息
        //initUserInfo();
    }
    
    public PersistentCookieStore getCookieStore() {
    	if(myCookieStore == null) {
    		myCookieStore = new PersistentCookieStore(this);
    	}
    	return myCookieStore;
    }
    /**
     * 获取Activity LinkList
     *
     * @return
     */
    public LinkedList<Activity> getActivityList() {
        if (activityList == null) {
            activityList = new LinkedList<Activity>();
        }
        return activityList;
    }

    /**
     * 添加Activity到 LinkList
     *
     * @param activity
     */
    public void addActivityToList(Activity activity) {
        getActivityList().add(activity);
        LogUtils.d("activityList size: " + getActivityList().size());
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
    /**
     * 从 LinkList移除Activity
     *
     * @param activity
     * @return
     */
    public boolean removeActivityFromList(Activity activity) {
        boolean bool = getActivityList().remove(activity);
        LogUtils.d("activityList size: " + getActivityList().size());
        return bool;
    }

    /**
     * 清空 LinkList
     */
    public void clearActivityList() {
        getActivityList().clear();
    }
    
    /**
     * 应用退出，结束所有的activity
     */
    public void exit(){
        for (Activity activity : activityList) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }
    /**
     * 保存用户信息
     *
     * @param police
     */
//    public void saveLoginUser(Police police) {
//
//        SessionManager.getInstance().setPolice(police);
//        editor.putInt(Constants.ID, police.getId());
//        editor.putInt(Constants.LOGINCOUNT, police.getLoginCount());
//        editor.putBoolean(Constants.ONLINE, police.isOnLine());
//        editor.putString(Constants.POLICENO, police.getPoliceNo());
//        PoliceDetails details=police.getDetails();
//        if (null!=details){
//            editor.putString(Constants.NAME, details.getName());
//            editor.putString(Constants.TEL, details.getTel());
//            editor.putString(Constants.PHOTO,details.getPhoto());
//        }
//
//
//        editor.commit();
//    }

//    /**
//     * 保存sessionId
//     * @param sessionId
//     */
//    public void saveSessionId(String sessionId){
//        editor.putString(Constants.SESSIONID, sessionId);
//        editor.commit();
//    }
//
//    /**
//     * 获取sessionId
//     * @return
//     */
//    public String getSessionId(){
//        return preferences.getString(Constants.SESSIONID,"");
//    }
//    /**
//     * 初始化登录用户信息
//     */
//    public void initUserInfo() {
//        int id = preferences.getInt(Constants.ID, 0);
//        String policeNo = preferences.getString(Constants.POLICENO, "");
//        String name = preferences.getString(Constants.NAME, "");
//        boolean online=preferences.getBoolean(Constants.ONLINE, false);
//        int loginCount=preferences.getInt(Constants.LOGINCOUNT, 0);
//
//        String sex = preferences.getString(Constants.SEX, "");
//        String birthday=preferences.getString(Constants.BIRTHDAY,"");
//        String tel = preferences.getString(Constants.TEL, "");
//        String shenFenId = preferences.getString(Constants.SHEN_FEN_ID, "");
//        String address = preferences.getString(Constants.ADDRESS, "");
//        String photo = preferences.getString(Constants.PHOTO, "");
////        String longitude = preferences.getString(Constants.LONGITUDE, 0 + "");
////        String latitude = preferences.getString(Constants.LATITUDE, 0 + "");
//        if (!TextUtils.isEmpty(policeNo)) {
//            Police police = new Police();
//            police.setId(id);
//            police.setLoginCount(loginCount);
//            police.setPoliceNo(policeNo);
//            police.setOnLine(online);
//
//            PoliceDetails details = new PoliceDetails();
//
//            details.setName(name);
//            details.setSex(sex);
//            details.setTel(tel);
//            details.setShenFenId(shenFenId);
//            details.setAddress(address);
//            details.setPhoto(photo);
//            details.setBirthday(birthday);
//            police.setDetails(details);
////            Location location = new Location();
////            location.setX(longitude);
////            location.setY(latitude);
////            police.setCoord(location);
//            SessionManager.getInstance().setPolice(police);
//        } else {
//        	reLogin(getApplicationContext());
//        }
//        SessionManager.getInstance().setSessionId(getSessionId());
//    }
//
//    /**
//     * 注销用户
//     *
//     * @param relogin 是否弹出登录界面
//     */
//    public void clearUserInfo(boolean relogin) {
//        // clear sharePreference
//        SessionManager.getInstance().setPolice(null);
//        editor.putInt(Constants.ID, 0);
//        editor.putInt(Constants.LOGINCOUNT, 0);
//        editor.putBoolean(Constants.ONLINE, false);
//        editor.putString(Constants.POLICENO, "");
//        editor.putString(Constants.PWD, "");
//        editor.putString(Constants.NAME, "");
////        editor.putInt(Constants.AGE, 0);
//        editor.putString(Constants.SEX, "");
//        editor.putString(Constants.BIRTHDAY, "");
//        editor.putString(Constants.TEL, "");
//        editor.putString(Constants.SHEN_FEN_ID, "");
//        editor.putString(Constants.ADDRESS, "");
//        editor.putString(Constants.PHOTO, "");
//        editor.putString(Constants.LONGITUDE, "");
//        editor.putString(Constants.LATITUDE, "");
//
//        editor.putString(Constants.SESSIONID,"");
//        editor.commit();
//        // 重新登录
//        if (relogin) {
//            reLogin(getApplicationContext());
//        }
//
//    }

    /**
     * 重新登录，清除Activity 栈
     *
     * @param mContext
     */
    public void reLogin(Context mContext) {
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }


}
