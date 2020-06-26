package com.example.ticketmanagement.domain.repository

import com.example.sharedkernel.domain.id.TicketId
import com.example.ticketmanagement.domain.model.Ticket
import org.springframework.data.jpa.repository.JpaRepository

interface TicketRepository : JpaRepository<Ticket, TicketId> {
    fun findByCustomerName(customerName: String): List<Ticket>
}
