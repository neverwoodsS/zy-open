package com.zy.open.demo.activity.recycler;

import android.widget.TextView;

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
        TextView itemTv = holder.getChildView(R.id.tv_item);
        itemTv.setText(data.content);
    }
}
