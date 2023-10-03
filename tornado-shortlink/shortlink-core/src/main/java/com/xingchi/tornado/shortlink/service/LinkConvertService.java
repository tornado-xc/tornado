package com.xingchi.tornado.shortlink.service;

/**
 * 链接转换服务
 *
 * @author xiaoya
 * @date 2023/6/29
 */
public interface LinkConvertService {

    /**
     * 长链转短链
     *
     * @param longLink      长链
     * @return              短链code
     */
    String long2Short(String longLink);

    /**
     * 长链转短链
     *
     * @param shortLink     短链code
     * @return              长链
     */
    String short2Long(String shortLink);

}
