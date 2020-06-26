package com.example.timemanagement.service

import com.example.sharedkernel.event.*
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class TimeEventHandler {
    @RabbitListener(queues = [EventsQueues.time])
    fun listen(event: TimeEvent): TimeEventResponse {
        when (event) {
            is GetInstantTimeEvent -> return GetInstantTimeEventResponse(CustomTime.instant())
            is AddOffsetTimeEvent -> CustomTime.addOffset(event.duration)
            is SetOffsetTimeEvent -> CustomTime.setOffset(event.duration)
            is SetTimeTimeEvent -> CustomTime.setTime(event.instant)
            is ResetTimeEvent -> CustomTime.reset()
        }

        return EmptyTimeEventResponse()
    }
}
