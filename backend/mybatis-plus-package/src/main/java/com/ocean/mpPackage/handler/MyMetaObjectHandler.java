package com.ocean.mpPackage.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("gmtCreate", Timestamp.valueOf(LocalDateTime.now()));
        metaObject.setValue("gmtModified", Timestamp.valueOf(LocalDateTime.now()));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 注意，如果要这里生效，需要这样使用update：update(new User(), updateWrapper);
        // 如果update(updateWrapper);使用，更改不会生效！！！
        // 下面三种方式均可以触发更新
        // boolean saveOrUpdate(T entity)
        // boolean saveOrUpdate(T entity, Wrapper<T> updateWrapper)
        // update(T entity, Wrapper<T> updateWrapper)
        metaObject.setValue("gmtModified", Timestamp.valueOf(LocalDateTime.now()));
    }
}
