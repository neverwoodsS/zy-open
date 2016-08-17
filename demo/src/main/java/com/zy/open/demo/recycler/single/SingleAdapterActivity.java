package com.zy.open.demo.recycler.single;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.zy.open.demo.R;
import com.zy.open.demo.recycler.bean.MultiTypeItem;
import com.zy.open.demo.recycler.bean.SingleTypeItem;
import com.zy.open.lib.adapter.recycler.RecyclerAdapter;
import com.zy.open.lib.adapter.recycler.RecyclerViewHolder;
import com.zy.open.lib.base.ZYActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleAdapterActivity extends ZYActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_adapter);
        initView();
        onSingleClicked(null);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onSingleClicked(View view) {
        //创建 dataSource
        List<SingleTypeItem> dataSource = new ArrayList<>();
        dataSource.add(new SingleTypeItem("test1"));
        dataSource.add(new SingleTypeItem("test2"));
        dataSource.add(new SingleTypeItem("test3"));
        dataSource.add(new SingleTypeItem("test4"));
        dataSource.add(new SingleTypeItem("test5"));
        dataSource.add(new SingleTypeItem("test6"));

        //创建 adapter
        RecyclerAdapter<SingleTypeItem> adapter;
        adapter = new RecyclerAdapter<SingleTypeItem>(this, dataSource, R.layout.item_single_type) {
            @Override
            protected void onBindData(RecyclerViewHolder holder, SingleTypeItem data, int position) {
                holder.setText(R.id.tv_item, data.content);
            }
        };

        recyclerView.setAdapter(adapter);
    }

    public void onMultiClicked(View view) {
        //创建 layouts
        Map<Integer, Integer> layouts = new HashMap<>();
        layouts.put(MultiTypeItem.TYPE_ONE, R.layout.item_multi_type_one);
        layouts.put(MultiTypeItem.TYPE_TWO, R.layout.item_multi_type_two);

        //创建 dataSource
        List<MultiTypeItem> dataSource = new ArrayList<>();
        dataSource.add(new MultiTypeItem(MultiTypeItem.TYPE_ONE, "test1"));
        dataSource.add(new MultiTypeItem(MultiTypeItem.TYPE_TWO, "test2"));
        dataSource.add(new MultiTypeItem(MultiTypeItem.TYPE_ONE, "test3"));
        dataSource.add(new MultiTypeItem(MultiTypeItem.TYPE_TWO, "test4"));
        dataSource.add(new MultiTypeItem(MultiTypeItem.TYPE_ONE, "test5"));
        dataSource.add(new MultiTypeItem(MultiTypeItem.TYPE_ONE, "test6"));

        //创建 adapter
        RecyclerAdapter<MultiTypeItem> adapter;
        adapter = new RecyclerAdapter<MultiTypeItem>(this, dataSource, layouts) {
            @Override
            protected void onBindData(RecyclerViewHolder holder, MultiTypeItem data, int position) {
                if (getItemViewType(position) == MultiTypeItem.TYPE_ONE) {
                    setTypeOneItem(holder, data, position);
                } else {
                    setTypeTwoItem(holder, data, position);
                }
            }

            /**
             * 实现多类型必须重写此方法
             */
            @Override
            protected int getItemViewType(int position, MultiTypeItem data) {
                return data.type;
            }

            private void setTypeOneItem(RecyclerViewHolder holder, MultiTypeItem data, int position) {
                holder.setText(R.id.tv_item, data.content)
                        .setImageResource(R.id.iv_item, R.mipmap.ic_launcher);
            }

            private void setTypeTwoItem(RecyclerViewHolder holder, MultiTypeItem data, int position) {
                holder.setText(R.id.tv_item, data.content);
            }
        };

        recyclerView.setAdapter(adapter);
    }
}