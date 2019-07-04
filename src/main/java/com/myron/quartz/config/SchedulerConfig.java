package com.myron.quartz.config;

import java.io.IOException;
import java.util.Properties;

import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class SchedulerConfig {

    @Resource(name="dataSource")
    private DataSource dataSource;

    @Bean(name="SchedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setQuartzProperties(quartzProperties());
        factory.setApplicationContextSchedulerContextKey("applicationContextKey");
        factory.setDataSource(dataSource);
        return factory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /*
     * quartz初始化监听器
     */
//    @Bean
//    public QuartzInitializerListener executorListener() {
//       return new QuartzInitializerListener();
//    }

    /*
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
//    @Bean(name="Scheduler")
//    public Scheduler scheduler() throws IOException {
//        return schedulerFactoryBean().getScheduler();
//    }

}
