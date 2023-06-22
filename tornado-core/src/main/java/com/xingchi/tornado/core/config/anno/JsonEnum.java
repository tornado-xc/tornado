package com.xingchi.tornado.core.config.anno;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xingchi.tornado.basic.BaseEnum;
import com.xingchi.tornado.core.config.serializer.EnumSerializer;
import org.springframework.lang.NonNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * json枚举处理
 *
 * @author xiaoya
 * @date 2023/6/16 15:53
 * @modified xiaoya
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = EnumSerializer.class)
public @interface JsonEnum {

    @NonNull
    Class<? extends BaseEnum> value();

}
