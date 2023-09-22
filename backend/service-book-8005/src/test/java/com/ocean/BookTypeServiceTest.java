package com.ocean;

import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.common.RCode.RCodeEnum;
import com.ocean.mpPackage.sqlEntity.BookType;
import com.ocean.service.serviceImpl.BookTypeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class BookTypeServiceTest {
    private final BookTypeServiceImpl service;

    @Autowired
    public BookTypeServiceTest(BookTypeServiceImpl service) {
        this.service = service;
    }

    @Test
    public void getBookTypeById() {
        R<BookType> result = service.getBookTypeById(1L);
        if (result.getCode().equals(RCodeEnum.SUCCESS.getCode())) {
            log.info("查询成功:{}", result.getData());
        } else {
            log.info("查询失败");
        }
    }

    @Test
    public void addBookType() {
        BookType type = new BookType();
        type.setType("测试type");
        R<BookType> result = service.addBookType(type);
        if (result.getCode().equals(RCodeEnum.SUCCESS.getCode())) {
            log.info("添加成功:{}", result.getData());
        } else {
            log.info("添加失败");
        }
    }

    @Test
    public void deleteBookTypeById() {
        R<String> result = service.deleteBookTypeById(4L);
        if (result.getCode().equals(RCodeEnum.SUCCESS.getCode())) {
            log.info("删除成功:{}", result.getData());
        } else {
            log.info("删除失败");
        }
    }

    @Test
    public void getAllBookType() {
        service.getAllBookType().getData().forEach(System.out::println);
    }

}
