package com.example.ridemanagement.application

import arrow.core.None
import arrow.core.Some
import arrow.core.extensions.list.foldable.nonEmpty
import arrow.core.extensions.list.monadFilter.filterMap
import arrow.core.identity
import com.example.ridemanagement.application.form.CheckRideForm
import com.example.ridemanagement.domain.model.CompletedRide
import com.example.ridemanagement.domain.repository.CompletedRideRepository
import com.example.ridemanagement.domain.repository.ConductorRepository
import com.example.sharedkernel.domain.id.ConductorId
import com.example.sharedkernel.event.sender.RideEventSender
import com.example.sharedkernel.event.sender.TicketEventSender
import com.example.sharedkernel.event.sender.TimeEventSender
import mu.KotlinLogging
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Service
@Transactional
class ConductorCheckingDevice(
        private val completedRideRepository: CompletedRideRepository,
        private val conductorRepository: ConductorRepository,
        amqpTemplate: AmqpTemplate
) {
    private val logger = KotlinLogging.logger {}

    private val timeEventService = TimeEventSender(amqpTemplate)
    private val rideEventService = RideEventSender(amqpTemplate)
    private val ticketEventService = TicketEventSender(amqpTemplate)

    fun checkRide(checkRideForm: CheckRideForm): CompletedRide {
        val now = timeEventService.getTime()

        val conductor = conductorRepository
                .findById(ConductorId(checkRideForm.conductorId))
                .orElseThrow {
                    throw IllegalArgumentException("Conductor with Id ${checkRideForm.conductorId} is not valid")
                }

        val rideId = when (val rideId = rideEventService.getRideId(checkRideForm.rideId)) {
            is Some -> rideId.t
            is None -> throw IllegalArgumentException("Ride with Id ${checkRideForm.rideId} is not valid")
        }

        run {
            val postgresTimeStampFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"))
            val alreadyCompleted = completedRideRepository.sameRidesToday(rideId.id, postgresTimeStampFormatter.format(now))

            if (alreadyCompleted) {
                throw IllegalArgumentException("Ride with Id ${checkRideForm.rideId} already completed for this time slot")
            }
        }

        if (!rideEventService.canCheckRide(rideId, now)) {
            throw IllegalArgumentException("Ride with Id ${checkRideForm.rideId} cannot be checked at this time")
        }

        val ticketsIds = checkRideForm.ticketsIds.map { it to ticketEventService.getTicketId(it) }.let { tickets ->
            val invalidTickets = tickets.filter { it.second.isEmpty() }.map { it.first }

            if (invalidTickets.nonEmpty()) {
                throw IllegalArgumentException("Tickets with Ids [${invalidTickets.joinToString()}] are not valid")
            } else {
                tickets.map { it.second }.filterMap(::identity)
            }
        }

        when (val answer = ticketEventService.canCheckAll(ticketsIds, now)) {
            is Some -> throw IllegalArgumentException(answer.t)
        }

        ticketEventService.checkAll(ticketsIds)

        val completedRide = run {
            val completedOn = now.atZone(ZoneId.of("UTC")).toLocalDateTime()

            CompletedRide(rideId, completedOn, conductor).let {
                completedRideRepository.saveAndFlush(it)
            }
        }

        return completedRide
    }
}
