package com.example.ticketmanagement.application

import com.example.sharedkernel.event.EventsQueues
import com.example.sharedkernel.event.TicketEvent
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class TicketEventHandler(
        private val ticketChecker: TicketChecker
) {
    @RabbitListener(queues = [EventsQueues.tickets])
    fun listen(event: TicketEvent): TicketEvent.Response {
        return when (event) {
            is TicketEvent.GetTicketId -> TicketEvent.Response.GetTicketId(ticketChecker.findTicketId(event.ticketId)).convert()
            is TicketEvent.CanCheckAll -> ticketChecker.canAllBeChecked(event.ticketsIds.toList(), event.atTime).fold(
                    { TicketEvent.Response.CanCheckAll.No(it) },
                    { TicketEvent.Response.CanCheckAll.Yes })//.convert()

            is TicketEvent.CheckAll -> {
                ticketChecker.checkAll(event.ticketsIds.toList())
                TicketEvent.Response.Empty
            }
        }
    }
}
