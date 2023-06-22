package com.xingchi.tornado.core.exception;

/**
 * 异常接口
 *
 * @author xingchi
 * @date 2022/8/22 21:35
 * @modified xingchi
 */
public interface BaseException {

    /**
     * 异常信息
     *
     * @return      异常信息
     */
    String message();

    /**
     * 异常状态码
     *
     * @return      异常状态码
     */
    int code();

}
