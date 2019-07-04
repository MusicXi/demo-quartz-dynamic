package com.myron.quartz.config;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //在 WebSocket 上启用 STOMP
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

	/**
	 * stomp 协议，一种格式比较简单且被广泛支持的通信协议，spring4提供了以stomp协议为基础的websocket通信实现。
	 * spring 的websocket实现，实际上是一个简易版的消息队列（而且是主题-订阅模式的）
	 * @param registry
	 */
	@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
		// "/websocket"，客户端需要注册这个端点进行链接，
		// .withSockJS()的作用是声明我们想要使用 SockJS 功能，如果WebSocket不可用的话，会使用 SockJS。
		registry.addEndpoint("/websocket").setAllowedOrigins("*").withSockJS();
	}

/*	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(new ChannelInterceptor() {
			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				StompHeaderAccessor accessor =
						MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
				if (StompCommand.CONNECT.equals(accessor.getCommand())) {
					Authentication user = ... ; // access authentication header(s)
					accessor.setUser(user);
				}
				return message;
			}
		});
	}*/

}