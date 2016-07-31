package com.zy.open.lib;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.Serializable;

/**
 * Created by zhangll on 2016.07.31
 * 最基础的基类 activity
 * 封装了 bundle 数据的存取以避免直接 getIntent 取值错误
 * 封装了 keyboard 的显示与隐藏方法
 */
public class ZYActivity extends AppCompatActivity {

    private Bundle savedBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBundle(savedInstanceState);
        initData(savedBundle);
    }

    /**
     * 传入 onCreate 中的 bundle，根据是否为空进行相应处理
     * 如果为 null，代表 activity 还没有保存过 bundle
     * @param savedInstanceState
     */
    private void initBundle(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            savedBundle = getIntent().getExtras();
        } else {
            savedBundle = savedInstanceState.getBundle("savedBundle");
        }

        //如果没有任何参数，则初始化 savedBundle，避免调用时 null pointer
        if (savedBundle == null) {
            savedBundle = new Bundle();
        }
    }

    /**
     * 保存 bundle
     * @param outState
     */
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBundle("savedBundle", this.savedBundle);
        super.onSaveInstanceState(outState);
    }

    /**
     * 为开发者提供保存额外参数至 savedBundle 的方法
     * @param key
     * @param value
     */
    protected void saveToBundle(String key, Serializable value) {
        savedBundle.putSerializable(key, value);
    }

    /**
     * 为开发者提供保存额外参数至 savedBundle 的方法
     * @param key
     * @param value
     */
    protected void saveToBundle(String key, Parcelable value) {
        savedBundle.putParcelable(key, value);
    }

    /**
     * 获取 savedBundle
     * @return
     */
    protected Bundle getSavedBundle() {
        return savedBundle;
    }

    /**
     * 提供给子类取值的方法
     * @param savedBundle
     */
    protected void initData(Bundle savedBundle) {

    }

    protected void hideKeyboard() {
        View v = getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    protected void showKeyboard(long delayTime) {
        final View v = getCurrentFocus();
        if (v != null) {
            new CountDownTimer(delayTime, delayTime) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
                }
            }.start();
        }
    }

    protected void showKeyboard() {
        showKeyboard(250);
    }
}