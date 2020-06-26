package com.example.sharedkernel.domain.time

import com.example.sharedkernel.domain.base.ValueObject
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Time(
        @Column(nullable = false)
        var hours: Int,

        @Column(nullable = false)
        var minutes: Int
) : ValueObject
