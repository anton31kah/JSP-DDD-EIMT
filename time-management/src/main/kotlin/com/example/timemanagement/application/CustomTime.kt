package com.example.timemanagement.application

import java.time.Clock
import java.time.Duration
import java.time.Instant

object CustomTime {
    private var clock = Clock.systemUTC()

    fun getTime(): Instant = clock.instant()

    fun setTime(newTime: Instant) {
        val utcClock = Clock.systemUTC()
        val duration = Duration.between(utcClock.instant(), newTime)
        clock = Clock.offset(utcClock, duration)
    }

    fun resetTime() {
        clock = Clock.systemUTC()
    }
}
