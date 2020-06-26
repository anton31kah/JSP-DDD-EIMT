package com.example.sharedkernel.event

import com.example.sharedkernel.domain.base.DomainEvent
import java.time.Duration
import java.time.Instant


abstract class TimeEvent : DomainEvent

class GetInstantTimeEvent : TimeEvent()

data class AddOffsetTimeEvent(val duration: Duration) : TimeEvent()

data class SetOffsetTimeEvent(val duration: Duration) : TimeEvent()

data class SetTimeTimeEvent(val instant: Instant) : TimeEvent()

class ResetTimeEvent : TimeEvent()

abstract class TimeEventResponse : DomainEvent

data class GetInstantTimeEventResponse(val instant: Instant) : TimeEventResponse()

class EmptyTimeEventResponse : TimeEventResponse()
