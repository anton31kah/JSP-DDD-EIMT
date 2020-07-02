package com.example.ridemanagement

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.example.ridemanagement", "com.example.sharedkernel.scanned")
class RideManagementApplication

fun main(args: Array<String>) {
    runApplication<RideManagementApplication>(*args)
}
