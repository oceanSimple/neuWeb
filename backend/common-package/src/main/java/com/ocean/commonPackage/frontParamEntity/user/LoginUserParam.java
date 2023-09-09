package com.ocean.commonPackage.frontParamEntity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserParam {
    private String code; // 用户名
    private String password; // 密码
}
