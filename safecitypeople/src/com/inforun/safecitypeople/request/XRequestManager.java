package com.inforun.safecitypeople.request;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.inforun.safecitypeople.App;
import com.inforun.safecitypeople.R;
import com.inforun.safecitypeople.SessionManager;
import com.inforun.safecitypeople.utils.UHelper;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.util.PreferencesCookieStore;

import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 单例：网络请求Manager
 *
 * @author xiong
 */
public class XRequestManager {
    private static XRequestManager requestManager;
    Context context;
    ProgressDialog progressDialog;
    private HttpUtils httpUtils;


    private boolean isLoin = false;

    public void setIsLoin(boolean isLoin) {
        this.isLoin = isLoin;
    }

    private boolean isProgressDialog = true;

    private String progressMessage = "正在处理，请稍后...";

    public void setProgressMessage(String progressMessage) {
        this.progressMessage = progressMessage;
    }

    public void setProgressDialog(boolean isProgressDialog) {
        this.isProgressDialog = isProgressDialog;
    }

    public XRequestManager(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    private XRequestManager manager;

    public XRequestManager getRequestManager() {
        if (null == manager) {
            manager = new XRequestManager(context);
        }
        return requestManager;
    }

    private HttpUtils getHttpUtils() {
        if (httpUtils == null) {
            httpUtils = new HttpUtils();
            httpUtils.configTimeout(1000 * 20);
            httpUtils.configSoTimeout(1000 * 20);
            httpUtils.configRequestThreadPoolSize(5);
            if (!isLoin) {
                CookieStore cookieStore = SessionManager.getInstance().getCookieStore();
                httpUtils.configCookieStore(cookieStore);
            }
        }
        return httpUtils;
    }

    /**
     * 显示提示dialong
     */
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(null);
        progressDialog.setMessage(progressMessage);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * 关闭dialog
     */
    public void dismissProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * post方式（除文件）
     *
     * @param url      地址
     * @param listener 回调函数
     */
    public void post(String url, HashMap<String, Object> map,
                     final XRequestListener listener) {
        RequestParams params = new RequestParams();
        if (map != null) {
            params = new RequestParams();
            for (String key : map.keySet()) {
                params.addBodyParameter(new BasicNameValuePair(key, String
                        .valueOf(map.get(key))));
            }
        }
        request(HttpMethod.POST, url, listener, params);
    }
    public void post(String url, RequestParams params, XRequestListener listener) {
    	request(HttpMethod.POST, url, listener, params);
    }
    /**
     * post方式（上传文件）
     *
     * @param url      地址
     * @param listener 回调函数
     */
    public void postFile(String url, HashMap<String, Object> map,
                         final XRequestListener listener) {
        RequestParams params = new RequestParams();
        // params.addHeader("name", "value");
        // params.addQueryStringParameter("name", "value");

        // 只包含字符串参数时默认使用BodyParamsEntity，
        // 类似于UrlEncodedFormEntity（"application/x-www-form-urlencoded"）。
        // params.addBodyParameter("name", "value");

        // 加入文件参数后默认使用MultipartEntity（"multipart/form-data"），
        // 如需"multipart/related"，xUtils中提供的MultipartEntity支持设置subType为"related"。
        // 使用params.setBodyEntity(httpEntity)可设置更多类型的HttpEntity（如：
        // MultipartEntity,BodyParamsEntity,FileUploadEntity,InputStreamUploadEntity,StringEntity）。
        // 例如发送json参数：params.setBodyEntity(new StringEntity(jsonStr,charset));
        if (map != null) {
            params = new RequestParams();
            for (String key : map.keySet()) {
                params.addBodyParameter(key,
                        new File(String.valueOf(map.get(key))));
            }
        }

        request(HttpMethod.POST, url, listener, params);
    }

    /**
     * get方式
     *
     * @param url      地址
     * @param listener 回调函数
     */
    public void get(String url, final XRequestListener listener) {
        request(HttpMethod.GET, url, listener, null);
    }

    /**
     * 请求
     *
     * @param method   访问方式
     * @param url      地址
     * @param listener 回调
     * @param params   参数
     */
    private void request(final HttpMethod method, final String url,
                         final XRequestListener listener, final RequestParams params) {
        getHttpUtils().send(method, url, params, new RequestCallBack<String>() {


            @Override
            public void onStart() {
                // TODO Auto-generated method stub
                printfRequestParams(method.toString(), url, params);

                if (null != listener) {
                    listener.onRequestStart();
                    if (isProgressDialog) {
                        showProgressDialog();
                    }
                }
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                // TODO Auto-generated method stub
                if (null != listener) {
                    listener.onRequestFailure(arg0, arg1);
                }
                if (arg0.getExceptionCode()==0) {
                	UHelper.showToast(context, context.getString(R.string.net_error));
				}else{
					UHelper.showToast(context, context.getString(R.string.server_error));
				}
                LogUtils.e(arg1);
                Log.e("sssssssssssss", arg1);
                Log.e("sssssssssssss", arg0.getExceptionCode() + "fffff");
                if (isProgressDialog) {
                    dismissProgressDialog();
                }
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                // TODO Auto-generated method stub
            	 LogUtils.i(arg0.result+"");
            	if (isProgressDialog) {
                    dismissProgressDialog();
                }
                XResponse response = new XResponse(context);
                response.setResponseInfo(arg0);
                if (!isLoin) {
                   if (response.getStatusCode()==-1){
                       ((App) context.getApplicationContext()).reLogin(context);
                       return;
                   }
                }
                if (isLoin) {
                    checkSession();
                }
                if (null != listener) {
                    listener.onRequestSuccess(response);
                }
            }
        });
    }

    /**
     * 比对SessionId
     */
    private boolean checkSession() {
        // 取得sessionid.........................
        DefaultHttpClient dh = (DefaultHttpClient) httpUtils.getHttpClient();
        CookieStore cs = dh.getCookieStore();
        List<Cookie> cookies = cs.getCookies();
        String aa = null;
        for (int i = 0; i < cookies.size(); i++) {
            if ("JSESSIONID".equals(cookies.get(i).getName())) {
                aa = cookies.get(i).getValue();
                System.out.println(aa);
                break;
            }
        }

        SessionManager.getInstance().setSessionId(aa);
        SessionManager.getInstance().setCookieStore(cs);
        ((App) context.getApplicationContext()).saveSessionId(aa);
        return true;
    }
    public void getSessionId() {
    	DefaultHttpClient dh = (DefaultHttpClient) httpUtils.getHttpClient();
        CookieStore cs = dh.getCookieStore();
        List<Cookie> cookies = cs.getCookies();
        String aa = null;
        for (int i = 0; i < cookies.size(); i++) {
            if ("JSESSIONID".equals(cookies.get(i).getName())) {
                aa = cookies.get(i).getValue();
                System.out.println(aa);
                break;
            }
        }
    }
//        String sessionId = SessionManager.getInstance().getSessionId();
//    if (sessionId.equals(aa)) {
//
//        return true;
//    }else{
//        ((MyApplication) context.getApplicationContext()).clearUserInfo(true);
//        return false;
//    }false


    /**
     * 打印请求参数
     *
     * @param url
     * @param params
     */
    private void printfRequestParams(String method, String url,
                                     RequestParams params) {

        LogUtils.i(" ");
        LogUtils.d("**************************************");
        LogUtils.d(method + "  Request URL: " + url);
        if (params != null) {
            ArrayList<NameValuePair> list = (ArrayList<NameValuePair>) params
                    .getQueryStringParams();
            if (list != null) {
                LogUtils.d("Request Params: ");
                for (int i = 0; i < list.size(); i++) {
                    NameValuePair pair = list.get(i);
                    LogUtils.d(pair.getName() + "  :  " + pair.getValue());
                }
            } else {
                LogUtils.d("no request params");
            }
        } else {
            LogUtils.d("no request params");
        }
        LogUtils.d("**************************************");
        LogUtils.i(" ");
    }

    /**
     * 打印请求参数
     *
     * @param url
     * @param map
     */
    private void printfRequestParams(String method, String url,
                                     HashMap<String, Object> map) {

        LogUtils.i(" ");
        LogUtils.d("**************************************");
        LogUtils.d(method + "  Request URL: " + url);
        if (map != null) {
            LogUtils.d("Request Params: ");
            for (String key : map.keySet()) {
                LogUtils.d(key + "  :  " + String.valueOf(map.get(key)));
            }
        } else {
            LogUtils.d("no request params");
        }
        LogUtils.d("**************************************");
        LogUtils.i(" ");
    }

    /**
     * 设置User—Agent
     *
     * @param sInfo
     */
    public void setUserAgent(HashMap<String, String> sInfo) {
        String userAgent = "";
        if (sInfo != null) {
            JSONObject object = new JSONObject(sInfo);
            userAgent = object.toString();
        }
        getHttpUtils().configUserAgent(userAgent);
    }
}
