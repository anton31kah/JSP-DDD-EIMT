package com.example.timemanagement.application

import com.example.sharedkernel.event.*
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class TimeEventHandler {
    @RabbitListener(queues = [EventsQueues.time])
    fun listen(event: TimeEvent): TimeEventResponse {
        when (event) {
            is GetTimeEvent -> return GetTimeEventResponse(CustomTime.getTime())
            is SetTimeEvent -> CustomTime.setTime(event.newTime)
            is ResetTimeEvent -> CustomTime.resetTime()
        }

        return EmptyTimeEventResponse()
    }
}
