package com.ocean.commonPackage.util.frontParam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SingleParam {
    private String name; // 参数名
    private String data; // 参数值
    private Pattern pattern; // 参数的正则表达式

    public SingleParam(String name, String data) {
        this.name = name;
        this.data = data;
        this.pattern = new Pattern("", "");
    }
}
