package com.example.sharedkernel.event

import com.example.sharedkernel.domain.base.DomainEvent
import java.time.Instant


sealed class TimeEvent : DomainEvent {

    object Get : TimeEvent()

    data class Set(val newTime: Instant) : TimeEvent()

    object Reset : TimeEvent()

    sealed class Response : DomainEvent {

        data class Get(val time: Instant) : Response()

        object Empty : Response()

    }
}
