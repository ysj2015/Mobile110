package com.inforun.safecitypolice.request;

import com.lidroid.xutils.exception.HttpException;

/**
 * 普通请求回调
 *
 * @author xiongchaoxi
 * @param
 */
public interface XRequestListener {
    /**
     * 请求开始
     */
    public void onRequestStart();

    /**
     * 请求成功 statusCode == 200
     *
     * @param res
     */
    public void onRequestSuccess(XResponse res);

    /**
     * 请求失败，请求地址不存在，或者网络错误
     *
     * @param error
     * @param msg
     */
    public void onRequestFailure(HttpException error, String msg);

}
