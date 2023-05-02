package com.xingchi.tornado.core.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Spring 容器工具
 * <li>实现{@link ApplicationContextAware}用以获取工厂，以获取bean</li>
 *
 * @author xiaoya
 * @date 2022/8/25 15:42
 * @modified xiaoya
 */
@SuppressWarnings({"unchecked", "unused"})
public class SpringContextHolder implements ApplicationContextAware, BeanFactoryAware {

    public static ApplicationContext context;

    private static ConfigurableBeanFactory beanFactory;

    private static Environment environment;

    public static final String ACTIVE_PROFILES_PROPERTY = "spring.profiles.active";

    public static final String INCLUDE_PROFILES_PROPERTY = "spring.profiles.include";

    private SpringContextHolder() {}

    /**
     * 创建单实例Bean对象
     */
    private static final SpringContextHolder INSTANCE = new SpringContextHolder();

    public static SpringContextHolder getInstance() {
        return INSTANCE;
    }

    /**
     * <li>根据beanName获取Bean对象</li>
     *
     * @param name      beanName
     * @param <T>       beanType
     * @return          指定beanName的对象
     */
    public static <T> T getBean(String name) {
        return (T) context.getBean(name);
    }

    /**
     * <li>根据beanType获取Bean对象</li>
     *
     * @param type      bean对应的字节码类型
     * @param <T>       bean对象类型
     * @return          指定beanName的对象
     */
    public static <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }

    /**
     * <li>获取指定类型指定名称的bean对象</li>
     *
     * @param name      beanName
     * @param type      beanType
     * @param <T>       返回值类型
     * @return          指定名称指定类型的bean
     */
    public static <T> T getBean(String name, Class<T> type) {
        return context.getBean(name, type);
    }

    /**
     * 获取type类型的所有实现类bean名称与实现类对象的映射关系
     *
     * @param type      实际类型
     * @param <T>       接口
     * @return          映射关系
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return context.getBeansOfType(type);
    }

    /**
     * 获取某个类的所有Spring管理的bean对象
     *
     * @param type      类型
     * @param <T>       接口
     * @return          beans
     */
    public static <T> Collection<T> getBeans(Class<T> type) {
        Map<String, T> beansOfType = SpringContextHolder.getBeansOfType(type);
        return beansOfType.values();
    }

    /**
     * 设置上下文对象
     *
     * @param applicationContext        Spring工厂
     * @throws BeansException           异常
     */
    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.environment = applicationContext.getEnvironment();
        SpringContextHolder.context = applicationContext;
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        SpringContextHolder.beanFactory = (ConfigurableBeanFactory) beanFactory;
    }

    /**
     * 像Spring Bean工厂中注入一个Bean对象
     *
     * @param name      beanName
     * @param obj       需要注入的对象实例
     */
    public static void registerSingleton(String name, Object obj) {
        SpringContextHolder.beanFactory.registerSingleton(name, obj);
    }

    /**
     * 发布一个事件
     *
     * @param applicationEvent      发布的事件
     */
    public static void publishEvent(@NonNull ApplicationEvent applicationEvent) {
        SpringContextHolder.context.publishEvent(applicationEvent);
    }

    /**
     * 获取当前激活的环境{@link SpringContextHolder#ACTIVE_PROFILES_PROPERTY}
     *
     * @return          当前环境字符串
     */
    public static String getActiveProfile() {
        return SpringContextHolder.environment.getProperty(ACTIVE_PROFILES_PROPERTY);
    }

    /**
     * 获取所有激活的环境使用,分隔{@link SpringContextHolder#ACTIVE_PROFILES_PROPERTY}&{@link SpringContextHolder#INCLUDE_PROFILES_PROPERTY}
     *
     * @return          返回所有激活的环境，使用,号分隔
     */
    public static String getActiveProfiles() {
        StringJoiner joiner = new StringJoiner(",");
        Arrays.stream(SpringContextHolder.environment.getActiveProfiles()).forEach(joiner::add);
        return joiner.toString();
    }

    /**
     * 获取配置文件中某个属性值
     *
     * @param propertyName          属性名
     * @return                      属性值
     */
    public static String getProperty(String propertyName) {
        return SpringContextHolder.environment.getProperty(propertyName);
    }

    /**
     * 获取指定类型的属性
     *
     * @param propertyName          属性名
     * @param tClass                对象类型
     * @param <T>                   类型类型参数
     * @return                      配置对象
     */
    public static <T> T getProperty(String propertyName, Class<T> tClass) {
        return SpringContextHolder.environment.getProperty(propertyName, tClass);
    }

    /**
     * 环境匹配，可用来判断当前激活的环境
     *
     * @param profile           环境字符串
     * @return                  是否匹配
     */
    public static boolean isMatchProfile(String profile) {
        return getActiveProfile() != null && getActiveProfile().equals(profile);
    }

}
