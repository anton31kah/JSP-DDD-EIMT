package com.example.sharedkernel.config

import com.example.sharedkernel.event.EventsQueues
import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {
    @Bean
    fun timeQueue(): Queue {
        return Queue(EventsQueues.time)
    }

    @Bean
    fun ticketsQueue(): Queue {
        return Queue(EventsQueues.tickets)
    }
}
