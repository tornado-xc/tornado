package com.xingchi.tornado.sms.common.enums;

import com.xingchi.tornado.basic.BaseEnum;

import java.util.Arrays;

/**
 * 通知类型
 *
 * @author xingchi
 * @date 2023/5/10 21:46
 * @modified xingchi
 */
public enum NoteType implements BaseEnum {

    /**
     * 通知类型
     */
    CODE(1, "验证码"),
    NOTICE(2, "通知"),
    OTHER(3, "其他"),
    ;

    private final Integer code;
    private final String description;

    NoteType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public Integer code() {
        return this.code;
    }

    @Override
    public String description() {
        return this.description;
    }

    /**
     * 校验是否存在指定类型
     *
     * @param code          类型编码
     * @return              是否存在
     */
    public static boolean exists(Integer code) {
        return Arrays.stream(NoteType.values()).anyMatch(item -> item.code.equals(code));
    }

    /**
     * 根据指定编码获取一个实例
     *
     * @param code          code
     * @return              存在则返回实例，否则返回null
     */
    public static NoteType getInstance(Integer code) {
        return Arrays.stream(NoteType.values()).filter(item -> item.code.equals(code)).findFirst().orElse(null);
    }

    /**
     * 根据指定编码获取一个实例，存在则直接返回，否则返回默认值
     *
     * @param code          code
     * @param defaultValue  默认值
     * @return              存在则返回实例，否则返回默认值defaultValue
     */
    public static NoteType getInstance(Integer code, NoteType defaultValue) {
        return Arrays.stream(NoteType.values()).filter(item -> item.code.equals(code)).findFirst().orElse(defaultValue);
    }
}
