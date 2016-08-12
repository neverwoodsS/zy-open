package com.zy.open.demo.activity.recycler;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.zy.open.demo.R;
import com.zy.open.lib.adapter.recycler.RecyclerAdapter;
import com.zy.open.lib.adapter.recycler.RecyclerViewHolder;
import com.zy.open.lib.base.ZYActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerActivity extends ZYActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter<Item> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        initAdapter();

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        List<Item> dataSource = new ArrayList<>();
        dataSource.add(new Item(Item.TYPE_ONE, "test1"));
        dataSource.add(new Item(Item.TYPE_TWO, "test2"));
        dataSource.add(new Item(Item.TYPE_ONE, "test3"));
        dataSource.add(new Item(Item.TYPE_TWO, "test4"));
        dataSource.add(new Item(Item.TYPE_ONE, "test5"));
        dataSource.add(new Item(Item.TYPE_ONE, "test6"));

        adapter.refresh(dataSource);
    }

    private void initAdapter() {
        Map<Integer, Integer> layouts = new HashMap<>();
        layouts.put(Item.TYPE_ONE, R.layout.item_multi_type_one);
        layouts.put(Item.TYPE_TWO, R.layout.item_multi_type_two);

        adapter = new RecyclerAdapter<Item>(this, new ArrayList<Item>(), layouts) {
            @Override
            public void onBindData(RecyclerViewHolder holder, Item data, int position) {
                if (getItemViewType(position) == Item.TYPE_ONE) {
                    setTypeOneItem(holder, data, position);
                } else {
                    setTypeTwoItem(holder, data, position);
                }
            }

            private void setTypeOneItem(RecyclerViewHolder holder, Item data, int position) {
                TextView itemTv = holder.getChildView(R.id.tv_item);
                itemTv.setText(data.content);
            }

            private void setTypeTwoItem(RecyclerViewHolder holder, Item data, int position) {
                TextView itemTv = holder.getChildView(R.id.tv_item);
                itemTv.setText(data.content);
            }

            @Override
            protected int getItemViewType(int position, Item data) {
                return data.type;
            }
        };
    }

    private class Item {
        public static final int TYPE_ONE = 1;
        public static final int TYPE_TWO = 2;

        public int type;
        public String content = "";

        public Item(int type, String content) {
            this.type = type;
            this.content = content;
        }
    }
}