package com.ocean.commonPackage.aop;

import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.common.RCode.RCodeEnum;
import com.ocean.commonPackage.util.frontParam.CheckFrontParam;
import com.ocean.commonPackage.util.frontParam.SingleParam;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class CheckParamAspect {
    @Pointcut("@annotation(com.ocean.commonPackage.anotation.CheckParam)")
    public void checkParamPointCut() {
    }

    @Around("checkParamPointCut()")
    public Object errorLogAround(ProceedingJoinPoint point) throws Throwable {
        // 检查参数的数量与格式
        // 本项目默认controller层的参数只有一个（为每一种前端的提交单独设计了提交实体类）
        if (point.getArgs().length == 0) { // 无参，返回错误信息
            return R.error(RCodeEnum.ERROR.getCode(), "参数检查aop：参数不能为空");
        } else if (point.getArgs().length > 1) { // 多参，返回错误信息
            return R.error(RCodeEnum.ERROR.getCode(), "参数检查aop：参数数量过多（大于1）");
        } else { // 单参，检查参数格式
            Object param = point.getArgs()[0]; // 获取参数
            R<Boolean> flag; // 检查结果
            // 参数有两种格式，一种是单个参数（string），一种是实体类
            if (param instanceof String) { // 单个参数，类型一定为string（项目规定）
                // 获取参数名
                String parameterName = ((CodeSignature) point.getSignature()).getParameterNames()[0];
                flag = CheckFrontParam.checkFrontParam(new SingleParam(parameterName, (String) param));
            } else {
                flag = CheckFrontParam.checkFrontParam(param);
            }
            if (!RCodeEnum.SUCCESS.getCode().equals(flag.getCode())) {
                // 如果参数检查失败，直接返回错误信息
                return flag;
            } else {
                // 如果所有参数检查成功，放行
                return point.proceed(point.getArgs());
            }
        }
    }
}
