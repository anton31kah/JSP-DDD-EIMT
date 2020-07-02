package com.example.routemanagement.application

import com.example.routemanagement.application.dto.RideDto
import com.example.routemanagement.domain.repository.RideRepository
import com.example.sharedkernel.domain.time.TimePoint
import com.example.sharedkernel.event.sender.TimeEventSender
import com.example.sharedkernel.util.RideTimeHelpers
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.DayOfWeek as JavaDayOfWeek

@Service
@Transactional
class RidesSchedule(
        private val rideRepository: RideRepository,
        amqpTemplate: AmqpTemplate
) {
    private val timeEventService = TimeEventSender(amqpTemplate)

    fun nextWeekRides(): List<RideDto> {
        val all = rideRepository.findAll()
        val flattened = all.flatMap { ride ->
            ride.occurrences.map { time ->
                RideDto(ride.id, ride.route, time, ride.stops)
            }
        }

        val currentTime = timeEventService.getTime().atZone(ZoneId.of("UTC"))

        val nextWeekOnly = flattened.filter { isInNextWeek(it.occurrence, currentTime) }
                .map { it.assignOccurrenceStamp(currentTime) }
                .sortedWith(compareBy(
                        { it.occurrence.day.day.ordinal },
                        { it.occurrence.time.hours },
                        { it.occurrence.time.minutes }
                ))

        return nextWeekOnly
    }

    private fun isInNextWeek(timeToCheck: TimePoint, currentTime: ZonedDateTime): Boolean {
        val dayOfWeek = JavaDayOfWeek.valueOf(timeToCheck.day.day.name.toUpperCase())

        if (dayOfWeek < currentTime.dayOfWeek) {
            return false
        }

        val time = RideTimeHelpers.timePointToInstant(timeToCheck, currentTime)

        return time.isAfter(currentTime.toInstant())
    }
}
