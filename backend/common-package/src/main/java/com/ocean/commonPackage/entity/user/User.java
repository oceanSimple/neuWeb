package com.ocean.commonPackage.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private BigInteger id;  // 数据库随机uuid
    private String code;    // 学号，规定为8位
    private String password; // 密码
    private String nickname; // 昵称
    private String campus; // 校区
    private String dormitory; // 宿舍
    private Integer isDeleted; // 数据库插入时会默认为0，删除时会改为1
    private String email; // 邮箱
    private String phone; // 手机号
    private Timestamp gmtCreate; // 数据库插入时会自动填充
    private Timestamp gmtModified; // 数据库插入和更新时会自动填充
    private String token; // 排除字段，不与数据库进行匹配，只是用来判断登录的权限
}
