package com.vb.appmvp.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-08-15
 * 网络拦截器
 */
public class VbInterceptor implements Interceptor {
    RequestCallback mRequestCallback;

    public VbInterceptor(RequestCallback callback) {
        this.mRequestCallback = callback;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (mRequestCallback != null) {
            request = mRequestCallback.onBeforeRequest(request, chain);
        }
        Response response = chain.proceed(request);
        if (mRequestCallback != null) {
            Response rp = mRequestCallback.onAfterRequest(response, chain);
            if (rp != null) {
                return rp;
            }
        }
        return response;
    }
}
