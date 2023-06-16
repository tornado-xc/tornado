package com.xingchi.tornado.mybatisplus.plugins.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 填充id值
 *
 * @author xingchi
 * @date 2023/6/16 23:00
 * @modified xingchi
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoId {
}
