package com.example.sharedkernel.event.sender

import org.springframework.stereotype.Component

@Component
class EventsSenders(
        val time: TimeEventSender,
        val rides: RideEventSender,
        val tickets: TicketEventSender
)
