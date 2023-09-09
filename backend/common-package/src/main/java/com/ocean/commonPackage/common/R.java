package com.ocean.commonPackage.common;

import lombok.Data;

/**
 * 通用返回结果类
 *
 * @param <T>
 */
@Data
public class R<T> {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    public static <T> R<T> success(Integer code, String msg, T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = code;
        r.msg = msg;
        return r;
    }

    public static <T> R<T> error(Integer code, String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }
}
