package com.xingchi.tornado.sms.exception;

/**
 * 通知发送异常
 *
 * @author xingchi
 * @date 2023/1/26 18:54
 * @modified xingchi
 */
public class NoteSendException extends RuntimeException {

    public NoteSendException() {
    }

    public NoteSendException(String message) {
        super(message);
    }

    public NoteSendException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoteSendException(Throwable cause) {
        super(cause);
    }

    public NoteSendException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
