package com.xingchi.tornado.core.plugins.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 填充id值，仅支持String or Long类型的id填充，标注在其他类型的字段上，将没有任何作用
 *
 * @author xingchi
 * @date 2023/6/16 23:00
 * @modified xingchi
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoId {
}
