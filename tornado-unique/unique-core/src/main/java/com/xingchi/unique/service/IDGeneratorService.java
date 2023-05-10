package com.xingchi.unique.service;


import com.xingchi.unique.common.IDProviderType;

import java.util.List;

/**
 * id生成服务接口
 *
 * @author xingchi
 * @date 2023/3/10 19:55
 * @modified xingchi
 */
public interface IDGeneratorService {

    /**
     * 雪花id生成器
     *
     * @return          雪花id
     */
    Long snowflakeId();

    /**
     * 雪花id生成器集合
     *
     * @return          雪花id
     */
    List<Long> snowflakeIds(Integer count);

    /**
     * uuid生成器，默认移除短横线
     *
     * @return                  uuid
     */
    String uuid();

    /**
     * 批量生成多个uuid, 默认移除短横线
     *
     * @return                  uuid
     */
    List<String> uuids(Integer count);

    /**
     * 生成redisid
     *
     * @return          redis自增id
     */
    Long redisId();

    /**
     * 生成redisId
     *
     * @return          redis自增id
     */
    List<Long> redisIds(Integer count);

    /**
     * 根据类型生成唯一id
     *
     * @param generatorType         id生成策略类型
     * @return                      id值
     */
    String uniqueId(IDProviderType generatorType);

    /**
     * 根据类型生成唯一id
     *
     * @param generatorType         id生成策略类型
     * @param count                 生成id个数
     * @return                      id集合
     */
    List<String> uniqueIds(IDProviderType generatorType, int count);

}
