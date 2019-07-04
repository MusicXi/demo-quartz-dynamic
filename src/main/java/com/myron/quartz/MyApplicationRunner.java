package com.myron.quartz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.myron.quartz.bean.QrtzJobDetails;
import com.myron.quartz.service.QrtzJobDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component//被spring容器管理
@Order(1)//如果多个自定义ApplicationRunner，用来标明执行顺序
public class MyApplicationRunner implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyApplicationRunner.class);
    @Autowired
    private QrtzJobDetailsService qrtzJobDetailsService;
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        QrtzJobDetails qrtzJobDetails = new QrtzJobDetails();
        qrtzJobDetails.setJobName("helloService.sayHello");
        qrtzJobDetails.setCronExpression("*/5 * * * * ?");
        qrtzJobDetails.setDescription("测试任务");
        QrtzJobDetails qrtzJobDetails1 = new QrtzJobDetails();
        qrtzJobDetails1.setJobName("helloService.sayBye");
        qrtzJobDetails1.setCronExpression("*/15 * * * * ?");
        qrtzJobDetails1.setDescription("测试任务111111");
        LOGGER.info("add default time job:{}", JSON.toJSONString(qrtzJobDetails, SerializerFeature.PrettyFormat));
        LOGGER.info("add default time job:{}", JSON.toJSONString(qrtzJobDetails1, SerializerFeature.PrettyFormat));
        qrtzJobDetailsService.createQrtzJobDetails(qrtzJobDetails);
        qrtzJobDetailsService.createQrtzJobDetails(qrtzJobDetails1);

    }

}