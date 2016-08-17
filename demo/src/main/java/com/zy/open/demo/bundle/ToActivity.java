package com.zy.open.demo.bundle;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.zy.open.lib.base.ZYActivity;
import com.zy.open.demo.R;

public class ToActivity extends ZYActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("LifeCircle", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to);
    }

    @Override
    protected void initData(Bundle savedBundle) {
        super.initData(savedBundle);
        int data = savedBundle.getInt("data");
        Log.i("bundle", " data =  " + data);

        //提供用来保存额外参数的 api
        saveToBundle("intData", data + 23);
        Log.i("bundle", " data =  " + savedBundle.getInt("intData"));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i("LifeCircle", "onRestore");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        Log.i("LifeCircle", "onRestore");
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }
}