package com.vb.appmvp.net;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.subscribers.ResourceSubscriber;


/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-08-15
 */

public abstract class ApiSubscriber<T extends IModel> extends ResourceSubscriber<T> {


    @Override
    public void onError(Throwable e) {
        NetError error = null;
        e.printStackTrace();
        if (e != null) {
            if (!(e instanceof NetError)) {
                e.printStackTrace();
                if (e instanceof UnknownHostException) {

                    error = new NetError("无法连接到服务器,请检查网络", NetError.NoConnectError);
                }else if(e instanceof ConnectException)
                {
                    error = new NetError("连接异常,请检查网络", NetError.NoConnectError);
                }else if(e instanceof retrofit2.adapter.rxjava2.HttpException)
                {
                    error=new NetError("资源不存在",NetError.NoDataError);
                }
                else if (e instanceof JSONException
                        || e instanceof JsonParseException
                        || e instanceof JsonSyntaxException) {
                    error = new NetError("数据解析异常", NetError.ParseError);
                } else {
                    error = new NetError("未知异常", NetError.OtherError);
                }
            } else {
                error = (NetError) e;
            }

            if (useCommonErrorHandler()
                    && VbApi.getCommonProvider() != null) {
                if (VbApi.getCommonProvider().handleError(error)) {        //使用通用异常处理
                    return;
                }
            }
            onFail(error);
        }

    }

    protected abstract void onFail(NetError error);

    @Override
    public void onComplete() {

    }


    protected boolean useCommonErrorHandler() {
        return true;
    }


}
