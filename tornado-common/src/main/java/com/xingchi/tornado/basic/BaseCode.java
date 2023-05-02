package com.xingchi.tornado.basic;

/**
 * 基本返回码
 *
 * @author xingchi
 * @date 2022/8/18 23:23
 * @modified xingchi
 */
public interface BaseCode {

    /**
     * 状态码
     *
     * @return      状态码
     */
    Integer code();

    /**
     * 提示消息
     *
     * @return      消息提示
     */
    String message();

    /**
     * 是否成功
     *
     * @return      true成功，false失败
     */
    boolean success();

}
