package com.vb.appmvp.imageloader;

import android.graphics.Bitmap;

/**
 * Created by Harrison on 2017/8/15.
 */

public abstract class LoadCallback {
    void onLoadFailed(Throwable e) {}

    public abstract void onLoadReady(Bitmap bitmap);

    void onLoadCanceled() {}
}
