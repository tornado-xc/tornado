package com.xingchi.tornado.mybatisplus.parser;

import com.xingchi.tornado.mybatisplus.model.SqlStatementHolder;
import com.xingchi.tornado.mybatisplus.parser.visitor.TableNameFunctionVisitor;
import com.xingchi.tornado.mybatisplus.parser.visitor.TableNameMappingVisitor;
import net.sf.jsqlparser.statement.Statement;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 抽象的sql语句处理器
 *
 * @author xingchi
 * @date 2024/1/21 14:44
 */
public abstract class AbstractSqlStatementHandler implements SqlStatementHandler {

    public SqlStatementHolder getSqlStatementHolder(String source) {
        return SqlStatementHolder.getInstance(source);
    }

    @Override
    public String addCondition(String source, String condition, Predicate<SqlStatementHolder> predicate) {

        SqlStatementHolder statementHolder = this.getSqlStatementHolder(source);
        if (predicate.test(statementHolder)) {
            return addCondition(source, condition);
        }

        return source;
    }

    @Override
    public String replaceTableName(String source, Map<String, String> tableNameMappings) {

        SqlStatementHolder statementHolder = this.getSqlStatementHolder(source);
        Statement statement = statementHolder.getStatement();
        statement.accept(new TableNameMappingVisitor(tableNameMappings));


        return statement.toString();
    }

    @Override
    public String replaceTableName(String source, Function<String, String> tableNameHandler) {

        SqlStatementHolder statementHolder = this.getSqlStatementHolder(source);
        Statement statement = statementHolder.getStatement();
        statement.accept(new TableNameFunctionVisitor(tableNameHandler));

        return statement.toString();
    }

    @Override
    public String replaceTableName(String source, String suffix) {

        if (StringUtils.isBlank(suffix)) {
            return source;
        }

        return replaceTableName(source, sourceName -> sourceName + suffix);
    }
}
