package com.xingchi.tornado.core.utils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xingchi
 * @date 2023/12/27 21:55
 */
public class TaskFutureUtils {

    /**
     * 并行任务处理方法，针对同一种处理方式对不同入参结果进行处理，获取最终结果
     * <p>
     *     用于处理相同类型不同入参的任务
     * </p>
     *
     * @param executor 线程池
     * @param source   处理源数据
     * @param handler  处理方式
     * @param <T>      原始元素类型
     * @param <R>      返回结果类型
     * @return 同步等待每个元素的处理结果
     */
    public static <T, R> List<R> parallelTask(Executor executor, List<T> source, Function<T, R> handler) {
        List<CompletableFuture<R>> completableFutures = source.stream().map(item -> CompletableFuture.supplyAsync(() -> handler.apply(item), executor)).collect(Collectors.toList());
        return completableFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

}
