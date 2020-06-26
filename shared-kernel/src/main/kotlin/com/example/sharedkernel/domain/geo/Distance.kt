package com.example.sharedkernel.domain.geo

import com.example.sharedkernel.domain.base.ValueObject
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Distance(
        @Column(name = "distance_in_meters", nullable = false)
        var meters: Double
) : ValueObject
