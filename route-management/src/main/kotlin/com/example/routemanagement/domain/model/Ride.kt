package com.example.routemanagement.domain.model

import com.example.sharedkernel.domain.base.AbstractEntity
import com.example.sharedkernel.domain.base.DomainObjectId
import com.example.sharedkernel.domain.id.RideId
import com.example.sharedkernel.domain.time.TimePoint
import javax.persistence.*

@Entity
@Table(name = "rides")
data class Ride(
        @Column(nullable = false)
        @Embedded
        val route: Route,

        @ElementCollection
        val occurrences: List<TimePoint>,

        @ManyToMany
        @OrderColumn(name = "stops_order")
        val stops: List<Stop>
) : AbstractEntity<RideId>(DomainObjectId.randomId(RideId::class.java))
