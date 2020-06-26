package com.example.ticketmanagement.application

import com.example.sharedkernel.domain.base.DomainObject
import com.example.sharedkernel.domain.finance.Price
import java.time.Duration
import java.time.Period

data class TicketPlan(val usages: Int, val price: Price, val validity: Period) : DomainObject
