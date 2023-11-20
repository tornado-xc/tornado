package com.xingchi.tornado.shortlink.service;

import com.xingchi.tornado.shortlink.model.ShortLink;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xingchi.tornado.shortlink.model.dto.ShortLinkCreateDTO;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xingchi
 * @date 2023-11-19 21:56:44
 */
public interface ShortLinkService extends IService<ShortLink> {

    /**
     * 根据短链id获取长链
     *
     * @param shortId 短链id
     * @return 长链
     */
    String getLongUrlByShortId(String shortId);

    /**
     * 存储短链映射关系
     *
     * @param shortId     短链id
     * @param longURL     长链url
     * @param serviceName 服务名称
     */
    void storeShortURLMapping(String shortId, String longURL, String serviceName);

    /**
     * 根据短链标识，查询长链信息
     *
     * @param shortId 短链id
     * @return 长链
     */
    String getLongURL(String shortId);

    /**
     * 存储长链
     *
     * @param shortLinkCreateDTO 长链元数据信息
     */
    void storeLongUrl(ShortLinkCreateDTO shortLinkCreateDTO);
}
