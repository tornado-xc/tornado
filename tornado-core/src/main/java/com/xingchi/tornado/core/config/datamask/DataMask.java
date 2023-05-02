package com.xingchi.tornado.core.config.datamask;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据脱敏
 *
 * @author xingchi
 * @date 2023/2/24 22:26
 * @modified xingchi
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = DataMaskSerializer.class)
public @interface DataMask {

    /**
     * 脱敏数据类型（没给默认值，所以使用时必须指定type）
     */
    DataMaskType type();

    /**
     * 前缀多少位不需要打码
     */
    int prefixNoMaskLen() default 1;

    /**
     * 后面多少位不需要打码
     */
    int suffixNoMaskLen() default 1;

    /**
     * 用什么打码
     */
    String symbol() default "*";

}
