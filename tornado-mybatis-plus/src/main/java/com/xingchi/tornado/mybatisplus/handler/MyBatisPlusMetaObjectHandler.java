package com.xingchi.tornado.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xingchi.tornado.constant.FiledConstants;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * mybatisplus自动填充处理器
 *
 * @author xingchi
 * @date 2023/5/21 20:51
 * @modified xingchi
 */
@Component
@ComponentScan(basePackages = {"com.xingchi.unique.client"})
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {

    /**
     * String类型全类名
     */
    private static final String STRING_CLASS_NAME = "java.lang.String";

    /**
     * Long类型全类名
     */
    private static final String LONG_CLASS_NAME = "java.lang.Long";

    public MyBatisPlusMetaObjectHandler() {
    }

    @Override
    public void insertFill(MetaObject metaObject) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            // 查询当前用户信息的id字段
            Field idField = ReflectionUtils.findField(principal.getClass(), FiledConstants.ID);
            // 如果存在id字段则获取当前字段的值，并填充到createBy以及updateBy字段
            if (Objects.nonNull(idField)) {
                ReflectionUtils.makeAccessible(idField);
                Class<?> type = idField.getType();
                if (STRING_CLASS_NAME.equals(type.getName()) && STRING_CLASS_NAME.equals(metaObject.getGetterType(FiledConstants.CREATE_BY).getName())) {
                    String principalId;
                    try {
                        principalId = (String) idField.get(principal);
                    } catch (IllegalAccessException e) {
                        principalId = null;
                    }
                    this.strictInsertFill(metaObject, FiledConstants.CREATE_BY, String.class, principalId);
                    this.strictInsertFill(metaObject, FiledConstants.UPDATE_BY, String.class, principalId);
                } else if (LONG_CLASS_NAME.equals(type.getName()) && LONG_CLASS_NAME.equals(metaObject.getGetterType(FiledConstants.CREATE_BY).getName())) {
                    Long principalId;
                    try {
                        principalId = idField.getLong(principal);
                    } catch (IllegalAccessException ignored) {
                        principalId = null;
                    }
                    this.strictInsertFill(metaObject, FiledConstants.CREATE_BY, Long.class, principalId);
                    this.strictInsertFill(metaObject, FiledConstants.UPDATE_BY, Long.class, principalId);
                }
            }
        }

        LocalDateTime currentTime = LocalDateTime.now();
        this.strictInsertFill(metaObject, FiledConstants.CREATE_TIME, LocalDateTime.class, currentTime);
        this.strictInsertFill(metaObject, FiledConstants.UPDATE_TIME, LocalDateTime.class, currentTime);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            // 查询当前用户信息的id字段
            Field idField = ReflectionUtils.findField(principal.getClass(), FiledConstants.ID);
            // 如果存在id字段则获取当前字段的值，并填充到createBy以及updateBy字段
            if (Objects.nonNull(idField)) {
                ReflectionUtils.makeAccessible(idField);
                Class<?> type = idField.getType();
                if (STRING_CLASS_NAME.equals(type.getName()) && STRING_CLASS_NAME.equals(metaObject.getGetterType(FiledConstants.CREATE_BY).getName())) {
                    String principalId;
                    try {
                        principalId = (String) idField.get(principal);
                    } catch (IllegalAccessException e) {
                        principalId = null;
                    }
                    this.strictInsertFill(metaObject, FiledConstants.UPDATE_BY, String.class, principalId);
                } else if (LONG_CLASS_NAME.equals(type.getName()) && LONG_CLASS_NAME.equals(metaObject.getGetterType(FiledConstants.CREATE_BY).getName())) {
                    Long principalId;
                    try {
                        principalId = idField.getLong(principal);
                    } catch (IllegalAccessException ignored) {
                        principalId = null;
                    }
                    this.strictInsertFill(metaObject, FiledConstants.UPDATE_BY, Long.class, principalId);
                }
            }
        }
        this.strictInsertFill(metaObject, FiledConstants.UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
    }
}
