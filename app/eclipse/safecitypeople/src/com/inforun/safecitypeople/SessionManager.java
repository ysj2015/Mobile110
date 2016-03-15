package com.inforun.safecitypeople;


import java.util.HashMap;

import android.content.Context;
import android.util.Log;

//import com.inforun.safecitypeople.activity.DatumActivity;
import com.inforun.safecitypeople.entity.Police;
import com.inforun.safecitypeople.entity.User;
import com.inforun.safecitypeople.Constants;
import com.inforun.safecitypeople.request.XRequestListener;
import com.inforun.safecitypeople.request.XRequestManager;
import com.inforun.safecitypeople.request.XResponse;
import com.inforun.safecitypeople.utils.StringUtils;
import com.lidroid.xutils.exception.HttpException;

import org.apache.http.client.CookieStore;

/**
 * 单例 session
 *
 * @author xiongchaoxi
 */
public class SessionManager {
    private static SessionManager sessionManager;
    private String diskCachePath;
    private Context context;
    
    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    private CookieStore cookieStore;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    private String sessionId;
    
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User u) {
        this.user = u;
    }

    public static SessionManager getInstance() {
        if (sessionManager == null) {
            sessionManager = new SessionManager();
        }
        return sessionManager;
    }

    public String getDiskCachePath() {
        return diskCachePath;
    }

    public void setDiskCachePath(String diskCachePath) {
        this.diskCachePath = diskCachePath;
    }

    public Context getContext() {
    	return context;
    }
    
    public void setContext(Context con) {
    	context = con;
    }
    /**
     * 判断是否已经登录
     *
     * @return
     */
    public boolean isLogin() {
        String loginName = "";
        if (null != getUser()) {
            loginName = getUser().getLoginName().trim();
        } else {
            return false;
        }
        if (StringUtils.isEmpty(loginName)) {
            return false;
        } else {
            return true;
        }
    }
    public void refreshUserData(){
    	final XRequestManager manager = new XRequestManager(context);
    	HashMap<String, Object> map = new HashMap<String, Object>(); 
    	manager.post(Constants.GET_USER_INFO, map, new XRequestListener() {
	        @Override
	        public void onRequestStart() {
	        	manager.setProgressMessage("加载中...");
	        }
	        @Override
	        public void onRequestSuccess(XResponse res) {
	        	User user = (User) res.getEntity("user",
						new User().getClass());
	        	System.out.println(user.getDetails().getName());
	        	System.out.println(user.getDetails().getShenFenId());
	        	SessionManager.getInstance().setUser(user);
	        }
			@Override
			public void onRequestFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				
			}
    	});
    }

}
