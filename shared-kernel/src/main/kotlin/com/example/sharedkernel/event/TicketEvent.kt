package com.example.sharedkernel.event

import com.example.sharedkernel.domain.base.DomainEvent

data class TicketEvent(val message: String): DomainEvent
