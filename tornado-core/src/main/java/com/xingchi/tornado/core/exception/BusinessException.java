package com.xingchi.tornado.core.exception;

import com.xingchi.tornado.basic.BaseCode;
import com.xingchi.tornado.basic.CommonCode;

/**
 * 业务异常
 *
 * @author xingchi
 * @date 2022/8/18 23:32
 * @modified xingchi
 */
public class BusinessException extends UncheckedException {

    public BusinessException(BaseCode baseCode) {
        super(baseCode.code(), baseCode.message());
    }

    public BusinessException(String message) {
        super(CommonCode.BUSINESS_ERROR.code(), message);
    }

    public BusinessException(int code, String message) {
        super(code, message);
    }

    public BusinessException(int code, String message, Object ...args) {
        super(code, message, args);
    }

}
