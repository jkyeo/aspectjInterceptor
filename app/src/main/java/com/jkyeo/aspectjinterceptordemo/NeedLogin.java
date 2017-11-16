package com.jkyeo.aspectjinterceptordemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 杨建宽
 * @date 2017/11/16
 * @mail yangjiankuan@lanjingren.com
 * @desc
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface NeedLogin {
    boolean retry() default true;
}
