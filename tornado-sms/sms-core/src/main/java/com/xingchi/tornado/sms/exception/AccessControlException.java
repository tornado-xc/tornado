package com.xingchi.tornado.sms.exception;

/**
 * 访问信息配置异常
 *
 * @author xiaoya
 * @date 2023/1/12 16:58
 * @modified xiaoya
 */
public class AccessControlException extends RuntimeException {

    public AccessControlException() {
    }

    public AccessControlException(String message) {
        super(message);
    }

    public AccessControlException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessControlException(Throwable cause) {
        super(cause);
    }

    public AccessControlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
