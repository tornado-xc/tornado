package com.xingchi.tornado.core.config;

import com.xingchi.tornado.core.context.SpringContextHolder;
import com.xingchi.tornado.core.lock.RedisLock;
import com.xingchi.tornado.core.plugins.IdAutoInterceptor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 通用配置
 *
 * @author xingchi
 * @date 2023/5/1 21:47
 * @modified xingchi
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class GeneralConfiguration {

    @Bean
    @ConditionalOnBean(RedisProperties.class)
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient(RedisProperties redisProperties) {
        Config config = new Config();

        RedisProperties.Sentinel sentinel = redisProperties.getSentinel();
        RedisProperties.Cluster cluster = redisProperties.getCluster();
        if (sentinel != null && !CollectionUtils.isEmpty(sentinel.getNodes())) {
            // 哨兵模式配置
            List<String> nodes = sentinel.getNodes().stream().map(address -> "redis://" + address).collect(Collectors.toList());
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
            sentinelServersConfig
                    .setMasterName(sentinel.getMaster())
                    .setSentinelAddresses(nodes);
            if (StringUtils.hasText(sentinel.getPassword())) {
                sentinelServersConfig.setPassword(sentinel.getPassword());
            }
        } else if (cluster != null && !CollectionUtils.isEmpty(cluster.getNodes())) {
            // 集群模式配置
            List<String> nodes = cluster.getNodes().stream().map(address -> "redis://" + address).collect(Collectors.toList());
            ClusterServersConfig clusterServersConfig = config.useClusterServers();
            clusterServersConfig.setNodeAddresses(nodes);
            if (StringUtils.hasText(redisProperties.getPassword())) {
                clusterServersConfig.setPassword(redisProperties.getPassword());
            }
        } else {
            // 单机
            SingleServerConfig singleServerConfig = config.useSingleServer();
            singleServerConfig
                    .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
                    .setDatabase(redisProperties.getDatabase());
            if (StringUtils.hasText(redisProperties.getPassword())) {
                singleServerConfig.setPassword(redisProperties.getPassword());
            }
        }

        RedissonClient redissonClient = Redisson.create(config);
        RedisLock.init(redissonClient, false);
        return redissonClient;
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringContextHolder springContextHolder() {
        return SpringContextHolder.getInstance();
    }

    @Bean
    @ConditionalOnMissingBean
    public IdAutoInterceptor idAutoInterceptor() {
        return new IdAutoInterceptor(true);
    }

}
