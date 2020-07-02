package com.example.timemanagement

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.example.timemanagement", "com.example.sharedkernel.scanned")
class TimeManagementApplication

fun main(args: Array<String>) {
    runApplication<TimeManagementApplication>(*args)
}
