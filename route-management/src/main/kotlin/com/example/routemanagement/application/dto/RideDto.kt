package com.example.routemanagement.application.dto

import com.example.routemanagement.domain.model.Route
import com.example.routemanagement.domain.model.Stop
import com.example.sharedkernel.domain.id.RideId
import com.example.sharedkernel.domain.time.TimePoint
import com.example.sharedkernel.util.RideTimeHelpers
import java.time.Instant
import java.time.ZonedDateTime

data class RideDto(
        val id: RideId,
        val route: Route,
        val occurrence: TimePoint,
        val stops: List<Stop>
) {
    lateinit var occurrenceStamp: Instant

    fun assignOccurrenceStamp(currentTime: ZonedDateTime): RideDto {
        occurrenceStamp = RideTimeHelpers.timePointToInstant(occurrence, currentTime)

        return this
    }
}
