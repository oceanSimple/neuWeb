package com.ocean.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ocean.commonPackage.common.R;
import com.ocean.mpPackage.sqlEntity.BookType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookTypeService extends IService<BookType> {
    R<BookType> getBookTypeById(Long id);

    R<BookType> addBookType(BookType bookType);

    R<String> deleteBookTypeById(Long id);

    R<List<BookType>> getAllBookType();
}
