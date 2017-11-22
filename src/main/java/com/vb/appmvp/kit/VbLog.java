package com.vb.appmvp.kit;

import android.util.Log;

import com.vb.appmvp.VbConf;

/**
 * Created by Harrison on 2017/8/29 0029.
 */

public class VbLog {
    public static void log(String content){
        if(VbConf.LOG){
            Log.i(VbConf.LOG_TAG,content);
        }
    }
    public static void logError(String content){
        if (VbConf.LOG){
            Log.e(VbConf.LOG_TAG,content);
        }
    }
}
