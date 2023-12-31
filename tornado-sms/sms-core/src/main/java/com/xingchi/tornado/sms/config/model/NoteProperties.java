package com.xingchi.tornado.sms.config.model;

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
@ConfigurationProperties(prefix = "xingchi.tornado.sms")
public class NoteProperties {

    public static final String PLATFORM_TYPE_PREFIX = "xingchi.tornado.sms.platform-type";

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

    /**
     * 邮箱配置
     */
    private EmailProperties email;

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

    @Data
    public static class EmailProperties {

        private String username;
        private String password;
        private String host;
        private Integer port;
        private Boolean ssl;
        private Boolean debug;

    }

}
