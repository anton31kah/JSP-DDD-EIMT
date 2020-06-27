package com.example.ticketmanagement.application

import com.example.sharedkernel.event.sender.TimeEventSender
import com.example.ticketmanagement.application.form.TicketForm
import com.example.ticketmanagement.domain.model.Customer
import com.example.ticketmanagement.domain.model.Ticket
import com.example.ticketmanagement.domain.model.Usages
import com.example.ticketmanagement.domain.repository.TicketRepository
import mu.KotlinLogging
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZoneId
import javax.validation.ConstraintViolationException
import javax.validation.Validator

@Service
@Transactional
class TicketMarket(
        private val ticketRepository: TicketRepository,
        private val validator: Validator,
        amqpTemplate: AmqpTemplate
) {
    private val logger = KotlinLogging.logger {}

    private val timeEventService = TimeEventSender(amqpTemplate)

    fun availablePlans(): List<TicketPlan> = TicketsPlans.predefined

    fun purchaseTicket(ticketForm: TicketForm): Ticket {
        val constraintViolations = validator.validate(ticketForm)

        if (constraintViolations.size > 0) {
            throw ConstraintViolationException("The TicketForm is not valid", constraintViolations)
        }

        if (ticketForm.ticketPlan !in TicketsPlans.predefined) {
            throw ConstraintViolationException("The chosen ticket plan is not valid", emptySet())
        }

        val newTicket = ticketRepository.saveAndFlush(toDomainModel(ticketForm))

        // no need to send anything
        logger.info { newTicket.id.id }

        return newTicket
    }

    private fun toDomainModel(ticketForm: TicketForm): Ticket {
        val customer = Customer(ticketForm.customerName)
        val boughtOn = LocalDateTime.ofInstant(timeEventService.getTime(), ZoneId.of("UTC"))
        val expiryDate = boughtOn.plus(ticketForm.ticketPlan.validity).toLocalDate()
        val usages = Usages(ticketForm.ticketPlan.usages, ticketForm.ticketPlan.usages)

        return Ticket(customer, boughtOn, expiryDate, usages)
    }
}
