package com.xingchi.tornado.sms.config.annotation;

import com.xingchi.tornado.sms.common.enums.PlatformType;
import com.xingchi.tornado.sms.config.NoteProperties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 根据存储平台类型进行注入
 *
 * @author xiaoya
 * @date 2023/1/12 13:54
 * @modified xiaoya
 */
public class OnNotePlatformCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        Environment environment = context.getEnvironment();
        if (metadata.isAnnotated(ConditionalOnNotePlatform.class.getName())) {

            MergedAnnotations annotations = metadata.getAnnotations();
            MergedAnnotation<ConditionalOnNotePlatform> platformMergedAnnotation = annotations.get(ConditionalOnNotePlatform.class);
            PlatformType platformType = platformMergedAnnotation.getValue("value", PlatformType.class).orElse(null);
            if (platformType == null) {
                return false;
            }
            PlatformType propertyPlatform = environment.getProperty(NoteProperties.PLATFORM_TYPE_PREFIX, PlatformType.class);
            return platformType.equals(propertyPlatform);
        }

        return false;
    }

}
