package com.xingchi.tornado.mybatisplus.parser;

import com.xingchi.tornado.mybatisplus.constants.SqlConstants;
import com.xingchi.tornado.mybatisplus.enums.SqlStatementTypeEnum;
import com.xingchi.tornado.mybatisplus.model.SqlStatementHolder;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.update.Update;
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
public class UpdateStatementHandler extends AbstractSqlStatementHandler {

    @Override
    public String addCondition(String source, String condition) {

        if (StringUtils.isBlank(condition)) {
            return source;
        }

        SqlStatementHolder instance = SqlStatementHolder.getInstance(source);
        if (!SqlStatementTypeEnum.UPDATE.equals(instance.getStatementType())) {
            return source;
        }

        Update update = (Update) instance.getStatement();
        Expression where = update.getWhere();
        try {
            if (Objects.isNull(where)) {
                where = CCJSqlParserUtil.parseCondExpression(condition);
            } else {
                where = CCJSqlParserUtil.parseCondExpression(where + SqlConstants.AND  + condition);
            }
            update.setWhere(where);
            return update.toString();
        } catch (JSQLParserException e) {
            throw new RuntimeException(e);
        }
    }

}
