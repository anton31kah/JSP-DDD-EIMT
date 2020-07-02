package com.example.sharedkernel.event

import arrow.core.Option
import com.example.sharedkernel.domain.base.DomainEvent
import com.example.sharedkernel.domain.id.TicketId
import java.time.Instant

sealed class TicketEvent : DomainEvent {

    data class GetTicketId(val ticketId: String) : TicketEvent()

    data class CanCheckAll(val ticketsIds: Array<TicketId>, val atTime: Instant) : TicketEvent()

    data class CheckAll(val ticketsIds: Array<TicketId>) : TicketEvent()

    sealed class Response : DomainEvent {

        data class GetTicketId(val ticketId: Option<TicketId>) : Response() {
            fun convert(): GetTicketIdSerializable {
                return GetTicketIdSerializable(ticketId.orNull())
            }
        }

        data class GetTicketIdSerializable(val ticketId: TicketId?) : Response() {
            fun convert(): GetTicketId {
                return GetTicketId(Option.fromNullable(ticketId))
            }
        }

        sealed class CanCheckAll : Response() {

            data class No(val reason: String) : CanCheckAll()

            object Yes : CanCheckAll()

        }

        object Empty : Response()

    }

}
