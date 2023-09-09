package com.ocean.commonPackage.common.RCode;

public enum RCodeEnum {
    SUCCESS(200, "成功"), // 成功
    PARAM_ERROR(400, "参数错误"), // 参数错误
    NO_DATA(404, "没找到数据"), // 没找到数据
    IS_DELETED(405, "数据已被删除"), // 数据已被删除
    ERROR(500, "错误"), // 错误
    IDENTIFY_ERROR(501, "身份验证失败"), // 身份验证失败
    ;

    private Integer code;
    private String msg;

    RCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
