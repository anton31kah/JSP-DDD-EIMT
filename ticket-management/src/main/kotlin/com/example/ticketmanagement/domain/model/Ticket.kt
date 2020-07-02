package com.example.ticketmanagement.domain.model

import com.example.sharedkernel.domain.base.AbstractEntity
import com.example.sharedkernel.domain.base.DomainObjectId
import com.example.sharedkernel.domain.id.TicketId
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tickets")
data class Ticket(
        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "name", column = Column(name = "customer_name", nullable = false))
        )
        val customer: Customer,

        @Column(nullable = false)
        val boughtOn: LocalDateTime,

        @Column(nullable = false)
        val expiryDate: LocalDate,

        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "initial", column = Column(name = "initial_usages", nullable = false)),
                AttributeOverride(name = "left", column = Column(name = "left_usages", nullable = false))
        )
        val usages: Usages
) : AbstractEntity<TicketId>(DomainObjectId.randomId(TicketId::class.java)) {

    fun checkOnce() {
        usages.left--
    }
}
