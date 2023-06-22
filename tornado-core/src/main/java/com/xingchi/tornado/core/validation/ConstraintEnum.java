package com.xingchi.tornado.core.validation;

import com.xingchi.tornado.basic.BaseEnum;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xingchi
 * @date 2023/6/11 16:38
 * @modified xingchi
 */
public class ConstraintEnum implements ConstraintValidator<EnumCheck, Integer> {

    public static final String METHODE_VALUES = "values";

    public Set<?> values = new HashSet<>();

    @Override
    public void initialize(EnumCheck constraintAnnotation) {
        Class<? extends BaseEnum> enumType = constraintAnnotation.value();
        Method method = ReflectionUtils.findMethod(enumType, METHODE_VALUES);
        assert method != null;
        method.setAccessible(true);
        try {
            BaseEnum[] codes = (BaseEnum[]) method.invoke(null);
            values = Arrays.stream(codes).map(BaseEnum::code).collect(Collectors.toSet());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return values.contains(value);
    }
}
