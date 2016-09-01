package com.zy.open.demo.recycler.multi;

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
import java.util.List;

public class MultiAdapterActivity extends ZYActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_adapter);
        initView();
        onMultiClicked(null);
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

        //创建 adapter，并实现绑定视图的方法
        RecyclerAdapter<SingleTypeItem> adapter;
        adapter = new RecyclerAdapter<SingleTypeItem>(this, dataSource, R.layout.item_multi_type_two) {
            @Override
            protected void bind(RecyclerViewHolder holder, SingleTypeItem data, int position) {
                holder.setText(R.id.tv_item, data.content);
            }
        };

        //或者这样子写，提供一个唯一的 delegate
        adapter = new RecyclerAdapter<>(this, dataSource);
        adapter.addDelegate(new SingleDelegate());

        //再或者这样子写，直接从构造器传入一个 delegate
        adapter = new RecyclerAdapter<>(this, dataSource, new SingleDelegate());

        recyclerView.setAdapter(adapter);
    }

    public void onMultiClicked(View view) {
        //创建 dataSource
        List<MultiTypeItem> dataSource = new ArrayList<>();
        dataSource.add(new MultiTypeItem(MultiTypeItem.TYPE_ONE, "test1"));
        dataSource.add(new MultiTypeItem(MultiTypeItem.TYPE_TWO, "test2"));
        dataSource.add(new MultiTypeItem(MultiTypeItem.TYPE_ONE, "test3"));
        dataSource.add(new MultiTypeItem(MultiTypeItem.TYPE_TWO, "test4"));
        dataSource.add(new MultiTypeItem(MultiTypeItem.TYPE_ONE, "test5"));
        dataSource.add(new MultiTypeItem(MultiTypeItem.TYPE_ONE, "test6"));

        //创建 adapter，实现类型区分的方法，并添加 delegate
        RecyclerAdapter<MultiTypeItem> adapter;
        adapter = new RecyclerAdapter<MultiTypeItem>(this, dataSource) {
            @Override
            protected int getItemViewType(MultiTypeItem data) {
                return data.type;
            }
        };
        adapter.addDelegate(MultiTypeItem.TYPE_ONE, new TypeOneDelegate())
                .addDelegate(MultiTypeItem.TYPE_TWO, new TypeTwoDelegate());

        recyclerView.setAdapter(adapter);
    }
}