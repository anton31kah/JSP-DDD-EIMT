package com.example.routemanagement.domain.model

import com.example.sharedkernel.domain.base.AbstractEntity
import com.example.sharedkernel.domain.base.DomainObjectId
import com.example.sharedkernel.domain.geo.MapPoint
import javax.persistence.*

@Entity
@Table(name = "stops")
data class Stop(
        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        val number: String,

        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "latitude", column = Column(name = "latitude", nullable = false)),
                AttributeOverride(name = "longitude", column = Column(name = "longitude", nullable = false))
        )
        val location: MapPoint
) : AbstractEntity<StopId>(DomainObjectId.randomId(StopId::class.java))
