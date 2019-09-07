# Introduction

This is simple project for testing issue with RabbitMQ STOMP plugin and auto delete queue.

# How to run

## RabbitMQ
RabbitMQ is defined using docker image. Files are in directory `rabbitmq`.

These are steps to start it:
* `docker-compose -f rabbitmq.docker-compose.yml build`
* `docker-compose -f rabbitmq.docker-compose.yml`


## Spring backend

Run backend service as Java application. For example:
* `./mvnw spring-boot:run`

It will connect to RabbitMQ using default settings.

## Client

Client code is located in directory `client`.
Open file `client.html` and then open browser console.

# How it works

1. Client html code will try to connect to `ws://localhost:8080/api/ws`.
2. Client will print on console when it is connected.
3. After it was connected, client will subscribe to `/user/queue/some-queue` with header `{'auto-delete': true}`.
4. On subscription, backend will set user with username `some-user`, as authenticated user.
4. Backend has task (`com.test.stomprabbitmq.testqueue.TestSendingTask`) that will try to send some text on that queue every 30s,
with initial delay of 30s.

# Issue
Backend will print line similar to this:

`Translated /user/queue/some-queue -> [/queue/some-queue-user8e8c6914-1b9f-1983-21db-129122fcf35f]`

and there is created auto delete queue

`/queue/some-queue-user8e8c6914-1b9f-1983-21db-129122fcf35f`

which can be verified using RabbitMQ manager (available on http://localhost:15672).

Backend will try to send message to this user and will print something like this:

`Translated /user/some-user/queue/some-queue -> [/queue/some-queue-user8e8c6914-1b9f-1983-21db-129122fcf35f]`

`Forwarding SEND /queue/some-queue-user8e8c6914-1b9f-1983-21db-129122fcf35f session=_system_ text/plain;charset=UTF-8 payload=Test payload`

`Received ERROR {message=[precondition_failed], content-type=[text/plain], version=[1.0,1.1,1.2], content-length=[170]} session=_system_ text/plain payload=PRECONDITION_FAILED - inequivalent arg 'auto_delete' for queue 'some-queue-user8...(truncated)`


This shows that application tries to send message to correct queue, but it receives error.

From [RabbitMQ STOMP Documentation](https://www.rabbitmq.com/stomp.html#queue-parameters) this should allow of creating and sending on auto delete queues.