package com.zy.open.lib.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhangll on 16/8/12.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public RecyclerViewHolder(View itemView) {
        super(itemView);
    }

    public <T extends View> T getChildView(int id) {
        return (T) itemView.findViewById(id);
    }
}
