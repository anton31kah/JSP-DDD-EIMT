package com.example.timemanagement.service

import java.time.Clock
import java.time.Duration
import java.time.Instant

object CustomTime {
    private var clock = Clock.systemUTC()

    fun instant(): Instant = clock.instant()

    fun addOffset(duration: Duration) {
        clock = Clock.offset(clock, duration)
    }

    fun setOffset(duration: Duration) {
        clock = Clock.offset(Clock.systemUTC(), duration)
    }

    fun setTime(instant: Instant) {
        setOffset(Duration.between(instant(), instant))
    }

    fun reset() {
        clock = Clock.systemUTC()
    }
}
