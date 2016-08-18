package com.zy.open.demo;

import android.app.Application;

import com.networkbench.agent.impl.NBSAppAgent;

/**
 * User: YangHai (1454025171@qq.com)
 * Date: 2016-08-18
 * Time: 10:33
 */
public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NBSAppAgent.setLicenseKey("58a88474216a4a4da53d3df7f7e1662c").withLocationServiceEnabled(true).setX5Enable(true)
                .start(this.getApplicationContext());
    }
}
