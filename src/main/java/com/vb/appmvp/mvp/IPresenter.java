package com.vb.appmvp.mvp;

/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-08-15
 */
public interface IPresenter<V> {
    void attachView(V view);
    void detachView();
}
