package com.example.ridemanagement

import com.example.sharedkernel.event.sender.TimeEventSender
import mu.KotlinLogging
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration
import java.time.Instant


private val logger = KotlinLogging.logger {}

@RestController
class TimeController(amqpTemplate: AmqpTemplate) {
    val timeEventService = TimeEventSender(amqpTemplate)

    @GetMapping
    fun getTime(): Instant {
        val now = Instant.now()
        logger.info { now }
        return now
    }

    @PostMapping("/clockAddDay")
    fun setTime(): Map<String, Instant> {
        val before = timeEventService.instant()
        timeEventService.addOffset(Duration.ofDays(1))
//        Thread.sleep(2000)
        val after = timeEventService.instant()
        return mapOf("before" to before, "after" to after)
    }
}
