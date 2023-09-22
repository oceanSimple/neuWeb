package com.ocean;

import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.common.RCode.RCodeEnum;
import com.ocean.mpPackage.sqlEntity.Book;
import com.ocean.service.serviceImpl.BookServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class BookServiceTest {
    private BookServiceImpl service;

    public BookServiceTest(@Autowired BookServiceImpl service) {
        this.service = service;
    }

    @Test
    public void addBook() {
        Book book = new Book("测试书籍", "ocean", new BigDecimal(10), "https://www.baidu.com", 1, 10, "00000000");
        R<Book> result = service.addBook(book);
        if (result.getCode().equals(RCodeEnum.SUCCESS.getCode()))
            log.info("添加成功: {}", result.getData());
        else
            log.info("添加失败: {}", result.getMsg());
    }

    @Test
    public void updateBook() {
        Book book = new Book("测试书籍", "sea", new BigDecimal(100.0), "https://www.baidu.com", 1, 10, "00000000");
        book.setId(3L);
        R<Book> result = service.updateBook(book);
        if (result.getCode().equals(RCodeEnum.SUCCESS.getCode()))
            log.info("修改成功: {}", result.getData());
        else
            log.info("修改失败: {}", result.getMsg());
    }

    @Test
    public void deleteBook() {
        R<String> result = service.deleteBookById(3L);
        if (result.getCode().equals(RCodeEnum.SUCCESS.getCode()))
            log.info("删除成功: {}", result.getData());
        else
            log.info("删除失败: {}", result.getMsg());
    }

    @Test
    public void getBook() {
        R<Book> result = service.getBookById(1L);
        if (result.getCode().equals(RCodeEnum.SUCCESS.getCode()))
            log.info("获取成功: {}", result.getData());
        else
            log.info("获取失败: {}", result.getMsg());
    }

    @Test
    public void getBookList() {
        service.getBookList().getData().forEach(System.out::println);
    }

}
