package com.example.sharedkernel.event.sender

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import com.example.sharedkernel.domain.id.TicketId
import com.example.sharedkernel.event.EventsQueues
import com.example.sharedkernel.event.TicketEvent
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class TicketEventSender(template: AmqpTemplate) : EventSenderBase(template, EventsQueues.tickets) {
    fun getTicketId(ticketId: String): Option<TicketId> {
        val response = template.convertSendAndReceive(queue, TicketEvent.GetTicketId(ticketId)) as TicketEvent.Response.GetTicketIdSerializable
        return response.convert().ticketId
    }

    fun canCheckAll(ticketsIds: List<TicketId>, atTime: Instant): Option<String> {
        return when (val response = (template.convertSendAndReceive(queue, TicketEvent.CanCheckAll(ticketsIds.toTypedArray(), atTime)) as TicketEvent.Response.CanCheckAll)) {
            is TicketEvent.Response.CanCheckAll.No -> Some(response.reason)
            is TicketEvent.Response.CanCheckAll.Yes -> None
        }
    }

    fun checkAll(ticketsIds: List<TicketId>) {
        template.convertSendAndReceive(queue, TicketEvent.CheckAll(ticketsIds.toTypedArray()))
    }
}
