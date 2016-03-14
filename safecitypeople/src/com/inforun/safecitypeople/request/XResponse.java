package com.inforun.safecitypeople.request;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.inforun.safecitypeople.R;
import com.inforun.safecitypeople.utils.StringUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.util.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 响应处理
 * xiongchaoxi
 */
public class XResponse {
    private ResponseInfo<String> responseInfo;
    private String requestUrl;
    private String result;
    private JSONObject jsonData;
    private int statusCode;
    private Context context;

    private String message;

    private Object entity;



    private String xstring;
    private Boolean xboolean;
    private int xint;
    private long xlong;

    public String getXstring(String key) {
        if (jsonData != null) {
            try {
                xstring = jsonData.getString(key);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                xstring= "";
            }
        }else{
            xstring="";
        }
        return xstring;
    }

    public Boolean getXboolean(String key) {
        if (jsonData != null) {
            try {
                xboolean = jsonData.getBoolean(key);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }
        return xboolean;
    }

    public int getXint(String key) {
        if (jsonData != null) {
            try {
                xint = jsonData.getInt(key);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                xint = -3;
            }
        } else {
            xint = -3;
        }
        return xint;
    }

    public long getXlong(String key) {
        if (jsonData != null) {
            try {
                xlong = jsonData.getLong(key);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                xlong = -3l;
            }
        } else {
            xlong = -3l;
        }
        return xlong;
    }

    private ArrayList<Object> entityList;


    public XResponse(Context context) {
        this.context = context;
    }

    public ResponseInfo<String> getResponseInfo() {
        return responseInfo;
    }

    public void setResponseInfo(ResponseInfo<String> responseInfo) {
        this.responseInfo = responseInfo;
        this.result = responseInfo.result;
        if (TextUtils.isEmpty(result)) {
            return;
        }
        try {
            jsonData = new JSONObject(result);
        } catch (JSONException e) {
            jsonData = null;
            LogUtils.e("JSON " + context.getString(R.string.parse_error));
            Toast.makeText(context, context.getString(R.string.parse_error),
            		Toast.LENGTH_LONG).show();
//            UHelper.showToast(context, context.getString(R.string.parse_error));
            LogUtils.e(e.getMessage(), e);
        }

    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getResult() {
        return result;
    }

    public JSONObject getJsonData() {
        return jsonData;
    }

    private boolean isJsonObj(String json) {
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            return false;
        }
    }

    /**
     * 得到状态
     *
     * @return
     */
    public int getStatusCode() {
        if (jsonData != null) {
            try {
                statusCode = jsonData.getInt("code");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            statusCode = -3;
        }
        return statusCode;
    }

    /**
     * 得到message
     *
     * @return
     */
    public String getMessage() {
        if (null != jsonData) {
            try {
                message = jsonData.getString("message");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (StringUtils.isEmpty(message)) {
            return context.getString(R.string.parse_error);
        } else
            return message;

    }


    /**
     * 得到一个实体对象
     *
     * @return
     */
    public Object getEntity(String key, Class class1) {

        if (!StringUtils.isEmail(result))
            if (isJsonObject(result)) {
                try {
                    entity = new Gson().fromJson(jsonData.getJSONObject(key).toString(), class1);
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        return entity;
    }

    /**
     * 判断是否是Json数据
     *
     * @param result
     * @return
     */
    private boolean isJsonObject(String result) {
        try {
            new JsonParser().parse(result);
            return true;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 得到对象集合List
     *
     * @param <T>
     * @param listType 类型
     * @return
     */
    public <T> List<?> getEntityList(String key,Type listType) {
//	    JsonUtil.getPersonList(result, cls);
        if (!StringUtils.isEmpty(result)) {
            try {
                String string = jsonData.getString(key);
                return new Gson().fromJson(string, listType);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//			new TypeToken<List<ForumVO>>(){}.getType()
        }
        return null;
    }


}
