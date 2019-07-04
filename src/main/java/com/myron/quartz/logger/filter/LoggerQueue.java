package com.myron.quartz.logger.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LoggerQueue {
    //队列大小
    public static final int QUEUE_MAX_SIZE = 10000;
    private static LoggerQueue alarmMessageQueue = new LoggerQueue();
    //阻塞队列
    private BlockingQueue blockingQueue = new LinkedBlockingQueue<>(QUEUE_MAX_SIZE);

    private LoggerQueue() {
    }

    public static LoggerQueue getInstance() {
        return alarmMessageQueue;
    }

    /**
     * 消息入队
     *
     * @param log
     * @return
     */
    public boolean push(ILoggingEvent log) {
        //队列满了就抛出异常，不阻塞
        return this.blockingQueue.add(log);
    }

    /**
     * 消息出队
     *
     * @return
     */
    public ILoggingEvent poll() {
        ILoggingEvent result = null;
        try {
            result = (ILoggingEvent) this.blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}