package com.example.ticketmanagement.application

import com.example.sharedkernel.domain.finance.Currency
import com.example.sharedkernel.domain.finance.Price
import java.time.Period

object TicketsPlans {
    val predefined = listOf(
            TicketPlan(10, Price(300, Currency.MKD), Period.ofWeeks(2)),
            TicketPlan(25, Price(700, Currency.MKD), Period.ofMonths(1)),
            TicketPlan(50, Price(1200, Currency.MKD), Period.ofMonths(3))
    )
}
