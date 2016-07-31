package com.zy.open.lib.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yanghai on 2015/10/6.
 * 验证电话号码，邮箱，身份证号码的正则表达式
 */
public class RegularUtil {

    private static Pattern p;
    private static Matcher m;

    private static boolean baseMethod(String regular, String checkStr) {
        p = Pattern.compile(regular);
        m = p.matcher(checkStr);
        return m.matches();
    }


    // 判断一个字符串是否都为数字
    public static boolean isDigit(String strNum) {
        String regular = "[0-9]{1,}";
        return baseMethod(regular, strNum);
    }

    /**
     * 判断字符串中是否有数字
     * @param str
     * @return
     */
    public static boolean checkIncludeDigit(String str) {
        for (int i = 0; i < 10; i++) {
            if (str.contains("" + i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证邮箱格式是否规范
     *
     * @param email
     * @return 规范返回ture
     */
    public static boolean checkEmail(String email) {
        String regular = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        return baseMethod(regular, email);
    }

    /**
     * 身份证验证
     * @param idCartNo
     * @return
     */
    public static boolean checkIdCardNo(String idCartNo) {
        String regular = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$";
        return baseMethod(regular, idCartNo);
    }
}