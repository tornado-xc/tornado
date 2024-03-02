package com.xingchi.tornado.shortlink.service;

/**
 * 短链生成服务
 *
 * @author xingchi
 * @date 2023/11/19 21:16
 */
public interface ShortURLGenerateService {

    /**
     * 根据长链url生成对应的短链标识
     *
     * @param longUrl       长链url
     * @return              短链标识
     */
    String generateShortURL(String longUrl);

}
