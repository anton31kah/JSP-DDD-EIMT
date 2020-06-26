package com.example.sharedkernel.domain.time

import com.example.sharedkernel.domain.base.ValueObject
import javax.persistence.*

@Embeddable
data class TimePoint(
        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "hours", column = Column(name = "time_hours", nullable = false)),
                AttributeOverride(name = "minutes", column = Column(name = "time_minutes", nullable = false))
        )
        var time: Time,

        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "day", column = Column(name = "day_of_week", nullable = false))
        )
        var day: Day
) : ValueObject
