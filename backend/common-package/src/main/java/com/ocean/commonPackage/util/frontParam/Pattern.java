package com.ocean.commonPackage.util.frontParam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Pattern {
    private String regex; // 正则表达式
    private String message; // 错误信息
}
