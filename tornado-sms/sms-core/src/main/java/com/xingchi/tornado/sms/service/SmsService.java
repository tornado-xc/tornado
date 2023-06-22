package com.xingchi.tornado.sms.service;

import com.xingchi.tornado.basic.BaseParameter;
import com.xingchi.tornado.sms.common.enums.PlatformType;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通知服务
 *
 * @author xingchi
 * @date 2022/12/1 22:00
 * @modified xingchi
 */
public interface SmsService {

    /**
     * 参数的正则表达式
     */
    String PARAM_PATTERN = "\\$\\{([^}]*)\\}";

    /**
     * 实现发送
     *
     * @param account           发送的账户
     * @param template          模板code
     * @param parameter         参数
     * @return                  是否发送成功
     */
    boolean send(String account, String template, BaseParameter parameter);

    /**
     * 实现发送
     *
     * @param accounts          发送的账户(多个)
     * @param template          模板code
     * @param parameter         参数
     * @return                  是否发送成功
     */
    boolean send(List<String> accounts, String template, BaseParameter parameter);

    /**
     * 用于指定实现类使用的平台类型
     *
     * @return          期望返回结果为 {@link PlatformType#code()}
     */
    Integer platformType();

    /**
     * 是否支持该平台
     *
     * @param code              平台码
     * @return                  是否支持
     */
    default boolean supports(Integer code) {
        return this.platformType().equals(code);
    }

    /**
     * 验证模板参数个数是否匹配
     *
     * @param content       模板内容
     * @param parameter     模板参数
     * @return              个数是否匹配
     */
    default boolean verifyTemplateParam(String content, BaseParameter parameter) {
        Pattern compile = Pattern.compile(PARAM_PATTERN);
        Matcher matcher = compile.matcher(content);
        return parameter.keySet().size() == matcher.groupCount();
    }

    /**
     * 填充模板内容
     *
     * @param content       原始模板
     * @param parameter     参数内容
     * @return              替换后的结果
     */
    default String fill(String content, BaseParameter parameter) {
        Pattern compile = Pattern.compile(PARAM_PATTERN);
        Matcher matcher = compile.matcher(content);
        return matcher.replaceAll(matchResult -> {
            // 得到参数${xxx}
            String group = matchResult.group();
            // 得到参数名
            String parameterName = group.substring(2, group.length() - 1);
            // 获取参数
            return  parameter.get(parameterName).toString();
        });
    }

}
