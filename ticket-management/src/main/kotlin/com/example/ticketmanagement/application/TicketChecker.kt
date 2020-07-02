package com.example.ticketmanagement.application

import arrow.core.*
import com.example.sharedkernel.domain.id.TicketId
import com.example.ticketmanagement.domain.model.Ticket
import com.example.ticketmanagement.domain.repository.TicketRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.ZoneId

@Service
class TicketChecker(
        private val ticketRepository: TicketRepository
) {
    fun findTicketId(ticketId: String): Option<TicketId> {
        return ticketRepository.findById(TicketId(ticketId))
                .map { it.id }
                .orElse(null)
                .toOption()
    }

    fun canAllBeChecked(ticketsIds: List<TicketId>, atTime: Instant): Either<String, None> {
        val tickets = ticketsIds
                .map { ticketRepository.findById(it) }
                .let { dbTicketsIds ->
                    if (dbTicketsIds.any { it.isEmpty }) {
                        return Left("One of the tickets has an invalid id")
                    } else {
                        dbTicketsIds.map { it.get() }
                    }
                }
        val nowDate = atTime.atZone(ZoneId.of("UTC")).toLocalDate()
        if (tickets.any { nowDate.isAfter(it.expiryDate) }) {
            return Left("One of the tickets is expired")
        }

        if (tickets.map { it.customer.name }.distinct().size != tickets.size) {
            return Left("One or more tickets belong to the same customer")
        }

        if (tickets.any { it.usages.left <= 0 }) {
            return Left("One or more tickets don't have enough usages left for a ride")
        }

        return Right(None)
    }

    fun checkAll(ticketsIds: List<TicketId>): List<Ticket> {
        return ticketRepository.saveAll(
                ticketsIds.map {
                    ticketRepository.findById(it).get()
                            .apply {
                                checkOnce()
                            }
                }
        )
    }
}
