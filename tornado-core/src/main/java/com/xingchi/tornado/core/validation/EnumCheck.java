package com.xingchi.tornado.core.validation;

import com.xingchi.tornado.basic.BaseEnum;
import org.springframework.lang.NonNull;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 枚举值校验
 *
 * @author xingchi
 * @date 2023/6/11 16:37
 * @modified xingchi
 */
@Documented
@Constraint(validatedBy = ConstraintEnum.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface EnumCheck {

    Class<? extends BaseEnum> value();

    String message() default "{com.xingchi.tornado.core.validation.EnumCheck.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
