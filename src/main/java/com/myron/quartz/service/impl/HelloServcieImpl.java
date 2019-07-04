package com.myron.quartz.service.impl;

import com.myron.quartz.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("helloService")
public class HelloServcieImpl implements HelloService {
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloServcieImpl.class);
	@Override
	public void sayHello() {
		LOGGER.info("hello world, i am quartz");
		
	}

	@Override
	public void sayBye() throws Exception {
		LOGGER.info("see you tomorrow!");
		throw new Exception("模拟业务异常");
	}

}
