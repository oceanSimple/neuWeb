package com.ocean.mpPackage.sqlEntity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("book")
public class Book {
    @TableId(type = IdType.AUTO)
    private Long id; // 主键, 自增
    private String title; // 书名
    private String author; // 作者
    private BigDecimal price; // 价格
    private String address; // 地址
    private String picture; // 图片
    private Integer type; // 类型
    private Integer account; // 库存
    private String printTime; // 出版年份
    private String publisher; // 出版社
    private String version; // 版本
    private String seller; // 卖家
    @TableField(fill = FieldFill.INSERT)
    private Timestamp gmtCreate; // 创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Timestamp gmtModified; // 修改时间
    @TableField(exist = false)
    private BookType bookType; // 书籍类型

    public Book(String title, String author, BigDecimal price, String picture, Integer type, Integer account, String seller) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.picture = picture;
        this.type = type;
        this.account = account;
        this.seller = seller;
    }

    public Book(String title, String author, BigDecimal price, String picture, Integer type, Integer account, String printTime, String publisher, String version) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.picture = picture;
        this.type = type;
        this.account = account;
        this.printTime = printTime;
        this.publisher = publisher;
        this.version = version;
    }
}
