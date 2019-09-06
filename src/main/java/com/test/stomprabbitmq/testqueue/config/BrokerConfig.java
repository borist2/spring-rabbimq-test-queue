package com.test.stomprabbitmq.testqueue.config;

import com.test.stomprabbitmq.testqueue.interceptior.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Configuration
public class BrokerConfig implements WebSocketMessageBrokerConfigurer
{
    private final AuthInterceptor authInterceptor;

    public BrokerConfig(AuthInterceptor authInterceptor)
    {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration)
    {
        registration.interceptors(authInterceptor);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry)
    {
        registry.enableStompBrokerRelay("/queue");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setPreservePublishOrder(true);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry)
    {
        registry.addEndpoint("/ws").setAllowedOrigins("*");
    }
}
