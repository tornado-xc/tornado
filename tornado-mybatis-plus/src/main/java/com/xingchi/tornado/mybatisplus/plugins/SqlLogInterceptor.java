package com.xingchi.tornado.mybatisplus.plugins;

import com.xingchi.tornado.constant.DateTimeFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * @author xiaoya
 * @date 2023/6/12 17:35
 * @modified xiaoya
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class})
})
public class SqlLogInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(SqlLogInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();

        Object arg = invocation.getArgs()[0];

        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());

        Object result;
        long start = System.currentTimeMillis();
        try {
            result = invocation.proceed();
        } finally {
            long end = System.currentTimeMillis();
            String sql;
            if (Proxy.isProxyClass(statementHandler.getClass())) {
                MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("h.target.delegate.mappedStatement");
                Configuration configuration = (Configuration) metaObject.getValue("h.target.delegate.configuration");
                sql = this.getSql(boundSql, configuration);
            } else {
                Configuration configuration = (Configuration) metaObject.getValue("delegate.configuration");
                sql = this.getSql(boundSql, configuration);
            }

            // 获取SQL
            if (log.isDebugEnabled()) {
                log.debug("executor \t sql: {}", sql);
                log.debug("executor \ttime: {}ms", (end - start));
            }
        }
        return result;
    }

    /**
     * 获取执行SQL
     *
     * @param boundSql          sql元数据信息
     * @param configuration     mybatis配置信息
     * @return                  填充参数后的SQL
     */
    private String getSql(BoundSql boundSql, Configuration configuration) throws SQLException {

        // 获取预编译后的SQL
        String sql = boundSql.getSql();
        if (StringUtils.isBlank(sql)) {
            throw new SQLException("运行sql不能为空");
        }
        sql = this.beautifySql(sql);

        // 获取参数传递过来的值
        Object parameterObject = boundSql.getParameterObject();
        // 获取参数元数据信息
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

        if (!CollectionUtils.isEmpty(parameterMappings)) {
            // 获取参数注册器
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = this.fullParameter(sql, parameterObject);
            } else {
                // 如果是对象，对象值等相关属性
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    // 获取属性名
                    String property = parameterMapping.getProperty();
                    if (metaObject.hasGetter(property)) {
                        Object value = metaObject.getValue(property);
                        sql = fullParameter(sql, value);
                    } else if (boundSql.hasAdditionalParameter(property)){
                        Object value = boundSql.getAdditionalParameter(property);
                        sql = fullParameter(sql, value);
                    }
                }
            }
        }

        return sql;
    }

    /**
     * 填充参数
     *
     * @param sql               SQL
     * @param parameterObject   参数对象
     * @return                  填充过参数的SQL
     */
    private String fullParameter(String sql, Object parameterObject) {

        String handler;
        if (parameterObject instanceof String) {
            handler = "'" + parameterObject + "'";
        } else if (parameterObject instanceof Date) {
            handler = "'" + new SimpleDateFormat(DateTimeFormat.DATE_TIME_FORMAT).format(parameterObject) + "'";
        } else if (parameterObject instanceof LocalDateTime) {
            handler = "'" + DateTimeFormatter.ofPattern(DateTimeFormat.DATE_TIME_FORMAT).format((TemporalAccessor) parameterObject) + "'";
        }  else if (parameterObject instanceof LocalDate) {
            handler = "'" + DateTimeFormatter.ofPattern(DateTimeFormat.DATE_FORMAT).format((TemporalAccessor) parameterObject) + "'";
        }  else if (parameterObject instanceof LocalTime) {
            handler = "'" + DateTimeFormatter.ofPattern(DateTimeFormat.TIME_FORMAT).format((TemporalAccessor) parameterObject) + "'";
        } else {
            handler = Objects.nonNull(parameterObject) ? parameterObject.toString() : "'NULL'";
        }

        sql = sql.replaceFirst("\\?", handler);
        return sql;
    }

    private String beautifySql(String sql) {
        return sql.replaceAll("[\\s\n]+", " ");
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
