package com.vb.appmvp.adapter.listviewAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.vb.appmvp.adapter.ListItemCallback;

import java.util.List;

/**
 * Created by Harrison on 2017/08/15.
 *
 * 适合单个itemType的listview
 */

public abstract class SingleTypeListAdapter<T, H> extends MultiTypeListAdapter<T> {

    public SingleTypeListAdapter(Context context) {
        super(context);
    }

    public SingleTypeListAdapter(Context context, ListItemCallback<T> callback) {
        super(context, callback);
    }

    public SingleTypeListAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        H holder = null;
        T item = data.get(position);

        if (convertView == null) {
            convertView = View.inflate(context, getLayoutId(), null);
            holder = newViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (H) convertView.getTag();
        }

        convert(convertView,holder, item, position);

        return convertView;
    }

    protected abstract H newViewHolder(View convertView);

    protected abstract int getLayoutId();

    protected abstract void convert(View convertView,H holder, T item, int position);
}
