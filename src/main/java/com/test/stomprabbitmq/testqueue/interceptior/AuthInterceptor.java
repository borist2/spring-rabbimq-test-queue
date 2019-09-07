package com.test.stomprabbitmq.testqueue.interceptior;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthInterceptor implements ChannelInterceptor
{
    private final AuthenticationManager authenticationManager;

    public AuthInterceptor(AuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel)
    {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("some-user", "user1234"));

        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null)
        {
            accessor.setUser(auth);
        }

        return message;
    }

}
