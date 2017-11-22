package com.vb.appmvp.mvp;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-08-15
 */
public class VDelegate {
    private Context context;

    private VDelegate(Context context) {
        this.context = context;
    }

    public static VDelegate create(Context context) {
        return new VDelegate(context);
    }

    public void visible(View view) {

        view.setVisibility(View.VISIBLE);
    }

    public void gone(View view) {

        view.setVisibility(View.GONE);
    }

    public void inVisible(View view) {

        view.setVisibility(View.INVISIBLE);
    }

    public void toastShort(String msg) {

    }
    public void log(String msg)
    {
        Log.i("app_log",msg);
    }
    public void toastLong(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
