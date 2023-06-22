package com.xingchi.tornado.mybatisplus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * mybatisplus属性配置
 *
 * @author xingchi
 * @date 2023/6/22 20:59
 * @modified xingchi
 */

@ConfigurationProperties(prefix = "xingchi.tornado.logging")
public class MybatisPlusProperties {

    /**
     * 是否开启sql日志记录
     */
    private boolean enable = false;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
