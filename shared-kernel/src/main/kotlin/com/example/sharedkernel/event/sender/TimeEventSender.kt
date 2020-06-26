package com.example.sharedkernel.event.sender

import com.example.sharedkernel.event.*
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant

@Component
class TimeEventSender(template: AmqpTemplate) : EventSenderBase(template, EventsQueues.time) {
    fun instant(): Instant {
        val response = template.convertSendAndReceive(queue, GetInstantTimeEvent()) as GetInstantTimeEventResponse
        return response.instant
    }

    fun addOffset(duration: Duration) {
        template.convertSendAndReceive(queue, AddOffsetTimeEvent(duration))
    }

    fun setOffset(duration: Duration) {
        template.convertSendAndReceive(queue, SetOffsetTimeEvent(duration))
    }

    fun setTime(instant: Instant) {
        template.convertSendAndReceive(queue, SetTimeTimeEvent(instant))
    }

    fun reset() {
        template.convertSendAndReceive(queue, ResetTimeEvent())
    }
}
