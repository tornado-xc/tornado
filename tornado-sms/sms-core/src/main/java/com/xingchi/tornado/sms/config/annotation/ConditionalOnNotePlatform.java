package com.xingchi.tornado.sms.config.annotation;

import com.xingchi.tornado.sms.common.enums.PlatformType;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiaoya
 * @date 2023/1/12 13:58
 * @modified xiaoya
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnNotePlatformCondition.class)
public @interface ConditionalOnNotePlatform {

    PlatformType value() default PlatformType.ALIYUN;

}
