package com.xingchi.unique.client;

import com.xingchi.unique.client.feign.UniqueCodeFeignClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 客户端操作类
 *
 * @author xingchi
 * @date 2023/3/10 20:37
 * @modified xingchi
 */
@Component
public class UniqueCodeClient {

    private final UniqueCodeFeignClient uniqueCodeFeignClient;

    public UniqueCodeClient(UniqueCodeFeignClient uniqueCodeFeignClient) {
        this.uniqueCodeFeignClient = uniqueCodeFeignClient;
    }

    /**
     * 获取单个id
     *
     * @param type              id生成类型 {@link com.xingchi.common.unique.IDProviderType}
     * @return                  id
     */
    public String uniqueId(String type) {
        return uniqueCodeFeignClient.uniqueId(type).getData();
    }

    /**
     * 获取多个id
     *
     * @param type              id生成类型 {@link com.xingchi.common.unique.IDProviderType}
     * @param count             生成个数
     * @return                  多个id
     */
    public List<String> uniqueIds(String type, Integer count) {
        return uniqueCodeFeignClient.uniqueIds(type, count).getData();
    }

    /**
     * 获取一个雪花id
     *
     * @return          雪花id
     */
    public Long snowflakeId() {
        return uniqueCodeFeignClient.snowflakeId().getData();
    }

    /**
     * 获取多个雪花id
     *
     * @param count         获取个数
     * @return              多个雪花id
     */
    public List<Long> snowflakeIds(Integer count) {
        return uniqueCodeFeignClient.snowflakeIds(count).getData();
    }

    /**
     * 获取一个redisId
     *
     * @return              id
     */
    public Long redisId() {
        return uniqueCodeFeignClient.redisId().getData();
    }

    /**
     * 获取多个个redisId
     *
     * @param count         id个数
     * @return              id
     */
    public List<Long> redisIds(Integer count) {
        return uniqueCodeFeignClient.redisIds(count).getData();
    }

}
