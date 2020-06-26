package com.example.sharedkernel.event.sender

import org.springframework.amqp.core.AmqpTemplate

abstract class EventSenderBase(protected val template: AmqpTemplate, protected val queue: String)
