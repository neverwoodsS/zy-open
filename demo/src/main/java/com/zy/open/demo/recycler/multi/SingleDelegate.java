package com.zy.open.demo.recycler.multi;

import com.zy.open.demo.R;
import com.zy.open.demo.recycler.bean.SingleTypeItem;
import com.zy.open.lib.adapter.recycler.AdapterDelegate;
import com.zy.open.lib.adapter.recycler.RecyclerViewHolder;

/**
 * Created by zhangll on 16/8/17.
 */
public class SingleDelegate implements AdapterDelegate<SingleTypeItem> {
    @Override
    public int getLayoutId() {
        return R.layout.item_multi_type_two;
    }

    @Override
    public void bind(RecyclerViewHolder holder, SingleTypeItem data, int position) {
        holder.setText(R.id.tv_item, data.content);
    }
}