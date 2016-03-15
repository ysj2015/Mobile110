package com.inforun.safecitypeople;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.inforun.safecitypeople.activity.LoginActivity;
import com.inforun.safecitypeople.entity.User;
import com.inforun.safecitypeople.SessionManager;
import com.inforun.safecitypeople.entity.Coord;
import com.inforun.safecitypeople.entity.Police;
import com.inforun.safecitypeople.entity.PoliceDetails;
import com.inforun.safecitypeople.entity.UserDetails;
import com.lidroid.xutils.util.LogUtils;
import com.loopj.android.http.PersistentCookieStore;

public class App extends Application{

	private LinkedList<Activity> activityList;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private PersistentCookieStore myCookieStore;
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	@Override
	public void onCreate() {
		super.onCreate();
		
		myCookieStore = new PersistentCookieStore(this);		
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(this);
		
		JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        
        //initLocation();
	}
	@Override
	public void onTerminate() {
		
		super.onTerminate();
		System.exit(0);
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
     * 保存用户信息
     *
     * @param police
     */
    public void saveLoginUser(User user) {

        SessionManager.getInstance().setUser(user);
        editor.putInt(Constants.ID, user.getId());
        editor.putBoolean(Constants.ONLINE, user.isOnLine());
        editor.putString(Constants.LOGINNAME, user.getLoginName());
        UserDetails detail = user.getDetails();
	    if (null!=detail){
	    	editor.putString(Constants.NAME, detail.getName());
	    	editor.putString(Constants.BIRTHDAY, formatter.format(detail.getBirthday()));
	      
	    	String sex = new String(new char[]{detail.getSex()});
	    	editor.putString(Constants.SEX, sex);
	    	editor.putString(Constants.TEL, detail.getTel());
	    	editor.putString(Constants.SHEN_FEN_ID, detail.getShenFenId());
	    	editor.putString(Constants.ADDRESS, detail.getAddress());
	    	editor.putString(Constants.PHOTO,detail.getPhoto());
	    }
	    editor.commit();

    }
    
    /**
     * 保存sessionId
     * @param sessionId
     */
    public void saveSessionId(String sessionId){
    	Log.v("result", "sessionId = "+ sessionId);
        editor.putString(Constants.SESSIONID, sessionId);
        editor.commit();
    }

    /**
     * 获取sessionId
     * @return
     */
    public String getSessionId(){
        return preferences.getString(Constants.SESSIONID,"");
    }
    
    /**
     * 初始化登录用户信息
     */
    public void initUserInfo() {
//        int id = preferences.getInt(Constants.ID, 0);
//        String policeNo = preferences.getString(Constants.POLICENO, "");
//        String pwd = preferences.getString(Constants.PWD, "");
//        String name = preferences.getString(Constants.NAME, "");
//        boolean online=preferences.getBoolean(Constants.ONLINE, false);
//        int loginCount=preferences.getInt(Constants.LOGINCOUNT, 0);
////        int age = preferences.getInt(Constants.AGE, 0);
//        String sex = preferences.getString(Constants.SEX, "");
//        String birthday=preferences.getString(Constants.BIRTHDAY,"");
//        String tel = preferences.getString(Constants.TEL, "");
//        String shenFenId = preferences.getString(Constants.SHEN_FEN_ID, "");
//        String address = preferences.getString(Constants.ADDRESS, "");
//        String photo = preferences.getString(Constants.PHOTO, "");
//        String longitude = preferences.getString(Constants.LONGITUDE, 0 + "");
//        String latitude = preferences.getString(Constants.LATITUDE, 0 + "");
//        if (!TextUtils.isEmpty(policeNo)) {
//            Police police = new Police();
//            police.setId(id);
//            police.setLoginCount(loginCount);
//            police.setPoliceNo(policeNo);
//            police.setPwd(pwd);
//            police.setOnLine(online);
//
//            PoliceDetails details=new PoliceDetails();
//
//            details.setName(name);
//            details.setSex(sex);
//            details.setTel(tel);
//            details.setShenFenId(shenFenId);
//            details.setAddress(address);
//            details.setPhoto(photo);
//            details.setBirthday(birthday);
//            police.setDetails(details);
//            Location location = new Location();
//            location.setX(longitude);
//            location.setY(latitude);
//            police.setCoord(location);
//            SessionManager.getInstance().setPolice(police);
//        }
//        SessionManager.getInstance().setSessionId(getSessionId());
    }

    /**
     * 注销用户
     *
     * @param relogin 是否弹出登录界面
     */
    public void clearUserInfo(boolean relogin) {
        // clear sharePreference
//        SessionManager.getInstance().setPolice(null);
//        editor.putInt(Constants.ID, 0);
//        editor.putInt(Constants.LOGINCOUNT, 0);
//        editor.putBoolean(Constants.ONLINE, false);
//        editor.putString(Constants.POLICENO, "");
//        editor.putString(Constants.PWD, "");
//        editor.putString(Constants.NAME, "");
//        editor.putInt(Constants.AGE, 0);
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

    }

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
    
    public void exit(){
        for (Activity activity : activityList) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }

}