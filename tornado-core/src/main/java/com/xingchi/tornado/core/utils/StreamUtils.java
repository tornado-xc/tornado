package com.xingchi.tornado.core.utils;

import com.google.common.collect.Maps;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流处理工具类
 *
 * @author xingchi
 * @date 2023/12/16 10:43
 */
public class StreamUtils {


    /**
     * 过滤满足条件的元素
     *
     * @param source        元素集合
     * @param filter        断言方法
     * @return              满足指定断言的方法
     * @param <S>           原始元素类型
     */
    public static <S> List<S> filter(List<S> source, @NonNull Predicate<S> filter) {
        return filter(source, filter, false);
    }

    /**
     * 过滤满足条件的元素并支持去重
     *
     * @param source        元素集合
     * @param filter        断言方法
     * @param removeDuplicate 是否去重
     * @return              满足指定断言的方法
     * @param <S>           原始元素类型
     */
    public static <S> List<S> filter(List<S> source, @NonNull Predicate<S> filter, boolean removeDuplicate) {
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        }

        if (removeDuplicate) {
            return source.stream().filter(filter).distinct().collect(Collectors.toList());
        }

        return source.stream().filter(filter).collect(Collectors.toList());
    }

    /**
     * 将 source 目标类型的元素，转换为指定类型的元素
     *
     * @param source        原始元素集合
     * @param convert       转换器，输入 S -> R
     * @return              转换后的对象集合
     * @param <S>           原始类型
     * @param <R>           结果类型
     */
    public static <S, R> List<R> convert(List<S> source, @NonNull Function<S, R> convert) {
        return convert(source, convert, false);
    }

    /**
     * 将 source 目标类型的元素，转换为指定类型的元素 支持去重
     *
     * @param source        原始元素集合
     * @param convert       转换器，输入 S -> R
     * @param removeDuplicate 是否去重
     * @return              转换后的对象集合
     * @param <S>           原始类型
     * @param <R>           结果类型
     */
    public static <S, R> List<R> convert(List<S> source, @NonNull Function<S, R> convert, boolean removeDuplicate) {
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        }

        if (removeDuplicate) {
            return source.stream().map(convert).distinct().collect(Collectors.toList());
        }

        return source.stream().map(convert).collect(Collectors.toList());
    }

    /**
     * 转换后进行过滤
     *
     * @param source        元数据
     * @param convert       转换器
     * @param filter        转换后过滤断言
     * @return              转换后通过断言函数进行过滤
     * @param <S>           原始类型
     * @param <R>           目标类型
     */
    public static <S, R> List<R> convertFilter(List<S> source, @NonNull Function<S, R> convert, @NonNull Predicate<R> filter) {
        return convertFilter(source, convert, filter, false);
    }

    /**
     * 转换后进行过滤 支持去重
     *
     * @param source        元数据
     * @param convert       转换器
     * @param filter        转换后过滤断言
     * @return              转换后通过断言函数进行过滤
     * @param <S>           原始类型
     * @param <R>           目标类型
     */
    public static <S, R> List<R> convertFilter(List<S> source, @NonNull Function<S, R> convert, Predicate<R> filter, boolean removeDuplicate) {
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        }

        Stream<R> sourceStream = source.stream().map(convert).filter(filter);

        if (removeDuplicate) {
            return sourceStream.distinct().collect(Collectors.toList());
        }

        return sourceStream.collect(Collectors.toList());
    }

    /**
     * source 转map
     *
     * @param source        原始类型
     * @param keyMapping    key映射
     * @param valueMapping  value映射
     * @return              KV映射结果
     * @param <K>           Map的Key类型
     * @param <V>           Map的Value类型
     * @param <S>           原始类型
     */
    public static <K, V, S> Map<K, V> toMap(List<S> source,
                                            @NonNull Function<S, K> keyMapping,
                                            @NonNull Function<S, V> valueMapping) {
        if (CollectionUtils.isEmpty(source)) {
            return Maps.newHashMap();
        }

        return source.stream().collect(Collectors.toMap(keyMapping, valueMapping));
    }

    /**
     * 将source进行分组
     *
     * @param source        原始集合
     * @param groupHandler  分组字段
     * @return              分组结果
     * @param <K>           分组K类型
     * @param <S>           原始数据类型
     */
    public static <K, S> Map<K, List<S>> groupingBy(List<S> source,
                                                    @NonNull Function<S, K> groupHandler) {
        if (CollectionUtils.isEmpty(source)) {
            return Maps.newHashMap();
        }

        return source.stream().collect(Collectors.groupingBy(groupHandler, Collectors.toList()));
    }

    /**
     * 分组后重新映射为新的List
     *
     * @param source            原始数据
     * @param groupKeyHandler   分组key
     * @param groupProcessor    分组处理函数
     * @return                  分组结果
     * @param <K>               分组后的Key
     * @param <S>               原始数据类型
     * @param <R>               结果类型
     */
    public static <K, S, R> Map<K, List<R>> groupAndMapping(List<S> source,
                                                            @NonNull Function<S, K> groupKeyHandler,
                                                            @NonNull Function<S, R> groupProcessor) {
        if (CollectionUtils.isEmpty(source)) {
            return Maps.newHashMap();
        }

        return source.stream().collect(Collectors.groupingBy(groupKeyHandler, Collectors.mapping(groupProcessor, Collectors.toList())));
    }

    /**
     * 分组后对其进行进一步处理
     *
     * @param source            原始数据
     * @param groupKeyHandler   分组字段处理
     * @param groupProcessor    分组后的处理函数
     * @return                  分组结果
     * @param <K>               分组Key
     * @param <S>               原始类型
     * @param <R>               最终结果
     */
    public static <K, S, R> Map<K, R> groupAndProcess(List<S> source,
                                                      @NonNull Function<S, K> groupKeyHandler,
                                                      @NonNull Function<List<S>, R> groupProcessor) {
        if (CollectionUtils.isEmpty(source)) {
            return Maps.newHashMap();
        }

        return source.stream().collect(Collectors.groupingBy(groupKeyHandler, Collectors.collectingAndThen(Collectors.toList(), groupProcessor)));
    }

}
