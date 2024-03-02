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

    /**
     * 给SQL添加指定条件
     *
     * @param source    原sql
     * @param condition 条件
     * @return 添加条件后的SQL
     */
    String addCondition(String source, String condition);

    /**
     * 给SQL添加指定条件，在满足predicate断言的时候才会进行拼接
     *
     * @param source    原sql
     * @param condition 需要拼接的条件
     * @param predicate 断言
     * @return 添加条件后的sql
     */
    String addCondition(String source, String condition, Predicate<SqlStatementHolder> predicate);

    /**
     * 将原sql中的表明，安装tableNameMappings的映射关系进行替换，如果不存在的映射关系，则不进行处理，包含子查询中的所有表名
     *
     * @param source            原sql
     * @param tableNameMappings 表名映射关系
     * @return 替换表名后的sql
     */
    String replaceTableName(String source, Map<String, String> tableNameMappings);

    /**
     * 将原sql中的表明，安装指定function进行处理，传入原表名，返回新表名
     *
     * @param source           原sql
     * @param tableNameHandler 处理函数
     * @return 替换后的sql
     */
    String replaceTableName(String source, Function<String, String> tableNameHandler);

    /**
     * 给sql中所有表名添加上指定的后缀
     *
     * @param source 原sql
     * @param suffix 指定后缀
     * @return 替换后的sql
     */
    String replaceTableName(String source, String suffix);

}
