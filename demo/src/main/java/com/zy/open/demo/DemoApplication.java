package com.zy.open.demo;

import android.app.Application;
import android.graphics.Color;
import android.os.Environment;

import com.networkbench.agent.impl.NBSAppAgent;
import com.zy.open.lib.exception.ExceptionHandler;
import com.zy.open.lib.util.DisplayUtil;
import com.zy.open.lib.view.title.TitleView;

import java.text.SimpleDateFormat;
import java.util.Date;

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

        //扑捉程序奔溃信息，并且，写入本地文件夹
//        ExceptionHandler catchHandler = ExceptionHandler.getInstance();
//        catchHandler.init(getApplicationContext());
//        catchHandler.collectDeviceInfo(getApplicationContext());
//        catchHandler.getSDPath();

        ExceptionHandler.getInstance()
                .init(this)
                .setAbsolutelyPath(Environment.getExternalStorageDirectory().getAbsolutePath())
                .setRelativePath("/exception");

        //初始化单位转换工具
        DisplayUtil.init(this);



        //设置标题栏默认参数
        TitleView.setDefaultBackgroundColor(Color.BLACK);
        TitleView.setDefaultLeftImage(R.mipmap.ic_launcher);
    }
}