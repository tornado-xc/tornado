package com.xingchi.tornado.shortlink.controller;

import com.xingchi.tornado.shortlink.model.dto.ShortLinkCreateDTO;
import com.xingchi.tornado.shortlink.service.ShortLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xingchi
 * @date 2023/11/19 22:54
 */
@RestController
@RequestMapping("/shortlink")
public class ShortLinkController {

    @Autowired
    private ShortLinkService shortLinkService;

    @PostMapping
    public void storeLongUrl(@RequestBody ShortLinkCreateDTO shortLinkCreateDTO) {
        shortLinkService.storeLongUrl(shortLinkCreateDTO);
    }

}
