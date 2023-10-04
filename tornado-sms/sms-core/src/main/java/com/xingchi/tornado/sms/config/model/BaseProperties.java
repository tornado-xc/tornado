package com.xingchi.tornado.sms.config.model;

import lombok.Data;

/**
 * 通道配置
 *
 * @author xingchi
 * @date 2022/12/1 22:04
 * @modified xingchi
 */
@Data
public class BaseProperties {

    /**
     * AccessKey ID
     */
    private String accessId;

    /**
     * AccessKey Secret
     */
    private String accessSecret;

}
