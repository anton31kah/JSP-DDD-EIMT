package com.example.ticketmanagement.application

import com.example.sharedkernel.event.EventsQueues
import com.example.sharedkernel.event.TicketEvent
import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class TicketEventHandler {
    private val logger = KotlinLogging.logger {}

    @RabbitListener(queues = [EventsQueues.tickets])
    fun listen(message: TicketEvent) {
        logger.info { message }
    }
}
