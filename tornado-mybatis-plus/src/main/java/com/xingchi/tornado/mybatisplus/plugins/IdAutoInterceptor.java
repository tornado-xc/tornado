package com.xingchi.tornado.mybatisplus.plugins;

/**
 * id自动填充拦截器
 *
 * @author xingchi
 * @date 2023/6/16 22:36
 * @modified xingchi
 */

import com.xingchi.tornado.mybatisplus.plugins.anno.AutoId;
import com.xingchi.tornado.unique.client.UniqueCodeClient;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.DatabaseIdProvider;
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

import java.lang.reflect.Field;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
})
public class IdAutoInterceptor implements Interceptor {

    private final UniqueCodeClient uniqueCodeClient;
    public IdAutoInterceptor(UniqueCodeClient uniqueCodeClient) {
        this.uniqueCodeClient = uniqueCodeClient;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        if (SqlCommandType.INSERT.equals(mappedStatement.getSqlCommandType())) {
            // 是插入语句
            Object parameterObject = invocation.getArgs()[1];
            MetaObject metaObject = MetaObject.forObject(parameterObject, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
            Field idField = ReflectionUtils.findField(parameterObject.getClass(), "id");
            if (idField != null && metaObject.hasSetter("id")) {
                metaObject.setValue("id", uniqueCodeClient.snowflakeId());
            }
        }
        return invocation.proceed();
    }
}
