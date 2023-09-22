package com.ocean.commonPackage.entity.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookType {
    private Integer id; // 主键, 自增
    private String type; // 类型名
}
