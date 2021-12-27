package com.jlearn.auth.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;

/**
 * Spring容器
 *
 * @author dingjuru
 * @date 2021/11/17
 */
@Slf4j
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext = null;


    /**
     * 通过类获取bean，并转为类对象
     *
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> requiredType) {
        return  applicationContext.getBean(requiredType);
    }

    /**
     * 通过bean名称获取bean，并转为类对象
     * @param name
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    /**
     * 通过bean名称获取bean，并强转为类对象
     * @param name
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 获取spring配置信息
     * @param property
     * @param defaultValue
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T getProperties(String property, T defaultValue, Class<T> requiredType) {
        T result = defaultValue;

        try {
            result = getBean(Environment.class).getProperty(property, requiredType);
        } catch (Exception e) {
            log.error("获取spring配置信息异常: {}", e.getMessage());
        }

        return result;
    }

    /**
     * 获取spring配置信息
     * @param property
     * @return
     */
    public static String getProperties(String property) {
        return getProperties(property, null, String.class);
    }


    /**
     * 清楚Spring容器数据
     */
    public static void clearHolder() {
        log.debug("清除SpringContextHolder中的ApplicationContext:"
                + applicationContext);
        applicationContext = null;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringContextHolder.applicationContext != null) {
            log.warn("AuthContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:" + SpringContextHolder.applicationContext);
        }

        SpringContextHolder.applicationContext = applicationContext;

    }

    @Override
    public void destroy() throws Exception {
        SpringContextHolder.clearHolder();
    }
}
