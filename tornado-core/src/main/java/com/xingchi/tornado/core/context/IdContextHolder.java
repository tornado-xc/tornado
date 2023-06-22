package com.xingchi.tornado.core.context;

import com.xingchi.tornado.core.exception.ExceptionWrap;
import com.xingchi.tornado.unique.client.UniqueCodeClient;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

/**
 * id生成ContextHolder
 *
 * @author xiaoya
 * @date 2023/6/19 17:07
 * @modified xiaoya
 */
public class IdContextHolder {

    private static final ThreadLocal<BlockingQueue<Long>> CONTEXT = ThreadLocal.withInitial(() -> new ArrayBlockingQueue<>(100));

    private static UniqueCodeClient uniqueCodeClient;

    public static void setUniqueCodeClient(UniqueCodeClient uniqueCodeClient) {
        IdContextHolder.uniqueCodeClient = uniqueCodeClient;
    }

    public static void set(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id参数不能为空");
        }
        CONTEXT.get().add(id);
    }

    public static void set(Collection<Long> ids) {
        // 过滤空值和重复的
        ids = ids.stream().filter(Objects::nonNull).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(ids)) {
            CONTEXT.get().addAll(ids);
        }
    }

    public static Long get() {
        try {
            BlockingQueue<Long> queue = CONTEXT.get();
            if (queue.size() <= 0) {
                List<Long> list = uniqueCodeClient.snowflakeIds(10);
                CONTEXT.get().addAll(list);
            }
            return CONTEXT.get().take();
        } catch (Exception e) {
            throw new IllegalArgumentException("获取id失败");
        }
    }

    public static void remove() {
        CONTEXT.remove();
    }

}
