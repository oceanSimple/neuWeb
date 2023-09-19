package com.ocean.commonPackage.util.frontParam;

import com.ocean.commonPackage.anotation.ParamRename;
import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.common.RCode.RCodeEnum;

import java.lang.reflect.Field;
import java.util.HashMap;

// 检查前端传来的参数是否符合规范
public class CheckFrontParam {
    private static final HashMap<String, Pattern> patternMap = StaticPatternMap.getPatternMap();

    public static <T> R<Boolean> checkFrontParam(T param) {
        if (param instanceof SingleParam) { // 如果参数只有一个string类型的参数，那么就直接检查
            return checkSingleParam(param);
        } else { // 如果参数是一个对象，那么就通过反射检查
            return checkObjParam(param);
        }
    }

    private static R<Boolean> checkSingleParam(Object param) {
        SingleParam singleParam = (SingleParam) param;
        // 检查参数名和参数值是否为空
        if (singleParam.getName() == null || singleParam.getData() == null) {
            return R.error(RCodeEnum.PARAM_ERROR.getCode(), "参数名或参数值不能为空");
        }

        // 判断参数是否自带正则表达式检查
        if (!singleParam.getPattern().getRegex().equals("")) { // 如果存在正则表达式，进行检查
            boolean flag = singleParam.getData().matches(singleParam.getPattern().getRegex());
            if (!flag)
                return R.error(RCodeEnum.ERROR.getCode(), singleParam.getPattern().getMessage());
        } else { // 如果没自带正则表达式，检查是否存在默认正则表达式
            Pattern pattern = patternMap.get(singleParam.getName());
            if (pattern != null) { // 如果找到pattern，进行检查
                boolean flag = singleParam.getData().matches(pattern.getRegex());
                if (!flag)
                    return R.error(RCodeEnum.ERROR.getCode(), pattern.getMessage());
            }
        }
        // 如果不存在pattern，说明不需要检查，直接返回成功
        return R.success(RCodeEnum.SUCCESS.getCode(), "参数检查通过", true);
    }

    private static <T> R<Boolean> checkObjParam(T obj) {
        // 通过反射获取所有的属性和属性值，并一次进行检查
        Class<?> aClass = obj.getClass();
        // 获取参数类的所有属性
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) { // 循环：对每一个属性进行参数校验
            try {
                field.setAccessible(true); // 启动对private属性的调用
                R<Boolean> flag; // 用于接收检查结果
                SingleParam singleParam; // 用于存储参数名、参数值、正则表达式
                // 获取属性值，判断属性是否带有ParamRename注解
                if (field.isAnnotationPresent(ParamRename.class)) {
                    ParamRename paramAnnotation = field.getAnnotation(ParamRename.class);
                    String paramName
                            = paramAnnotation.value().equals("") ? field.getName() : paramAnnotation.value();
                    singleParam = new SingleParam(paramName, field.get(obj).toString(), new Pattern(paramAnnotation.regex(), paramAnnotation.errorMessage()));
                } else {
                    singleParam = new SingleParam(field.getName(), field.get(obj).toString());
                }

                flag = checkSingleParam(singleParam);
                if (!RCodeEnum.SUCCESS.getCode().equals(flag.getCode())) { // 如果检查失败，直接返回
                    return flag;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return R.success(RCodeEnum.SUCCESS.getCode(), "参数检查通过", true);
    }
}
