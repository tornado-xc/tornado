package com.xingchi.unique.provider;

import com.xingchi.common.unique.IDProviderType;

import java.util.ArrayList;
import java.util.List;

/**
 * id生成器
 *
 * @author xingchi
 * @date 2023/5/3 9:55
 * @modified xingchi
 */
public interface IDProvider<T> {

    int DEFAULT_COUNT = 10;

    /**
     * 生成一个id
     *
     * @return          id
     */
    T nextId();

    /**
     * 批量获取id
     *
     * @param count     获取个数
     * @return          id
     */
    default List<T> nextIds(Integer count) {
        if (count == null || count <= 0) {
            count = DEFAULT_COUNT;
        }

        List<T> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(this.nextId());
        }
        return result;
    }

    /**
     * 是否支持
     *
     * @param type          id类型
     * @return              是否支持
     */
    boolean supports(IDProviderType type);

}
