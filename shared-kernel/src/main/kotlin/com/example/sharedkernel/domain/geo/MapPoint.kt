package com.example.sharedkernel.domain.geo

import com.example.sharedkernel.domain.base.ValueObject
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class MapPoint(
        @Column(nullable = false)
        var latitude: Double,

        @Column(nullable = false)
        var longitude: Double
) : ValueObject
