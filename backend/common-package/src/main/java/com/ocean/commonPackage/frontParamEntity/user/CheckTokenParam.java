package com.ocean.commonPackage.frontParamEntity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckTokenParam {
    private String code; // 账号
    private String token; // token
}
