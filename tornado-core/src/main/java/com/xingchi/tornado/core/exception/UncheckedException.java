package com.xingchi.tornado.core.exception;

/**
 * 未校验异常
 *
 * @author xingchi
 * @date 2022/8/22 21:40
 * @modified xingchi
 */
public class UncheckedException extends RuntimeException implements BaseException {

    /**
     * 异常信息
     */
    protected String message;

    /**
     * 异常状态码
     */
    private int code;

    public UncheckedException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public UncheckedException(int code, String format, Object ...args) {
        super(String.format(format, args));
        this.code = code;
        this.message = String.format(format, args);
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public int code() {
        return code;
    }
}
