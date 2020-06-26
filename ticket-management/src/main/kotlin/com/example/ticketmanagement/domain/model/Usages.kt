package com.example.ticketmanagement.domain.model

import com.example.sharedkernel.domain.base.ValueObject
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Usages(
        @Column(name = "initial_usages", nullable = false)
        val initial: Int,

        @Column(name = "left_usages", nullable = false)
        var left: Int
) : ValueObject

