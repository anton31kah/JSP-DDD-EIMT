package com.example.sharedkernel.domain.time

import com.example.sharedkernel.domain.base.ValueObject
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
data class Day(
        @Column(name = "day_of_week", nullable = false)
        @Enumerated(EnumType.STRING)
        var day: DayOfWeek
) : ValueObject
