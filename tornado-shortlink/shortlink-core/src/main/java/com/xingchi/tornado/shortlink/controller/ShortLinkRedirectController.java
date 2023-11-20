package com.xingchi.tornado.shortlink.controller;

import com.xingchi.tornado.shortlink.service.ShortLinkService;
import com.xingchi.tornado.shortlink.service.ShortURLRedirectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xingchi
 * @date 2023/11/19 22:54
 */
@RestController
@RequestMapping("/v1")
public class ShortLinkRedirectController {

    @Autowired
    private ShortURLRedirectService shortURLRedirectService;

    @GetMapping("/{shortId}")
    public void redirectToLongUrl(@PathVariable("shortId") String shortId, HttpServletResponse response) {
        shortURLRedirectService.handlerRedirect(shortId, response);
    }

}
