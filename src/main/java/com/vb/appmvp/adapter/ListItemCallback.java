package com.vb.appmvp.adapter;

/**
 * Created by Harrison on 2017/08/15.
 */

public abstract class ListItemCallback<T> {

    public void onItemClick(int position, T model, int tag) {}

    public void onItemLongClick(int position, T model, int tag) {}
}
