package com.xingchi.tornado.mybatisplus.model;

import com.xingchi.tornado.mybatisplus.enums.SqlStatementTypeEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

/**
 * sql语句包装器
 *
 * @author xingchi
 * @date 2024/1/21 13:53
 */
@Data
@Slf4j
public class SqlStatementHolder {

    private String source;

    private Statement statement;

    private SqlStatementTypeEnum statementType;

    private SqlStatementHolder() {

    }

    public static SqlStatementHolder getInstance(String sourceSql) {

        SqlStatementHolder statementHolder = new SqlStatementHolder();
        statementHolder.setSource(sourceSql);

        try {
            Statement statement = CCJSqlParserUtil.parse(sourceSql);
            statementHolder.setStatement(statement);

            if (statement instanceof Select) {
                statementHolder.setStatementType(SqlStatementTypeEnum.SELECT);
            } else if (statement instanceof Insert) {
                statementHolder.setStatementType(SqlStatementTypeEnum.INSERT);
            } else if (statement instanceof Delete) {
                statementHolder.setStatementType(SqlStatementTypeEnum.DELETE);
            } else if (statement instanceof Update) {
                statementHolder.setStatementType(SqlStatementTypeEnum.UPDATE);
            } else {
                statementHolder.setStatementType(SqlStatementTypeEnum.OTHER);
            }

        } catch (JSQLParserException e) {
            log.error("sql语句：{} 解析失败，原因：{}", sourceSql, e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return statementHolder;
    }

}
