package com.inforun.safecitypolice;


import android.content.Context;
import android.util.Log;

import com.inforun.safecitypolice.entity.Police;
import com.inforun.safecitypolice.finals.Constants;
import com.inforun.safecitypolice.request.XRequestListener;
import com.inforun.safecitypolice.request.XRequestManager;
import com.inforun.safecitypolice.request.XResponse;
import com.inforun.safecitypolice.utils.StringUtils;
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
    
    private Police police;

    public Police getPolice() {
        return police;
    }

    public void setPolice(Police police) {
        this.police = police;
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
        if (null != getPolice()) {
            loginName = getPolice().getPoliceNo().trim();
        } else {
            return false;
        }
        if (StringUtils.isEmpty(loginName)) {
            return false;
        } else {
            return true;
        }
    }
//    public void refreshPoliceData(){
//    	XRequestManager manager = new XRequestManager(context);
//    	manager.post(Constants.POLICE_GETMYINFO, null, new XRequestListener() {
//	        @Override
//	        public void onRequestStart() {
//	
//	        }
//	        @Override
//	        public void onRequestSuccess(XResponse res) {
//	        	Log.v("police", res.getJsonData().toString());
//	        	Police police = (Police) res.getEntity("police",
//						new Police().getClass());
//	        	SessionManager.getInstance().setPolice(police);
//	        }
//			@Override
//			public void onRequestFailure(HttpException error, String msg) {
//				// TODO Auto-generated method stub
//				
//			}
//    	});
//    }

}
