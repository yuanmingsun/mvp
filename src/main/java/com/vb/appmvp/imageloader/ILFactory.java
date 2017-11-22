package com.vb.appmvp.imageloader;

/**
 * Created by Harrison on 2017/8/15.
 */
public class ILFactory {

    private static ILoader loader;

    /*
    * 调用该方法获取ILoader实例，再获取图片
    * */
    public static ILoader getLoader() {
        if (loader == null) {
            synchronized (ILFactory.class) {
                if (loader == null) {
                    loader = new GlideLoader();
                }
            }
        }
        return loader;
    }


}
