package com.zy.open.demo.title;

import android.os.Bundle;
import android.view.View;
import com.zy.open.demo.R;
import com.zy.open.lib.base.TitleActivity;
import com.zy.open.lib.view.title.TitleStyle;
import com.zy.open.lib.view.title.TitleView;

public class TestTitleActivity extends TitleActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_title);
    }

    @Override
    protected void setTitle(TitleView titleView) {
        titleView.setTitleStyle(TitleStyle.LEFT_ONLY);
        titleView.setCenterText("这是测试标题");
        titleView.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
