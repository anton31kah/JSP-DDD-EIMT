package com.example.sharedkernel.util

import com.example.sharedkernel.domain.time.TimePoint
import java.time.*
import java.time.temporal.TemporalAdjusters

object RideTimeHelpers {
    fun timePointToInstant(timePoint: TimePoint, currentTime: ZonedDateTime): Instant {
        val dayOfWeek = DayOfWeek.valueOf(timePoint.day.day.name.toUpperCase())
        val timeInDay = LocalTime.of(timePoint.time.hours, timePoint.time.minutes)

        val temporalAdjuster =
                if (dayOfWeek < currentTime.dayOfWeek) TemporalAdjusters.previous(dayOfWeek)
                else /*if (dayOfWeek >= currentTime.dayOfWeek)*/ TemporalAdjusters.nextOrSame(dayOfWeek)

        return ZonedDateTime.now(ZoneId.systemDefault())
                .with(timeInDay)
                .withZoneSameInstant(ZoneOffset.UTC)
                .with(temporalAdjuster)
                .toInstant()
    }
}
