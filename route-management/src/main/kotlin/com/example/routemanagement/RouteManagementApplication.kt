package com.example.routemanagement

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.example.routemanagement", "com.example.sharedkernel.scanned")
class RouteManagementApplication

fun main(args: Array<String>) {
    runApplication<RouteManagementApplication>(*args)
}
