package com.ocean.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.common.RCode.RCodeEnum;
import com.ocean.mapper.BookTypeMapper;
import com.ocean.mpPackage.sqlEntity.BookType;
import com.ocean.service.BookTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookTypeServiceImpl extends ServiceImpl<BookTypeMapper, BookType> implements BookTypeService {
    @Override
    public R<BookType> getBookTypeById(Long id) {
        BookType bookType = getById(id);
        if (bookType != null)
            return R.success(RCodeEnum.SUCCESS.getCode(), "获取成功", bookType);
        else
            return R.error(RCodeEnum.NO_DATA.getCode(), "获取失败");
    }

    @Override
    public R<BookType> addBookType(BookType bookType) {
        boolean save = save(bookType);
        if (save)
            return R.success(RCodeEnum.SUCCESS.getCode(), "添加成功", bookType);
        else
            return R.error(RCodeEnum.ERROR.getCode(), "添加失败");
    }

    @Override
    public R<String> deleteBookTypeById(Long id) {
        boolean b = removeById(id);
        if (b)
            return R.success(RCodeEnum.SUCCESS.getCode(), "删除成功", "删除成功");
        else
            return R.error(RCodeEnum.ERROR.getCode(), "删除失败");
    }

    @Override
    public R<List<BookType>> getAllBookType() {
        List<BookType> list = list();
        if (list != null)
            return R.success(RCodeEnum.SUCCESS.getCode(), "获取成功", list);
        else
            return R.error(RCodeEnum.NO_DATA.getCode(), "获取失败");
    }
}
