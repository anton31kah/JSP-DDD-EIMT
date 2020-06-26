package com.example.sharedkernel.event

import com.example.sharedkernel.domain.base.DomainEvent
import com.example.sharedkernel.domain.id.ConductorId
import com.example.sharedkernel.domain.id.RideId
import com.example.sharedkernel.domain.id.TicketId
import java.time.LocalDateTime

data class CheckRideEvent(
        val ride: RideId,
        val completedOn: LocalDateTime,
        val conductor: ConductorId,
        val tickets: List<TicketId>
) : DomainEvent
