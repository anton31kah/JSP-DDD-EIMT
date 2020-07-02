package com.example.routemanagement.application

import com.example.sharedkernel.event.EventsQueues
import com.example.sharedkernel.event.RideEvent
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RideEventHandler(
        private val rideChecker: RideChecker
) {

    @RabbitListener(queues = [EventsQueues.rides])
    fun listen(event: RideEvent): RideEvent.Response {
        return when (event) {
            is RideEvent.GetRideId -> RideEvent.Response.GetRideId(rideChecker.findRideId(event.rideId)).convert()
            is RideEvent.CanCheck -> RideEvent.Response.CanCheck(rideChecker.canBeChecked(event.rideId, event.atTime))
        }
    }
}
