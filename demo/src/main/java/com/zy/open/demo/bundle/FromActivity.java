package com.zy.open.demo.bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.zy.open.lib.base.ZYActivity;
import com.zy.open.demo.R;

/**
 * User: YangHai (1454025171@qq.com)
 * Date: 2016-08-18
 * Time: 10:33
 */
public class FromActivity extends ZYActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from);
        findViewById(R.id.btn_send_bundle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FromActivity.this, ToActivity.class);
                intent.putExtra("data", 1);
                FromActivity.this.startActivity(intent);
            }
        });
    }
}
