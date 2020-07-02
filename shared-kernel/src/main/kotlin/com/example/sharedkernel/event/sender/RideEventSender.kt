package com.example.sharedkernel.event.sender

import arrow.core.Option
import com.example.sharedkernel.domain.id.RideId
import com.example.sharedkernel.event.EventsQueues
import com.example.sharedkernel.event.RideEvent
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class RideEventSender(template: AmqpTemplate) : EventSenderBase(template, EventsQueues.rides) {

    fun getRideId(rideId: String): Option<RideId> {
        val response = template.convertSendAndReceive(queue, RideEvent.GetRideId(rideId)) as RideEvent.Response.GetRideIdSerializable
        return response.convert().rideId
    }

    fun canCheckRide(rideId: RideId, atTime: Instant): Boolean {
        val response = template.convertSendAndReceive(queue, RideEvent.CanCheck(rideId, atTime)) as RideEvent.Response.CanCheck
        return response.answer
    }
}
