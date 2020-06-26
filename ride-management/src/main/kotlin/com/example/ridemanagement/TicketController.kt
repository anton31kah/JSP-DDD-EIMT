package com.example.ridemanagement

import com.example.sharedkernel.event.EventsQueues
import com.example.sharedkernel.event.TicketEvent
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
class TicketController(val template: AmqpTemplate) {
    @PostMapping("/")
    fun send(@RequestParam message: String) {
        template.convertAndSend(EventsQueues.tickets, TicketEvent(message))
    }
}
