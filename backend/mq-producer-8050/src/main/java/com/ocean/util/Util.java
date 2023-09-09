package com.ocean.util;

public class Util {
    // 生成4位随机验证码
    public static String getVerificationCode() {
        return String.valueOf((int) (Math.random() * 9000 + 1000));
    }
}
