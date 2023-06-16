package com.xingchi.tornado.unique;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * id生成服务主启动类
 *
 * @author xingchi
 * @date 2023/5/9 22:57
 * @modified xingchi
 */
@EnableDiscoveryClient
@SpringBootApplication
public class UniqueApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniqueApplication.class, args);
    }

}
