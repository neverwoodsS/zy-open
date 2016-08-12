package com.zy.open.demo.activity.list;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.zy.open.demo.R;
import com.zy.open.lib.adapter.ZYAdapter;
import com.zy.open.lib.base.ZYActivity;
import java.util.ArrayList;
import java.util.List;

public class SingleTypeListActivity extends ZYActivity {

    ListView listView;
    ZYAdapter<Item> adapter;
    List<Item> dataSource = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_type_list);

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
        adapter = new ZYAdapter<Item>(this, new ArrayList<Item>(), R.layout.item_single_type) {
            @Override
            protected void setItem(View convertView, Item data, int position) {
                TextView itemTv = getChildView(convertView, R.id.tv_item);
                itemTv.setText(data.content);
            }
        };
    }

    private void initDataSource() {
        dataSource.add(new Item("test1"));
        dataSource.add(new Item("test2"));
        dataSource.add(new Item("test3"));
        dataSource.add(new Item("test4"));
        dataSource.add(new Item("test5"));
        dataSource.add(new Item("test6"));
    }

    private class Item {
        public String content;

        public Item(String content) {
            this.content = content;
        }
    }
}