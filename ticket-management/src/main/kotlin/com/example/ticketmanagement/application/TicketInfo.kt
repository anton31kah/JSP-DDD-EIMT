package com.example.ticketmanagement.application

import com.example.sharedkernel.domain.id.TicketId
import com.example.ticketmanagement.domain.model.Ticket
import com.example.ticketmanagement.domain.repository.TicketRepository
import org.springframework.stereotype.Service

@Service
class TicketInfo(
        private val ticketRepository: TicketRepository
) {
    fun locateByCustomerName(customerName: String): List<Ticket> {
        return ticketRepository.findByCustomerName(customerName)
    }

    fun locateByTicketId(ticketId: String): List<Ticket> {
        return ticketRepository.findById(TicketId(ticketId))
                .map { listOf(it) }
                .orElse(emptyList())
    }

}
