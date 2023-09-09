package com.ocean.commonPackage.frontParamEntity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckEmailVerificationCode {
    private String email; // 邮箱
    private String code; // 验证码
}
