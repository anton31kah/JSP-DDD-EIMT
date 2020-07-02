package com.example.sharedkernel.event.sender

import com.example.sharedkernel.event.EventsQueues
import com.example.sharedkernel.event.TimeEvent
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class TimeEventSender(template: AmqpTemplate) : EventSenderBase(template, EventsQueues.time) {
    fun getTime(): Instant {
        val response = template.convertSendAndReceive(queue, TimeEvent.Get) as TimeEvent.Response.Get
        return response.time
    }

    fun setTime(instant: Instant) {
        template.convertSendAndReceive(queue, TimeEvent.Set(instant))
    }

    fun resetTime() {
        template.convertSendAndReceive(queue, TimeEvent.Reset)
    }
}
