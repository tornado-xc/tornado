package com.xingchi.tornado.mybatisplus.parser;

import com.xingchi.tornado.mybatisplus.model.SqlStatementHolder;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * sql语句处理器
 *
 * @author xingchi
 * @date 2024/1/21 13:52
 */
public interface SqlStatementHandler {

    String addCondition(String source, String condition);

    String addCondition(String source, String condition, Predicate<SqlStatementHolder> predicate);

    String replaceTableName(String source, Map<String, String> tableNameMappings);

    String replaceTableName(String source, Function<String, String> tableNameHandler);

    String replaceTableName(String source, String suffix);

}
