package com.zy.open.demo.recycler.multi;

import com.zy.open.demo.R;
import com.zy.open.demo.recycler.bean.MultiTypeItem;
import com.zy.open.lib.adapter.recycler.AdapterDelegate;
import com.zy.open.lib.adapter.recycler.RecyclerViewHolder;

/**
 * Created by zhangll on 16/8/16.
 */
public class TypeOneDelegate implements AdapterDelegate<MultiTypeItem> {
    @Override
    public int getLayoutId() {
        return R.layout.item_multi_type_one;
    }

    @Override
    public void bind(RecyclerViewHolder holder, MultiTypeItem data, int position) {
        holder.setText(R.id.tv_item, data.content)
                .setImageResource(R.id.iv_item, R.mipmap.ic_launcher);
    }
}