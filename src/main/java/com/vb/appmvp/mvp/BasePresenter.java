package com.vb.appmvp.mvp;

/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-08-15
 */
public class BasePresenter<V extends IView> implements IPresenter<V> {
    private V v;

    @Override
    public void attachView(V view) {
        v = view;
    }

    @Override
    public void detachView() {
        v = null;
    }

    protected V getV() {
        if (v == null) {
            throw new IllegalStateException("view can not be null");
        }
        return v;
    }
}
