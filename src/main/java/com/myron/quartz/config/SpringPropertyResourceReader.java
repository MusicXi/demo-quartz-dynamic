package com.myron.quartz.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.*;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Properties;

/**
 * 读取spring容器加载的属性配置文件
 * @author linrx
 */
@Component
public class SpringPropertyResourceReader implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    /** 配置文件中原始属性带占位符*/
    private static Properties properties = new Properties();
    private static PropertySourcesPropertyResolver propertySourcesPropertyResolver = null;

    /** 获取上下文属性*/
    private static void init() {
        try {
            Environment env = applicationContext.getEnvironment();
            StandardEnvironment standardServletEnvironment = (StandardEnvironment) env;
            MutablePropertySources mutablePropertySources = standardServletEnvironment.getPropertySources();
            Iterator<PropertySource<?>> iterator =  mutablePropertySources.iterator();
            while (iterator.hasNext()) {
                PropertySource propertySource = iterator.next();
                Object source =  propertySource.getSource();
                if (source instanceof Properties) {
                    Properties prop = (Properties) propertySource.getSource();
                    properties.putAll(prop);
                }
            }
             propertySourcesPropertyResolver = new PropertySourcesPropertyResolver(standardServletEnvironment.getPropertySources());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取配置文件原始属性值
     * @param propertyName
     * @return
     */
    public static String getPropertyRaw(String propertyName) {
        return properties.getProperty(propertyName);
    }

    /**
     * 生效属性真实值
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return  propertySourcesPropertyResolver.getProperty(key);
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
        init();
    }
}