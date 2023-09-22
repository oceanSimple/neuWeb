package com.ocean.commonPackage.entity.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Long id; // 主键, 自增
    private String title; // 书名
    private String author; // 作者
    private BigDecimal price; // 价格
    private String address; // 地址
    private String picture; // 图片
    private Integer type; // 类型
    private Integer account; // 库存
    private String printTime; // 出版时间
    private String publisher; // 出版社
    private String version; // 版本
    private String seller; // 卖家
    private Timestamp gmtCreate; // 创建时间
    private Timestamp gmtModified; // 修改时间
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
