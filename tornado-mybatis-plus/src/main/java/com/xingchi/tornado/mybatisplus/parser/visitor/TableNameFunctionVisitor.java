package com.xingchi.tornado.mybatisplus.parser.visitor;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.function.Function;

/**
 * tableName访问器
 *
 * @author xingchi
 * @date 2024/1/23 20:41
 */
public class TableNameFunctionVisitor extends TablesNamesFinder {

    /**
     * 表名访问器
     *      <li> key: 原表名
     *      <li> value: 新表名
     */
    private final Function<String, String> tableNameHandler;

    public TableNameFunctionVisitor(Function<String, String> tableNameHandler) {
        if (tableNameHandler == null) {
            tableNameHandler = Function.identity();
        }
        this.tableNameHandler = tableNameHandler;
    }

    @Override
    public void visit(Table tableName) {
        tableName.setName(tableNameHandler.apply(tableName.getName()));
    }
}
