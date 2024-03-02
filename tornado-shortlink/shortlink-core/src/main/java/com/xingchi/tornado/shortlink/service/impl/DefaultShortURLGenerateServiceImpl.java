package com.xingchi.tornado.shortlink.service.impl;

import com.xingchi.tornado.codec.Base62;
import com.xingchi.tornado.shortlink.service.ShortURLGenerateService;
import com.xingchi.tornado.unique.client.UniqueCodeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 默认短链生成服务
 *
 * @author xingchi
 * @date 2023/11/19 21:16
 */
@Service
public class DefaultShortURLGenerateServiceImpl implements ShortURLGenerateService {

    @Autowired
    private UniqueCodeClient uniqueCodeClient;

    @Override
    public String generateShortURL(String longUrl) {
        Long uniqueId = uniqueCodeClient.redisId();
        return Base62.encode(uniqueId);
    }

}
