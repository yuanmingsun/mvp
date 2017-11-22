package com.vb.appmvp.event;

/**
 * Created by Harrison on 2017/8/15.
 */

public interface IBus {

    void register(Object object);
    void unregister(Object object);
    void post(IEvent event);
    void postSticky(IEvent event);

    /*
    * 想要传递数据，就需要让数据实现IEvent接口,返回自定义唯一标志位
    * */
    interface IEvent {
        int getTag();
    }

}
