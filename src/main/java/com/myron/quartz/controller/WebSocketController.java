package com.myron.quartz.controller;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.myron.quartz.bean.CustomLoggingEvent;
import com.myron.quartz.logger.filter.LoggerQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api")
public class WebSocketController {

	@Autowired
    SimpMessagingTemplate messagingTemplate;

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


	@GetMapping("/websocket/print/{msg}")
	public String print(@PathVariable  String msg) {
		messagingTemplate.convertAndSend("/topic/print",msg);
		return msg;
	}

	@PostConstruct
	public void pushLogger(){
		ExecutorService executorService= Executors.newFixedThreadPool(2);
		Runnable runnable=new Runnable() {
			@Override
			public void run() {
				CustomLoggingEvent customLoggingEvent = new CustomLoggingEvent();
				while (true) {
					try {
						ILoggingEvent log = LoggerQueue.getInstance().poll();
						if (log != null) {
							if (messagingTemplate != null) {
								customLoggingEvent.setTimeStamp(format.format(new Date(log.getTimeStamp())));
								customLoggingEvent.setLevel(log.getLevel().levelStr);
								customLoggingEvent.setThreadName(log.getThreadName());
								customLoggingEvent.setLoggerName(log.getLoggerName());
								customLoggingEvent.setFormattedMessage(log.getFormattedMessage());
								messagingTemplate.convertAndSend("/topic/print", customLoggingEvent);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		executorService.submit(runnable);
	}

}