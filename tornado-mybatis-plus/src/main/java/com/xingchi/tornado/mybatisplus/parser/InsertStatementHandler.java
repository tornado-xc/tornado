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

}
