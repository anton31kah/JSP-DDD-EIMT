package com.example.ticketmanagement.port.rest

import com.example.ticketmanagement.application.TicketInfo
import com.example.ticketmanagement.application.TicketMarket
import com.example.ticketmanagement.application.TicketPlan
import com.example.ticketmanagement.application.form.TicketForm
import com.example.ticketmanagement.domain.model.Ticket
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
class TicketMarketController(
        private val ticketMarket: TicketMarket,
        private val ticketInfo: TicketInfo
) {
    @GetMapping("/plans")
    fun getPlans(): List<TicketPlan> {
        return ticketMarket.availablePlans()
    }

    @PostMapping
    fun purchaseTicket(@RequestBody form: TicketForm): Ticket {
        return ticketMarket.purchaseTicket(form)
    }

    @GetMapping
    fun getInfo(@RequestParam(name = "name") customerName: String): List<Ticket> {
        return ticketInfo.locateByCustomerName(customerName)
    }
}
