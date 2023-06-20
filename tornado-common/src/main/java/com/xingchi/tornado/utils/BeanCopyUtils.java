package com.xingchi.tornado.utils;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaoya
 */
public class BeanCopyUtils {

    /**
     * BeanCopier 的缓存
     */
    private static final BeanCopierCache CACHE = BeanCopierCache.getInstance();

    /**
     * 将源对象的属性值复制到目标对象的对应属性中
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        BeanCopier copier = CACHE.get(source.getClass(), target.getClass());
        copier.copy(source, target, null);
    }

    /**
     * 将源对象的属性值复制到目标对象的对应属性中
     *
     * @param source     源对象
     * @param targetType 目标对象
     */
    public static <T> T copyProperties(Object source, Class<T> targetType) {
        if (source == null || targetType == null) {
            return null;
        }
        BeanCopier copier = CACHE.get(source.getClass(), targetType);
        T target = null;
        try {
            target = targetType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        copier.copy(source, target, null);
        return target;
    }

    /**
     * 将列表中的所有对象进行拷贝，返回新的列表
     *
     * @param sourceList 源列表
     * @param targetType 目标类型
     * @param <S>        源类型
     * @param <T>        目标类型
     * @return 拷贝后的新列表
     */
    public static <S, T> List<T> copyList(List<S> sourceList, Class<T> targetType) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        List<T> targetList = new ArrayList<>(sourceList.size());
        for (S source : sourceList) {
            T target = copyProperties(source, targetType);
            targetList.add(target);
        }
        return targetList;
    }

    /**
     * BeanCopier 的缓存类
     */
    private static class BeanCopierCache {
        private static final BeanCopierCache INSTANCE = new BeanCopierCache();
        public static final int DEFAULT_CACHE_SIZE = 8192;
        private final Map<String, BeanCopier> beanCopiers = new ConcurrentHashMap<>();

        private BeanCopierCache() {
        }

        private static BeanCopierCache getInstance() {
            return INSTANCE;
        }

        public BeanCopier get(Class<?> sourceClass, Class<?> targetClass) {
            String key = genKey(sourceClass, targetClass);
            BeanCopier copier = beanCopiers.get(key);
            if (copier == null) {

                if (beanCopiers.size() >= DEFAULT_CACHE_SIZE) {
                    beanCopiers.clear();
                }

                copier = BeanCopier.create(sourceClass, targetClass, false);
                beanCopiers.put(key, copier);
            }
            return copier;
        }

        private String genKey(Class<?> sourceClass, Class<?> targetClass) {
            return sourceClass.getName() + targetClass.getName();
        }
    }
}
