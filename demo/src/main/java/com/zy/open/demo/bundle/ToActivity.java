package com.zy.open.demo.bundle;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zy.open.lib.base.ZYActivity;
import com.zy.open.demo.R;

/**
 * User: YangHai (1454025171@qq.com)
 * Date: 2016-08-18
 * Time: 10:33
 */
public class ToActivity extends ZYActivity {

    int data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("LifeCircle", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to);

        findViewById(R.id.btn_send_bundle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToActivity.this, ThirdActivity.class);
                ToActivity.this.startActivity(intent);
            }
        });

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("data = " + data);
    }

    @Override
    protected void initData(Bundle savedBundle) {
        super.initData(savedBundle);
        data = savedBundle.getInt("data");
        Log.i("bundle", " data =  " + data);

        //提供用来保存额外参数的 api
        saveToBundle("intData", data + 23);
        Log.i("bundle", " data =  " + savedBundle.getInt("intData"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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