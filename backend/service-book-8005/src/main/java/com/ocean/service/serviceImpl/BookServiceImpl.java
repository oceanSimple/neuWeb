package com.ocean.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.common.RCode.RCodeEnum;
import com.ocean.mapper.BookMapper;
import com.ocean.mpPackage.sqlEntity.Book;
import com.ocean.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    @Override
    public R<Book> addBook(Book book) {
        boolean save = save(book);
        if (save)
            return R.success(RCodeEnum.SUCCESS.getCode(), "添加成功", book);
        else
            return R.error(RCodeEnum.ERROR.getCode(), "添加失败");
    }

    @Override
    public R<Book> updateBook(Book book) {
        UpdateWrapper<Book> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", book.getId());
        boolean update = update(book, updateWrapper);
        if (update)
            return R.success(RCodeEnum.SUCCESS.getCode(), "修改成功", book);
        else
            return R.error(RCodeEnum.ERROR.getCode(), "修改失败");
    }

    @Override
    public R<String> deleteBookById(Long id) {
        boolean remove = removeById(id);
        if (remove)
            return R.success(RCodeEnum.SUCCESS.getCode(), "删除成功", "删除成功");
        else
            return R.error(RCodeEnum.ERROR.getCode(), "删除失败");
    }

    @Override
    public R<Book> getBookById(Long id) {
        Book book = getById(id);
        if (book != null)
            return R.success(RCodeEnum.SUCCESS.getCode(), "查询成功", book);
        else
            return R.error(RCodeEnum.ERROR.getCode(), "查询失败");
    }

    @Override
    public R<List<Book>> getBookList() {
        List<Book> list = list();
        if (list.size() > 0)
            return R.success(RCodeEnum.SUCCESS.getCode(), "查询成功", list);
        else
            return R.error(RCodeEnum.ERROR.getCode(), "查询失败");
    }
}
