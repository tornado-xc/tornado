package com.xingchi.tornado.basic;

/**
 * 基础枚举
 *
 * @author xingchi
 * @date 2022/8/18 22:35
 * @modified xingchi
 */
public interface BaseEnum {

    /**
     * 获取枚举码
     *
     * @return      枚举码
     */
    Integer code();

    /**
     * 获取该枚举描述信息
     *
     * @return      描述信息
     */
    String description();

    /**
     * 枚举名
     *
     * @return      枚举名
     */
    String name();

    /**
     * 是否是某个类型
     *
     * @param code  code
     * @return      是否是某个枚举码
     */
    default boolean isType(Integer code) {
        return this.code().equals(code);
    }

}
