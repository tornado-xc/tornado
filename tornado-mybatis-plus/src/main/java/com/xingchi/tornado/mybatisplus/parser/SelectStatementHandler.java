package com.xingchi.tornado.mybatisplus.parser;

import com.xingchi.tornado.mybatisplus.constants.SqlConstants;
import com.xingchi.tornado.mybatisplus.enums.SqlStatementTypeEnum;
import com.xingchi.tornado.mybatisplus.model.SqlStatementHolder;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

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
                where = CCJSqlParserUtil.parseCondExpression(where + SqlConstants.AND + condition);
            }
        } catch (JSQLParserException e) {
            log.error("sql条件格式不正确");
            throw new RuntimeException(e);
        }
        plainSelect.setWhere(where);

        return plainSelect.toString();
    }

}
