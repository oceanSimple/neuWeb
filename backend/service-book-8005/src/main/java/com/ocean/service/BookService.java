package com.ocean.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ocean.commonPackage.common.R;
import com.ocean.mpPackage.sqlEntity.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService extends IService<Book> {
    R<Book> addBook(Book book);

    R<Book> updateBook(Book book);

    R<String> deleteBookById(Long id);

    R<Book> getBookById(Long id);

    R<List<Book>> getBookList();
}
