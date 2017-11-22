package com.vb.appmvp.adapter.recycleviewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.droidlover.xrecyclerview.RecyclerAdapter;

/**
 * Created by Harrison on 2017/08/15.
 *
 * 适合单个itemType的recycleview
 */

public abstract class SingleTypeRecAdapter<T, F extends RecyclerView.ViewHolder> extends RecyclerAdapter<T, F> {

    public SingleTypeRecAdapter(Context context) {
        super(context);
    }

    @Override
    public F onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        return newViewHolder(view);
    }

    public abstract F newViewHolder(View itemView);

    public abstract int getLayoutId();

}
