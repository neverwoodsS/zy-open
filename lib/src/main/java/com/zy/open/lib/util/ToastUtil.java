package com.zy.open.lib.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static void showToast(Context context, String s) {
        showToast(context, s, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, int resId) {
        showToast(context, context.getResources().getText(resId),
                Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, CharSequence text,
                                 int duration) {
        if (context != null) {
            Toast.makeText(context, text, duration).show();
        }

    }
}
