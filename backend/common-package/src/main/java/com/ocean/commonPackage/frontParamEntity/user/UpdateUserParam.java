package com.ocean.commonPackage.frontParamEntity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserParam {
    private String code; // 账号
    private String target; // 修改目标
    private String data; // 修改数据
}
