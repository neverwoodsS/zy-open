package com.zy.open.demo;

import android.app.Application;

import com.networkbench.agent.impl.NBSAppAgent;

/**
 * Created by  chuangtou on 16/8/2.
 * 国丞创投
 */
public class DemoApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        NBSAppAgent.setLicenseKey("58a88474216a4a4da53d3df7f7e1662c").withLocationServiceEnabled(true).setX5Enable(true)
                .start(this.getApplicationContext());
    }
}
