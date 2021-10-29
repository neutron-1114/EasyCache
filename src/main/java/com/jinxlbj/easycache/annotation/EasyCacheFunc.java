package com.jinxlbj.easycache.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface EasyCacheFunc {

    String namespace() default "default";

    String key() default "";

    int ttl() default 60 * 60;

    // 0:get|set 1:set 2:del
    int type() default 0;

}
