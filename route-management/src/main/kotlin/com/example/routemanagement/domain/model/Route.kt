package com.example.routemanagement.domain.model

import com.example.sharedkernel.domain.base.ValueObject
import com.example.sharedkernel.domain.geo.Distance
import javax.persistence.*

@Embeddable
data class Route(
        @Column(name = "route_name", nullable = false)
        var name: String,

        @Column(name = "route_number", nullable = false)
        var number: String,

        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "meters", column = Column(name = "route_length", nullable = false))
        )
        var length: Distance
) : ValueObject
