package com.zy.open.demo.recycler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.zy.open.demo.R;
import com.zy.open.demo.recycler.multi.MultiAdapterActivity;
import com.zy.open.demo.recycler.single.SingleAdapterActivity;
import com.zy.open.lib.base.ZYActivity;

public class RecyclerEntranceActivity extends ZYActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_entrance);
    }

    public void toSingle(View v) {
        startActivity(new Intent(this, SingleAdapterActivity.class));
    }

    public void toMulti(View v) {
        startActivity(new Intent(this, MultiAdapterActivity.class));
    }
}