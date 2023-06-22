package com.xingchi.tornado.sms.config;

import com.xingchi.tornado.sms.common.enums.PlatformType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xingchi
 * @date 2023/1/19 23:00
 * @modified xingchi
 */
@Data
@ConfigurationProperties(prefix = "xingchi.note")
public class NoteProperties {

    public static final String PLATFORM_TYPE_PREFIX = "xingchi.note.platform-type";

    /**
     * 短信平台
     */
    private PlatformType platformType = PlatformType.DEFAULT;

    /**
     * 是否开启短信发送
     */
    private boolean enable;

    /**
     * 阿里云属性配置
     */
    private AliyunProperties aliyun;

    /**
     * 腾讯云属性配置
     */
    private TencentCloudProperties tencentCloud;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class AliyunProperties extends BaseProperties {

        /**
         * 服务端点
         */
        private String endpoint = "dysmsapi.aliyuncs.com";

        /**
         * 地域节点
         */
        private String regionId;

    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class TencentCloudProperties extends BaseProperties {

        /**
         * 服务端点
         */
        private String endpoint = "sms.tencentcloudapi.com";

        /**
         * 地域节点
         */
        private String regionId = "ap-guangzhou";

    }

}
