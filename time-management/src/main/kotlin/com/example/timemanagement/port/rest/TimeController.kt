package com.example.timemanagement.port.rest

import com.example.sharedkernel.event.sender.TimeEventSender
import mu.KotlinLogging
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.ZonedDateTime


@RestController
@CrossOrigin(origins = ["*"])
class TimeController(amqpTemplate: AmqpTemplate) {
    private val logger = KotlinLogging.logger {}

    val timeEventService = TimeEventSender(amqpTemplate)

    @GetMapping
    fun getTime(): Instant {
        val now = timeEventService.getTime()
        return now
    }

    @PostMapping
    fun setTime(@RequestBody newTime: NewTimeDto): Map<String, Instant> {
        val newInstant = ZonedDateTime.parse(newTime.newTime).toInstant()

        val before = timeEventService.getTime()
        timeEventService.setTime(newInstant)
        val after = timeEventService.getTime()
        return mapOf("before" to before, "after" to after)
    }

    @DeleteMapping
    fun resetTime(): Map<String, Instant> {
        val before = timeEventService.getTime()
        timeEventService.resetTime()
        val after = timeEventService.getTime()
        return mapOf("before" to before, "after" to after)
    }
}
