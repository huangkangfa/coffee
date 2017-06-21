package com.hkf.coffee.others;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtil {
    // 判断是否是手机号
    public static boolean isPhoneNum(String phone) {
        if(phone==null||"".equals(phone))
            return false;
        String regEx = "^1[3|4|5|7|8][0-9]\\d{8}$";
        Pattern pattern = Pattern.compile(regEx);
        return pattern.matcher(phone).matches();
    }

    // 判断是否是身份证号码
    public static boolean isIdCard(String num) {
        if(num==null||"".equals(num))
            return false;
        String regEx = "^(\\d{6})(18|19|20)?(\\d{2})([01]\\d)([0123]\\d)(\\d{3})(\\d|X|x)?$";
        Pattern pattern = Pattern.compile(regEx);
        return pattern.matcher(num).matches();
    }

    // 判断是否是email
    public static boolean isEmail(String email) {
        if(email==null||"".equals(email))
            return false;
        String regEx = "^[a-zA-Z_0-9]{1,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}$";
        Pattern pattern = Pattern.compile(regEx);
        return pattern.matcher(email).matches();
    }
   //匹配当前字符串是否是ip地址
   public static boolean isIp(String data){
        String regEx ="((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(data);
        return m.matches();
    }
    //匹配中文
    public static boolean isChinese(String data){
        String regEx="[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(data);
        return m.matches();
    }
}