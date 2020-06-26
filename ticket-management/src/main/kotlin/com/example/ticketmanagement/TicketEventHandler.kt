package com.example.ticketmanagement

import com.example.sharedkernel.event.EventsQueues
import com.example.sharedkernel.event.TicketEvent
import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class TicketEventHandler {
    @RabbitListener(queues = [EventsQueues.tickets])
    fun listen(message: TicketEvent) {
        logger.info { message }
    }
}
