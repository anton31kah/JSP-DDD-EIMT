package com.example.sharedkernel.event

import com.example.sharedkernel.domain.base.DomainEvent
import java.time.Instant


abstract class TimeEvent : DomainEvent

class GetTimeEvent : TimeEvent()

data class SetTimeEvent(val newTime: Instant) : TimeEvent()

class ResetTimeEvent : TimeEvent()

abstract class TimeEventResponse : DomainEvent

data class GetTimeEventResponse(val time: Instant) : TimeEventResponse()

class EmptyTimeEventResponse : TimeEventResponse()
