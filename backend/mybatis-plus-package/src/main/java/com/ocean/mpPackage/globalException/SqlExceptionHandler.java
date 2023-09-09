package com.ocean.mpPackage.globalException;

import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.common.RCode.RCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;

@ControllerAdvice
@ResponseBody
public class SqlExceptionHandler {
    // 处理重复异常
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        // 处理重复异常
        // Duplicate entry 'ocean' for key 'idx_username'
        if (ex.getMessage().contains("Duplicate entry")) {
            String[] split = ex.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return R.error(RCodeEnum.ERROR.getCode(), msg);
        }
        return R.error(RCodeEnum.ERROR.getCode(), "未知错误：" + ex.getMessage());
    }

    // 处理列、表、数据库不存在异常
    @ExceptionHandler(value = SQLSyntaxErrorException.class)
    public R<String> exceptionHandler(SQLSyntaxErrorException ex) {
        // 处理列名不存在异常
        // Unknown column 'username' in 'field list'
        if (ex.getMessage().contains("Unknown column")) {
            String[] split = ex.getMessage().split(" ");
            String msg = "列：" + split[2] + ",不存在";
            return R.error(RCodeEnum.ERROR.getCode(), msg);
        } else if (ex.getMessage().contains("Table")) {
            // 处理表不存在异常
            // Table 'user' doesn't exist
            String[] split = ex.getMessage().split(" ");
            String msg = "表" + split[1] + ",不存在";
            return R.error(RCodeEnum.ERROR.getCode(), msg);
        } else if (ex.getMessage().contains("Unknown database")) {
            // 处理数据库不存在异常
            // Unknown database 'user'
            String[] split = ex.getMessage().split(" ");
            String msg = "数据库" + split[2] + ",不存在";
            return R.error(RCodeEnum.ERROR.getCode(), msg);
        } else {
            return R.error(RCodeEnum.ERROR.getCode(), "未知错误" + ex.getMessage());
        }
    }
}
