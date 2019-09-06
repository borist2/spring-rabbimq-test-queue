package com.test.stomprabbitmq.testqueue;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TestSendingTask
{
    private final SimpMessagingTemplate simpMessagingTemplate;

    public TestSendingTask(SimpMessagingTemplate simpMessagingTemplate)
    {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Scheduled(fixedRate = 30000, initialDelay = 30000)
    public void sendSomething ()
    {
        simpMessagingTemplate.convertAndSendToUser("some-user","/queue/some-queue","Test payload");
    }
}
