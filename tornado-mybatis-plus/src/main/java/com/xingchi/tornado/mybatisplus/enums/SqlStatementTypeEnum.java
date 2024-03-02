package com.xingchi.tornado.mybatisplus.enums;

import com.xingchi.tornado.basic.BaseEnum;
import com.xingchi.tornado.mybatisplus.parser.SqlStatementHandler;
import lombok.Getter;

/**
 * sql语句类型
 *
 * @author xingchi
 * @date 2024/1/21 14:08
 */
@Getter
public enum SqlStatementTypeEnum implements BaseEnum {

    SELECT(1, "查询", null),
    UPDATE(2, "更新", null),
    INSERT(3, "插入", null),
    DELETE(4, "删除", null),
    OTHER(5, "其他", null),
    ;

    private final Integer code;

    private final String description;

    private final SqlStatementHandler sqlStatementHandler;

    SqlStatementTypeEnum(Integer code, String description, SqlStatementHandler sqlStatementHandler) {
        this.code = code;
        this.description = description;
        this.sqlStatementHandler = sqlStatementHandler;
    }
}
