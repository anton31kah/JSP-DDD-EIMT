package com.example.sharedkernel.event.sender

import com.example.sharedkernel.event.*
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class TimeEventSender(template: AmqpTemplate) : EventSenderBase(template, EventsQueues.time) {
    fun getTime(): Instant {
        val response = template.convertSendAndReceive(queue, GetTimeEvent()) as GetTimeEventResponse
        return response.time
    }

    fun setTime(instant: Instant) {
        template.convertSendAndReceive(queue, SetTimeEvent(instant))
    }

    fun resetTime() {
        template.convertSendAndReceive(queue, ResetTimeEvent())
    }
}
