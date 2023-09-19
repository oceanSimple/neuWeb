package com.ocean.commonPackage.util.frontParam;

import java.util.HashMap;

public class StaticPatternMap {
    private static HashMap<String, Pattern> patternMap = new HashMap<>();

    public static HashMap<String, Pattern> getPatternMap() {
        initMap();
        return patternMap;
    }

    private static void initMap() {
        patternMap
                .put("code", new Pattern("^[0-9]{8}$", "账号必须是8位数字"));
        patternMap
                .put("nickname", new Pattern("^[\\u4E00-\\u9FA5A-Za-z0-9]{2,20}$", "账号必须是8位数字"));
        patternMap
                .put("email", new Pattern("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", "邮箱格式不正确"));
        patternMap
                .put("password", new Pattern("^[a-zA-Z0-9]{6,16}$", "密码必须是6-16位字母或数字"));
        patternMap
                .put("emailVerifyCode", new Pattern("^[0-9]{4}$", "邮箱验证码必须是4位数字"));
    }
}
