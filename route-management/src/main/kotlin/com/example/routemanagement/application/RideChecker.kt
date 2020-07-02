package com.example.routemanagement.application

import arrow.core.Option
import arrow.core.toOption
import com.example.routemanagement.domain.repository.RideRepository
import com.example.sharedkernel.domain.id.RideId
import com.example.sharedkernel.util.RideTimeHelpers
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit

@Service
class RideChecker(
        private val rideRepository: RideRepository
) {
    private val logger = KotlinLogging.logger {}

    fun findRideId(rideId: String): Option<RideId> {
        return rideRepository.findById(RideId(rideId))
                .map { it.id }
                .orElse(null)
                .toOption()
    }

    fun canBeChecked(rideId: RideId, atTime: Instant): Boolean {
        val ride = rideRepository.findById(rideId).let {
            if (it.isEmpty) return false
            it.get()
        }

        val zonedNow = atTime.atZone(ZoneId.of("UTC"))
        val answer = ride.occurrences.any { timePoint ->
            val instant = RideTimeHelpers.timePointToInstant(timePoint, zonedNow)

            timePoint.day.day.name.toLowerCase() == zonedNow.dayOfWeek.name.toLowerCase()
                    && ChronoUnit.MINUTES.between(atTime, instant) in -60..15
        }

        return answer
    }
}
