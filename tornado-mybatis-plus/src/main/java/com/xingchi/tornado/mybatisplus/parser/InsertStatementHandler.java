package com.xingchi.tornado.mybatisplus.parser;

import com.xingchi.tornado.mybatisplus.enums.SqlStatementTypeEnum;
import com.xingchi.tornado.mybatisplus.model.SqlStatementHolder;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.insert.Insert;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 查询语句处理器
 *
 * @author xingchi
 * @date 2024/1/21 15:10
 */
public class InsertStatementHandler extends AbstractSqlStatementHandler {

    @Override
    public String addCondition(String source, String condition) {
        return source;
    }

    @Override
    public String addCondition(String source, String condition, Predicate<SqlStatementHolder> predicate) {
        return source;
    }

    @Override
    public String replaceTableName(String source, Map<String, String> tableNameMappings) {

        SqlStatementHolder statementHolder = SqlStatementHolder.getInstance(source);
        if (!SqlStatementTypeEnum.INSERT.equals(statementHolder.getStatementType())) {
            return source;
        }

        Insert insert = (Insert) statementHolder.getStatement();
        Table table = insert.getTable();
        table.setName(tableNameMappings.getOrDefault(table.getName(), table.getName()));

        return insert.toString();
    }

    @Override
    public String replaceTableName(String source, Function<String, String> tableNameHandler) {
        SqlStatementHolder statementHolder = SqlStatementHolder.getInstance(source);
        if (!SqlStatementTypeEnum.INSERT.equals(statementHolder.getStatementType())) {
            return source;
        }

        Insert insert = (Insert) statementHolder.getStatement();
        Table table = insert.getTable();
        table.setName(tableNameHandler.apply(table.getName()));

        return insert.toString();
    }

    @Override
    public String replaceTableName(String source, String suffix) {
        return replaceTableName(source, tableName -> tableName + suffix);
    }


}
