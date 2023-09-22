package com.ocean.util;


import com.ocean.mpPackage.sqlEntity.Book;
import com.ocean.mpPackage.sqlEntity.BookType;

import java.util.List;

public class Util {
    public static void matchBookAndBookType(List<Book> bookList, List<BookType> bookTypeList) {
        bookList.stream().forEach(book -> {
            bookTypeList.stream().forEach(bookType -> {
                if (book.getType().equals(bookType.getId())) {
                    book.setBookType(bookType);
                }
            });
        });
    }
}
