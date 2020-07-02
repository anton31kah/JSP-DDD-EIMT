package com.example.timemanagement.application

import com.example.sharedkernel.event.EventsQueues
import com.example.sharedkernel.event.TimeEvent
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class TimeEventHandler {
    @RabbitListener(queues = [EventsQueues.time])
    fun listen(event: TimeEvent): TimeEvent.Response {
        when (event) {
            is TimeEvent.Get -> return TimeEvent.Response.Get(CustomTime.getTime())
            is TimeEvent.Set -> CustomTime.setTime(event.newTime)
            is TimeEvent.Reset -> CustomTime.resetTime()
        }

        return TimeEvent.Response.Empty
    }
}
