package com.xingchi.tornado.shortlink.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 短链重定向服务
 *
 * @author xingchi
 * @date 2023/11/19 21:23
 */
public interface ShortURLRedirectService {

    /**
     * 重定向到长链url
     *
     * @param shortId       短链重定向
     */
    void redirectToLongURL(String shortId, HttpServletResponse response);

    /**
     * 处理重定向
     *
     * @param shortId       短链id
     * @param response      响应
     */
    void handlerRedirect(String shortId, HttpServletResponse response);

}
