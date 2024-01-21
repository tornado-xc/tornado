package com.xingchi.tornado.mybatisplus.parser;

import com.xingchi.tornado.mybatisplus.enums.SqlStatementTypeEnum;
import com.xingchi.tornado.mybatisplus.model.SqlStatementHolder;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
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
public class SelectStatementHandler extends AbstractSqlStatementHandler {

    @Override
    public String addCondition(String source, String condition) {

        if (StringUtils.isBlank(condition)) {
            return source;
        }

        SqlStatementHolder statementHolder = SqlStatementHolder.getInstance(source);
        Statement statement = statementHolder.getStatement();
        if (!SqlStatementTypeEnum.SELECT.equals(statementHolder.getStatementType())) {
            return source;
        }

        // 是select语句强转
        Select select = (Select) statement;
        SelectBody selectBody = select.getSelectBody();
        if (!(selectBody instanceof PlainSelect)) {
            return source;
        }

        PlainSelect plainSelect = (PlainSelect) selectBody;
        Expression where = plainSelect.getWhere();
        try {
            if (Objects.isNull(where)) {
                where = CCJSqlParserUtil.parseCondExpression(condition);
            } else {
                where = CCJSqlParserUtil.parseCondExpression(where.toString() + " AND " + condition);
            }
        } catch (JSQLParserException e) {
            log.error("sql条件格式不正确");
            throw new RuntimeException(e);
        }
        plainSelect.setWhere(where);

        return plainSelect.toString();
    }

    @Override
    public String addCondition(String source, String condition, Predicate<SqlStatementHolder> predicate) {
        SqlStatementHolder statementHolder = SqlStatementHolder.getInstance(source);
        if (predicate.test(statementHolder)) {
            return addCondition(source, condition);
        }
        return source;
    }

    @Override
    public String replaceTableName(String source, Map<String, String> tableNameMappings) {

        PlainSelect plainSelect = this.getPlainSelect(source);
        if (Objects.isNull(plainSelect)) {
            return source;
        }

        List<Join> joins = plainSelect.getJoins();
        if (!CollectionUtils.isEmpty(joins)) {
            for (Join join : joins) {
                FromItem rightItem = join.getRightItem();
                if (rightItem instanceof Table) {
                    Table table = (Table) rightItem;
                    table.setName(tableNameMappings.getOrDefault(table.getName(), table.getName()));
                }
            }
        }

        FromItem fromItem = plainSelect.getFromItem();
        Table mainTable = (Table) fromItem;
        mainTable.setName(tableNameMappings.getOrDefault(mainTable.getName(), mainTable.getName()));

        return plainSelect.toString();
    }

    @Override
    public String replaceTableName(String source, Function<String, String> tableNameHandler) {
        PlainSelect plainSelect = this.getPlainSelect(source);
        if (Objects.isNull(plainSelect)) {
            return source;
        }

        List<Join> joins = plainSelect.getJoins();
        if (!CollectionUtils.isEmpty(joins)) {
            for (Join join : joins) {
                FromItem rightItem = join.getRightItem();
                if (rightItem instanceof Table) {
                    Table table = (Table) rightItem;
                    table.setName(tableNameHandler.apply(table.getName()));
                }
            }
        }

        FromItem fromItem = plainSelect.getFromItem();
        Table mainTable = (Table) fromItem;
        mainTable.setName(tableNameHandler.apply(mainTable.getName()));

        return plainSelect.toString();
    }

    @Override
    public String replaceTableName(String source, String suffix) {
        return replaceTableName(source, tableName -> tableName + suffix);
    }

    public PlainSelect getPlainSelect(String source) {
        SqlStatementHolder statementHolder = SqlStatementHolder.getInstance(source);
        Statement statement = statementHolder.getStatement();
        if (!SqlStatementTypeEnum.SELECT.equals(statementHolder.getStatementType())) {
            return null;
        }

        // 是select语句强转
        Select select = (Select) statement;
        SelectBody selectBody = select.getSelectBody();
        if (!(selectBody instanceof PlainSelect)) {
            return null;
        }

        return (PlainSelect) selectBody;
    }
}
