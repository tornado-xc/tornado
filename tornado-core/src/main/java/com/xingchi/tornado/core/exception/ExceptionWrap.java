package com.xingchi.tornado.core.exception;

import com.xingchi.tornado.basic.BaseCode;

/**
 * 异常处理工厂，对业务异常抛出进行进一步封装
 *
 * @author xingchi
 * @date 2022/8/22 21:50
 * @modified xingchi
 */
public class ExceptionWrap {

    /**
     * 异常捕获
     *
     * @param baseCode          异常状态码
     */
    public static void cast(BaseCode baseCode) {
        throw new BusinessException(baseCode);
    }

    /**
     * 异常捕获
     *
     * @param message           异常提示消息
     */
    public static void cast(String message) {
        throw new BusinessException(message);
    }

    /**
     * 异常捕获
     *
     * @param code              异常状态码
     * @param message           异常提示消息
     */
    public static void cast(int code, String message) {
        throw new BusinessException(code, message);
    }

    /**
     * 异常捕获
     *
     * @param code              异常状态码
     * @param message           异常提示消息
     * @param args              异常提示消息参数
     */
    public static void cast(int code, String message, Object ...args) {
        throw new BusinessException(code, message, args);
    }

    /**
     * 异常捕获
     *
     * @param baseCode          异常状态码
     * @param message           异常消息
     */
    public static void cast(BaseCode baseCode, String message) {
        throw new BusinessException(baseCode.code(), message);
    }

    /**
     * 异常抛出
     *
     * @param baseCode          状态码
     * @param message           异常信息
     * @param args              格式化参数（与异常信息消息组合使用，格式满足{@link String#format(String, Object...)}）
     */
    public static void cast(BaseCode baseCode, String message, Object ...args) {
        throw new BusinessException(baseCode.code(), message, args);
    }

}
