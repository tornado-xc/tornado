package com.xingchi.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 短信通知模块主启动类
 *
 * @author xingchi
 * @date 2023/5/10 22:05
 * @modified xingchi
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
    }

}
