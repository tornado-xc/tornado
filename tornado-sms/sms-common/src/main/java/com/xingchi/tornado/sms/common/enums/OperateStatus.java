package com.xingchi.tornado.sms.common.enums;

import com.xingchi.tornado.basic.BaseEnum;

/**
 * 操作状态
 *
 * @author xingchi
 * @date 2023/6/22 19:19
 * @modified xingchi
 */
public enum OperateStatus implements BaseEnum {

    /**
     * 操作状态
     */
    SUCCESS(1, "成功"),
    FAIL(0, "失败");
    ;

    private final Integer code;
    private final String description;

    OperateStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    ;

    @Override
    public Integer code() {
        return code;
    }

    @Override
    public String description() {
        return description;
    }
}
