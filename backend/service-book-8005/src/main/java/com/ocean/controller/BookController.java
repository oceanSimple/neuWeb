package com.ocean.controller;

import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.common.RCode.RCodeEnum;
import com.ocean.mpPackage.sqlEntity.Book;
import com.ocean.mpPackage.sqlEntity.BookType;
import com.ocean.service.serviceImpl.BookServiceImpl;
import com.ocean.service.serviceImpl.BookTypeServiceImpl;
import com.ocean.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookTypeServiceImpl bookTypeService;
    private final BookServiceImpl bookService;

    @Autowired
    public BookController(BookTypeServiceImpl bookTypeService, BookServiceImpl bookService) {
        this.bookTypeService = bookTypeService;
        this.bookService = bookService;
    }

    @GetMapping("/getBookList")
    public R<List<Book>> getBookList() {
        R<List<Book>> bookList = bookService.getBookList();
        R<List<BookType>> bookTypeList = bookTypeService.getAllBookType();
        if (bookTypeList.getData().size() > 0) {
            Util.matchBookAndBookType(bookList.getData(), bookTypeList.getData());
            return R.success(RCodeEnum.SUCCESS.getCode(), "book列表查询成功", bookList.getData());
        } else {
            return R.error(RCodeEnum.ERROR.getCode(), "book列表查询失败");
        }
    }
}
