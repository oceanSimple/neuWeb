package com.ocean.commonPackage.aop;

import com.ocean.commonPackage.anotation.ErrorLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
@Slf4j
@Aspect
public class ErrorLogAspect {
    @Pointcut("@annotation(com.ocean.commonPackage.anotation.ErrorLog)")
    public void errorLorPointCut() {
    }

    @Around("errorLorPointCut()")
    public Object errorLogAround(ProceedingJoinPoint point) throws ClassNotFoundException {
        Object result = null;
        try {
            result = point.proceed();
        } catch (Throwable throwable) {
            // 获取类路径
            String classPath = point.getThis().toString().split("@")[0];
            // 通过反射获取类
            Class<?> aClass = Class.forName(classPath);
            // 获取方法（注意，此方法要求方法名唯一！）
            for (Method method : aClass.getMethods()) {
                if (method.getName().equals(point.getSignature().getName())) {
                    ErrorLog annotation = method.getAnnotation(ErrorLog.class);
                    log.error("出错方法功能: {}", annotation.value());
                }
            }
            log.error("错误的方法：{}", point.getSignature());
            log.error("传入的参数: {}", Arrays.toString(point.getArgs()));
            log.error("错误原因: {}", throwable.getMessage());
        }
        return result;
    }
}
