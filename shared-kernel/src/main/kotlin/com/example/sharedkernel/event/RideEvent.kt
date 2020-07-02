package com.example.sharedkernel.event

import arrow.core.Option
import com.example.sharedkernel.domain.base.DomainEvent
import com.example.sharedkernel.domain.id.RideId
import java.time.Instant

sealed class RideEvent : DomainEvent {

    data class GetRideId(val rideId: String) : RideEvent()

    data class CanCheck constructor(val rideId: RideId, val atTime: Instant) : RideEvent()

    sealed class Response : DomainEvent {

        data class GetRideId(val rideId: Option<RideId>) : Response() {
            fun convert(): GetRideIdSerializable {
                return GetRideIdSerializable(rideId.orNull())
            }
        }

        data class GetRideIdSerializable(val rideId: RideId?) : Response() {
            fun convert(): GetRideId {
                return GetRideId(Option.fromNullable(rideId))
            }
        }

        data class CanCheck(val answer: Boolean) : Response()

    }

}
