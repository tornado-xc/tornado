package com.xingchi.tornado.shortlink.model.dto;

import lombok.Data;

/**
 * 短链创建DTO
 *
 * @author xingchi
 * @date 2023/11/20 11:28
 */
@Data
public class ShortLinkCreateDTO {

    /**
     * 长链
     */
    private String longUrl;

    /**
     * 服务名称
     */
    private String serviceName;

}
