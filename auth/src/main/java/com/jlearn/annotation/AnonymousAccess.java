package com.jlearn.annotation;

import java.lang.annotation.*;

/**
 * 标记匿名访问方法
 * @author dingjuru
 * @date 2021/11/12
 */

@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnonymousAccess {
}
