package com.vb.appmvp.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.vb.appmvp.event.BusProvider;
import com.vb.appmvp.kit.KnifeKit;


/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-08-15
 */
public abstract class BaseActivity<P extends IPresenter> extends RxAppCompatActivity implements IView<P> {
    private VDelegate mDelegate;
    private P p;
    public Activity context;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context =this;
        if(getLayoutId()>0)
        {
            View v= LayoutInflater.from(this).inflate(getLayoutId(),null);
            setContentView(v);
            bindUI(null);
            bindEvent();
        }
        initData(savedInstanceState);
    }

   // @Override
    public void bindUI(View rootView) {
        KnifeKit.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (useEventBus()) {
            BusProvider.getBus().unregister(this);
        }
        if (getP() != null) {
            getP().detachView();
        }
        p = null;
        mDelegate=null;
    }
    protected  VDelegate getvDelegate()
    {
        if(mDelegate==null)
        {
            mDelegate=VDelegate.create(context);
        }
        return mDelegate;
    }
    protected P getP() {
        if (p == null) {
            p = newP();
            if (p != null) {
                p.attachView(this);
            }
        }
        return p;
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (useEventBus()) {
            BusProvider.getBus().register(this);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getOptionsMenuId() > 0) {
            getMenuInflater().inflate(getOptionsMenuId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public int getOptionsMenuId() {
        return 0;
    }

    @Override
    public void bindEvent() {

    }

    @Override
    public boolean useEventBus() {
        return true;
    }
}
