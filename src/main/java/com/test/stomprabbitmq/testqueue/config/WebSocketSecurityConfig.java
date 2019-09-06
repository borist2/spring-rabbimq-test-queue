package com.test.stomprabbitmq.testqueue.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer
{
    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages)
    {
        messages
                .simpTypeMatchers(SimpMessageType.CONNECT,
                        SimpMessageType.DISCONNECT, SimpMessageType.OTHER).permitAll()
                .simpSubscribeDestMatchers("/user/**").authenticated()
                .nullDestMatcher().authenticated()
                .anyMessage().denyAll();
    }

    @Override
    protected boolean sameOriginDisabled()
    {
        return true;
    }


}
