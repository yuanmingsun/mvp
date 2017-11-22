package com.vb.appmvp.cache;

/**
 * Created by Harrison on 2017/8/15.
 */

/*
* 该类是 cache的接口
*   实现类需要实现 增删改查方法
* */

public interface ICache {
    void put(String key, Object value);

    Object get(String key);

    void remove(String key);

    boolean contains(String key);

    void clear();

}
