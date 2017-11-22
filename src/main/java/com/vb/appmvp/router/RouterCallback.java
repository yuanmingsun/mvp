package com.vb.appmvp.router;

import android.app.Activity;

/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-08-15
 * 路由跳转的回调
 */
public interface RouterCallback {
    //跳转前回调
    void onBefore(Activity from, Class<?> to);

    //跳转成功后回调
    void onNext(Activity from,Class<?> to);

    //跳转错误回调
    void onError(Activity from,Class<?> to,Throwable throwable);
}
