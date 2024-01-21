package com.xingchi.tornado.mybatisplus.parser;

import com.xingchi.tornado.mybatisplus.enums.SqlStatementTypeEnum;
import com.xingchi.tornado.mybatisplus.model.SqlStatementHolder;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 查询语句处理器
 *
 * @author xingchi
 * @date 2024/1/21 15:10
 */
@Slf4j
@SuppressWarnings("Duplicates")
public class DeleteStatementHandler extends AbstractSqlStatementHandler {

    @Override
    public String addCondition(String source, String condition) {

        if (StringUtils.isBlank(condition)) {
            return source;
        }

        SqlStatementHolder instance = SqlStatementHolder.getInstance(source);
        if (!SqlStatementTypeEnum.DELETE.equals(instance.getStatementType())) {
            return source;
        }

        Delete delete = (Delete) instance.getStatement();
        Expression where = delete.getWhere();
        try {
            if (Objects.isNull(where)) {
                where = CCJSqlParserUtil.parseCondExpression(condition);
            } else {
                where = CCJSqlParserUtil.parseCondExpression(where + "AND" + condition);
            }
            delete.setWhere(where);
        } catch (JSQLParserException e) {
            throw new RuntimeException(e);
        }
        return delete.toString();
    }

    @Override
    public String addCondition(String source, String condition, Predicate<SqlStatementHolder> predicate) {

        SqlStatementHolder instance = SqlStatementHolder.getInstance(source);
        if (predicate.test(instance)) {
            return addCondition(source, condition);
        }

        return source;
    }

    @Override
    public String replaceTableName(String source, Map<String, String> tableNameMappings) {

        SqlStatementHolder instance = SqlStatementHolder.getInstance(source);
        if (!SqlStatementTypeEnum.DELETE.equals(instance.getStatementType())) {
            return source;
        }

        Delete delete = (Delete) instance.getStatement();
        List<Join> joins = delete.getJoins();
        if (!CollectionUtils.isEmpty(joins)) {
            for (Join join : joins) {
                FromItem rightItem = join.getRightItem();
                if (rightItem instanceof Table) {
                    Table table = (Table) rightItem;
                    table.setName(tableNameMappings.getOrDefault(table.getName(), table.getName()));
                }
            }
        }

        for (Table table : delete.getTables()) {
            table.setName(tableNameMappings.getOrDefault(table.getName(), table.getName()));
        }

        return delete.toString();
    }

    @Override
    public String replaceTableName(String source, Function<String, String> tableNameHandler) {
        SqlStatementHolder instance = SqlStatementHolder.getInstance(source);
        if (!SqlStatementTypeEnum.DELETE.equals(instance.getStatementType())) {
            return source;
        }

        Delete delete = (Delete) instance.getStatement();
        List<Join> joins = delete.getJoins();
        if (!CollectionUtils.isEmpty(joins)) {
            for (Join join : joins) {
                FromItem rightItem = join.getRightItem();
                if (rightItem instanceof Table) {
                    Table table = (Table) rightItem;
                    table.setName(tableNameHandler.apply(table.getName()));
                }
            }
        }

        for (Table table : delete.getTables()) {
            table.setName(tableNameHandler.apply(table.getName()));
        }

        return delete.toString();
    }

    @Override
    public String replaceTableName(String source, String suffix) {
        return replaceTableName(source, tableName -> tableName + suffix);
    }
}
