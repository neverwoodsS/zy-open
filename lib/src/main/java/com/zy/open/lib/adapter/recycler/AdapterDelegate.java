package com.zy.open.lib.adapter.recycler;

/**
 * Created by zhangll on 16/8/16.
 */
public interface AdapterDelegate<T> {
    int getLayoutId();
    void bind(RecyclerViewHolder holder, T data, int position);
}
