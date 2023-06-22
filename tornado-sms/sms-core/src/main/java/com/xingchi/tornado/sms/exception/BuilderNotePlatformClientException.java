package com.xingchi.tornado.sms.exception;

/**
 * 构建短信平台操作客户端异常
 *
 * @author xingchi
 * @date 2023/1/19 20:07
 * @modified xingchi
 */
public class BuilderNotePlatformClientException extends RuntimeException {

    public BuilderNotePlatformClientException() {
    }

    public BuilderNotePlatformClientException(String message) {
        super(message);
    }

    public BuilderNotePlatformClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuilderNotePlatformClientException(Throwable cause) {
        super(cause);
    }

    public BuilderNotePlatformClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
