package com.ocean.commonPackage.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ParamRename {
    String value() default "";

    String regex() default "";

    String errorMessage() default "";
}
