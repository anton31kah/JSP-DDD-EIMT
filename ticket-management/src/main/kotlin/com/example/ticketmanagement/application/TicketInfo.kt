package com.example.ticketmanagement.application

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
}
