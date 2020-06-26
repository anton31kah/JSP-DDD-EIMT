package com.example.ticketmanagement.application.form

import com.example.ticketmanagement.application.TicketPlan
import java.io.Serializable
import javax.validation.constraints.Pattern

data class TicketForm(
        @Pattern(regexp = """[a-zA-Z]+([a-zA-Z]+){0,5}""")
        val customerName: String,

        val ticketPlan: TicketPlan
) : Serializable
