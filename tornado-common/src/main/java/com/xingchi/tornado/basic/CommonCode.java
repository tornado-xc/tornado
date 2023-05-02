package com.xingchi.tornado.basic;

/**
 * 通用状态码 返回 10000-20000 通用状态码
 *
 * @author xingchi
 * @date 2022/8/18 23:26
 * @modified xingchi
 */
public enum CommonCode implements BaseCode {

    /**
     * 成功状态码
     */
    SUCCESS(10000, "成功", true),
    PARAM_CHECK_FAIL(10001, "参数校验异常", false),
    FAIL(99999, "服务器正忙，请稍后重试或联系管理员~", false),
    BUSINESS_ERROR(11000, "业务繁忙，请稍后重试或联系管理员~", false),
    UNKNOWN(11002, "未知异常，请稍后重试或联系管理员~", false),

    ;

    private final Integer code;
    private final String message;
    private final boolean success;

    CommonCode(Integer code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    @Override
    public Integer code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public boolean success() {
        return success;
    }
}
