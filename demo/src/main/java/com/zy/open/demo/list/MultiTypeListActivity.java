package com.zy.open.demo.list;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.zy.open.demo.R;
import com.zy.open.lib.adapter.list.ListAdapter;
import com.zy.open.lib.base.ZYActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiTypeListActivity extends ZYActivity {

    ListView listView;
    ListAdapter<Item> adapter;
    List<Item> dataSource = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type_list);

        initView();
        initAdapter();
        initDataSource();

        listView.setAdapter(adapter);
        adapter.refresh(dataSource);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.lv);
    }

    private void initAdapter() {
        Map<Integer, Integer> layouts = new HashMap<>();
        layouts.put(Item.TYPE_ONE, R.layout.item_multi_type_one);
        layouts.put(Item.TYPE_TWO, R.layout.item_multi_type_two);

        adapter = new MultiTypeAdapter(this, new ArrayList<Item>(), layouts);
    }

    private void initDataSource() {
        dataSource.add(new Item(Item.TYPE_ONE, "test1"));
        dataSource.add(new Item(Item.TYPE_TWO, "test2"));
        dataSource.add(new Item(Item.TYPE_ONE, "test3"));
        dataSource.add(new Item(Item.TYPE_TWO, "test4"));
        dataSource.add(new Item(Item.TYPE_ONE, "test5"));
        dataSource.add(new Item(Item.TYPE_ONE, "test6"));
    }

    private class Item {
        public static final int TYPE_ONE = 1;
        public static final int TYPE_TWO = 2;

        public int type;
        public String content;

        public Item(int type, String content) {
            this.type = type;
            this.content = content;
        }
    }

    class MultiTypeAdapter extends ListAdapter<Item> {
        public MultiTypeAdapter(Context context, List<Item> data, Map<Integer, Integer> layouts) {
            super(context, data, layouts);
        }

        @Override
        protected void setItem(View convertView, Item data, int position) {
            if (getItemViewType(position) == Item.TYPE_ONE) {
                setTypeOneItem(convertView, data, position);
            } else {
                setTypeTwoItem(convertView, data, position);
            }
        }

        private void setTypeOneItem(View convertView, Item data, int position) {
            TextView itemTv = getChildView(convertView, R.id.tv_item);
            itemTv.setText(data.content);
        }

        private void setTypeTwoItem(View convertView, Item data, int position) {
            TextView itemTv = getChildView(convertView, R.id.tv_item);
            itemTv.setText(data.content);
        }

        @Override
        protected int getItemViewType(int position, Item data) {
            return data.type;
        }
    }
}