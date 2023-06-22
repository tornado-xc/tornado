package com.xingchi.tornado.unique.service.impl;


import com.xingchi.tornado.unique.common.IDProviderType;
import com.xingchi.tornado.constant.Constants;
import com.xingchi.tornado.unique.provider.IDProvider;
import com.xingchi.tornado.unique.provider.impl.RedisProvider;
import com.xingchi.tornado.unique.provider.impl.SnowflakeProvider;
import com.xingchi.tornado.unique.provider.impl.UUIDProvider;
import com.xingchi.tornado.unique.service.IDGeneratorService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * id生成服务接口
 *
 * @author xingchi
 * @date 2023/3/10 19:55
 * @modified xingchi
 */
@Service
@SuppressWarnings({"unchecked"})
public class IDGeneratorServiceImpl implements IDGeneratorService, InitializingBean {

    /**
     * redis id生成器
     */
    private final RedisProvider redisProvider;

    /**
     * 雪花id生成器
     */
    private final SnowflakeProvider snowflakeProvider;

    /**
     * uuid生成器
     */
    private final UUIDProvider uuidProvider;

    private final ApplicationContext context;

    Map<String, IDProvider> providerMappings = new ConcurrentHashMap<>(Constants.INIT_CAPACITY);

    @Autowired
    public IDGeneratorServiceImpl(RedisProvider redisProvider, SnowflakeProvider snowflakeProvider, UUIDProvider uuidProvider, ApplicationContext context) {
        this.redisProvider = redisProvider;
        this.snowflakeProvider = snowflakeProvider;
        this.uuidProvider = uuidProvider;
        this.context = context;
    }

    @Override
    public Long snowflakeId() {
        return snowflakeProvider.nextId();
    }

    @Override
    public List<Long> snowflakeIds(Integer count) {
        return snowflakeProvider.nextIds(count);
    }

    @Override
    public String uuid() {
        return uuidProvider.nextId();
    }

    @Override
    public List<String> uuids(Integer count) {
        return uuidProvider.nextIds(count);
    }

    @Override
    public Long redisId() {
        return redisProvider.nextId();
    }

    @Override
    public List<Long> redisIds(Integer count) {
        return redisProvider.nextIds(count);
    }

    @Override
    public String uniqueId(IDProviderType generatorType) {
        IDProvider<Object> provider = this.getProvider(generatorType);
        return String.valueOf(provider.nextId());
    }

    @Override
    public List<String> uniqueIds(IDProviderType generatorType, int count) {
        IDProvider<Object> provider = this.getProvider(generatorType);
        return provider.nextIds(count).stream().map(String::valueOf).collect(Collectors.toList());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        providerMappings = context.getBeansOfType(IDProvider.class);
    }

    public <T> IDProvider<T> getProvider(IDProviderType type) {
        if (providerMappings.isEmpty()) {
            providerMappings = context.getBeansOfType(IDProvider.class);
        }

        for (IDProvider provider : providerMappings.values()) {
            if (provider.supports(type)) {
                return provider;
            }
        }

        throw new IllegalArgumentException("未找到相应的服务");
    }

}
