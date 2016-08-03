package com.zy.open.lib.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by  chuangtou on 16/8/3.
 * 国丞创投
 */
public class ShareUtils {

    private static ShareUtils shareUtils;

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    /**
     * 在applicatoin中进行初始化
     * @param context
     * @param shareName
     */
    public static void init(Context context, String shareName) {
        sharedPreferences = context.getSharedPreferences(shareName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public static void saveString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(String key, String normal) {
        return sharedPreferences.getString(key, normal);
    }


    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }


    public static void saveInteger(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInteger(String key, int normal) {
        return sharedPreferences.getInt(key, normal);
    }

    public static int getInteger(String key) {
        return sharedPreferences.getInt(key, 0);
    }


    public static Boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, true);
    }

    public static void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

}
