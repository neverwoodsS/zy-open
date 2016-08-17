package com.zy.open.demo.recycler;

import com.zy.open.demo.R;
import com.zy.open.lib.adapter.recycler.AdapterDelegate;
import com.zy.open.lib.adapter.recycler.RecyclerViewHolder;

/**
 * Created by zhangll on 16/8/16.
 */
public class TypeTwoDelegate implements AdapterDelegate<RecyclerActivity.Item> {
    @Override
    public int getLayoutId() {
        return R.layout.item_multi_type_two;
    }

    @Override
    public void bind(RecyclerViewHolder holder, RecyclerActivity.Item data, int position) {
        holder.setText(R.id.tv_item, data.content);
    }
}