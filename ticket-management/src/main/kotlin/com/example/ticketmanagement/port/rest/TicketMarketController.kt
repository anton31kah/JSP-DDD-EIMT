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
    fun getInfo(@RequestParam(name = "name", required = false, defaultValue = "!") customerName: String,
                @RequestParam(name = "id", required = false, defaultValue = "!") ticketId: String
    ): List<Ticket> {
        if (customerName != "!") {
            return ticketInfo.locateByCustomerName(customerName)
        }

        if (ticketId != "!") {
            return ticketInfo.locateByTicketId(ticketId)
        }

        return emptyList()
    }
}
