package com.ocean.mpPackage.sqlEntity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("book_type")
public class BookType {
    @TableId(type = IdType.AUTO)
    private Integer id; // 主键, 自增
    private String type; // 类型名
}
