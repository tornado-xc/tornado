package com.xingchi.tornado.mybatisplus.parser.visitor;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * tableName访问器
 *
 * @author xingchi
 * @date 2024/1/23 20:41
 */
public class TableNameMappingVisitor extends TablesNamesFinder {

    /**
     * 表名映射关系
     */
    private final Map<String, String> tableNameMappings;

    public TableNameMappingVisitor(Map<String, String> tableNameMappings) {
        if (CollectionUtils.isEmpty(tableNameMappings)) {
            tableNameMappings = new HashMap<>();
        }
        this.tableNameMappings = tableNameMappings;
    }

    @Override
    public void visit(Table tableName) {
        tableName.setName(tableNameMappings.getOrDefault(tableName.getName(), tableName.getName()));
    }
}
