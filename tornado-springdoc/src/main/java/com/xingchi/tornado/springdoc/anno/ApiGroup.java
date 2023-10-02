package com.xingchi.tornado.springdoc.anno;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分组
 *
 * @author xiaoya
 * @date 2022/12/5 16:20
 * @modified xiaoya
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface ApiGroup {

    @AliasFor("group")
    String[] value() default "default" ;

    @AliasFor("value")
    String[] group() default "default";

    int order() default 0;

}
