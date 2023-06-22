package com.xingchi.tornado.core.plugins;

import com.xingchi.tornado.core.context.IdContextHolder;
import com.xingchi.tornado.core.plugins.anno.AutoId;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.util.ReflectionUtils;

import java.util.Objects;

import static com.xingchi.tornado.constant.FiledConstants.ID;

/**
 * id自动填充拦截器
 *
 * @author xingchi
 * @date 2023/6/16 22:36
 * @modified xingchi
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
})
public class IdAutoInterceptor implements Interceptor {

    /**
     * 是否强制使用默认id服务生成id
     */
    private final Boolean forceUse;

    /**
     * 构造
     *
     * @param forceUse              是否强制使用
     */
    public IdAutoInterceptor(Boolean forceUse) {
        this.forceUse = forceUse;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        if (SqlCommandType.INSERT.equals(mappedStatement.getSqlCommandType())) {
            // 是插入语句
            Object parameterObject = invocation.getArgs()[1];
            MetaObject metaObject = MetaObject.forObject(parameterObject, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());

            ReflectionUtils.doWithFields(parameterObject.getClass(), field -> {
                AutoId annotation = field.getAnnotation(AutoId.class);
                if (annotation == null && !ID.equals(field.getName())) {
                    return;
                }

                if (Objects.isNull(metaObject.getValue(field.getName())) || forceUse) {
                    if (Long.class.isAssignableFrom(field.getType())) {
                        metaObject.setValue(field.getName(), IdContextHolder.get());
                    } else if (String.class.isAssignableFrom(field.getType())){
                        metaObject.setValue(field.getName(), IdContextHolder.get().toString());
                    }
                }
            });
        }
        return invocation.proceed();
    }
}
