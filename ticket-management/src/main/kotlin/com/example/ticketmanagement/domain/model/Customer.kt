package com.example.ticketmanagement.domain.model

import com.example.sharedkernel.domain.base.ValueObject
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Customer(
        @Column(name = "customer_name", nullable = false)
        var name: String
) : ValueObject
