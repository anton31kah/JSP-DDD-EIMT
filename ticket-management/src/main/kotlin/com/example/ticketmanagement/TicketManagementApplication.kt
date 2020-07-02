package com.example.ticketmanagement

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.example.ticketmanagement", "com.example.sharedkernel.scanned")
class TicketManagementApplication

fun main(args: Array<String>) {
    runApplication<TicketManagementApplication>(*args)
}
