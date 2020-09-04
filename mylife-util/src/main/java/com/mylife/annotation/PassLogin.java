package com.mylife.annotation;

import java.lang.annotation.*;

/**
 * @descirption : 放过登录拦截注解
 * @author : wyh
 * @date : 2020/9/4 18:15
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PassLogin {
}
