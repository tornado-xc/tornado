package com.xingchi.tornado.shortlink;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 短链系统主启动
 *
 * @author xiaoya
 * @date 2023/6/29
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.xingchi.tornado"})
@MapperScan({"com.xingchi.tornado.*.mapper", "com.xingchi.tornado.*.dao"})
@EnableFeignClients("com.xingchi")
public class ShortLinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortLinkApplication.class, args);
    }

}
