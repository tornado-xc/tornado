package com.xingchi.tornado.unique.client.feign;

import com.xingchi.tornado.basic.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * fegin客户端
 *
 * @author xingchi
 * @date 2023/3/10 20:30
 * @modified xingchi
 */
@FeignClient(value = "unique-service", path = "/unique")
public interface UniqueCodeFeignClient {

    @GetMapping("/{type}")
    Result<String> uniqueId(@PathVariable("type") String type);

    @GetMapping("/{type}/{count}")
    Result<List<String>> uniqueIds(@PathVariable("type") String type, @PathVariable("count") Integer count);

    @GetMapping("/snowflake")
    Result<Long> snowflakeId();

    @GetMapping("/snowflake/{count}")
    Result<List<Long>> snowflakeIds(@PathVariable("count") Integer count);

    @GetMapping("/redis")
    Result<Long> redisId();

    @GetMapping("/redis/{count}")
    Result<List<Long>> redisIds(@PathVariable("count") Integer count);

}
