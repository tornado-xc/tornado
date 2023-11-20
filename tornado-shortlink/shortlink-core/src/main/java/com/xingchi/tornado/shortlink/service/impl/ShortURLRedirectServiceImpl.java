package com.xingchi.tornado.shortlink.service.impl;

import com.xingchi.tornado.shortlink.service.ShortLinkService;
import com.xingchi.tornado.shortlink.service.ShortURLRedirectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 重定向服务
 *
 * @author xingchi
 * @date 2023/11/19 22:15
 */
@Service
public class ShortURLRedirectServiceImpl implements ShortURLRedirectService {

    @Autowired
    private ShortLinkService shortLinkService;

    @Override
    public void redirectToLongURL(String shortId, HttpServletResponse response) {

        String longURL = shortLinkService.getLongURL(shortId);
        try {
            // 存在则重定向
            if (StringUtils.hasText(longURL)) {
                response.sendRedirect(longURL);
                response.setStatus(HttpServletResponse.SC_FOUND);
                return;
            }
            // 否则报404
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Short URL not found");
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    @Override
    public void handlerRedirect(String shortId, HttpServletResponse response) {

        // TODO 统计请求
        this.redirectToLongURL(shortId, response);
    }


}
