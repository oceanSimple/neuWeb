package com.ocean.util;

import org.springframework.util.DigestUtils;

import java.util.UUID;

public class Util {
    // md5加密
    public static String md5Encryption(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    // 生成uuid
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
