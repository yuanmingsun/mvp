package com.vb.appmvp.net;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-08-15
 */
public interface RequestCallback {
    Request onBeforeRequest(Request request, Interceptor.Chain chain);
    Response onAfterRequest(Response request, Interceptor.Chain chain);
}
