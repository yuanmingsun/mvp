package com.vb.appmvp;


import com.vb.appmvp.imageloader.ILoader;

public class VbConf {


    //开启缓存
    public static final boolean CACHE_ENABLED=false;


    //约定返回code
    public static final String OK = "0";
    public static final String INVALID_PARAM = "-1";
    public static final String PARAM_MISSING = "-4";
    public static final String INVALID_FORMAT = "-3";
    public static final String TIME_OUT = "-2";
    public static final String STRINGERNAL_ERROR = "500";
    public static final String NOT_FOUND = "404";
    public static final String UNAUTHORIZED = "403";
    public static final String ALREADY_EXIST = "409";
    public static final String STATUS_ERROR = "410";
    public static final String TOO_MANY_TIME = "411";
    public static final String LOST_COOKIE="10001";

    // #log
    public static final boolean LOG = true;
    public static final String LOG_TAG = "vb_tag";

    // #cache
    public static final String CACHE_SP_NAME = "vb_config";//缓存文件名称

    // #imageloader
    public static final int IL_LOADING_RES = ILoader.Options.RES_NONE;//默认的 加载中 图片资源
    public static final int IL_ERROR_RES = ILoader.Options.RES_NONE;//默认的 加载失败 图片资源

    // #router默认设置为渐隐渐现效果
    public static final int ROUTER_ANIM_ENTER = android.R.anim.fade_in;
    public static final int ROUTER_ANIM_EXIT = android.R.anim.fade_out;

    //网络请求超时时间
    public static final int CONNECT_TIMEOUT=10*1000;
    public static final int READ_TIMEOUT=10*1000;

    //网络请求个数
    public static final int PAGE_SIZE=10;

}
