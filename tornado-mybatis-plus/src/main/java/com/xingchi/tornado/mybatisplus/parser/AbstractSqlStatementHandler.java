package com.xingchi.tornado.mybatisplus.parser;

import com.xingchi.tornado.mybatisplus.model.SqlStatementHolder;

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

}
