package com.example.ridemanagement.domain.model

import com.example.sharedkernel.domain.base.AbstractEntity
import com.example.sharedkernel.domain.base.DomainObjectId
import com.example.sharedkernel.domain.id.RideId
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "completed_rides")
data class CompletedRide(
        @Embedded
        @AttributeOverrides(
                AttributeOverride(name = "id", column = Column(name = "ride_id", nullable = false))
        )
        val rideId: RideId,

        @Column(nullable = false)
        val completedOn: LocalDateTime,

        @ManyToOne
        val conductor: Conductor
) : AbstractEntity<CompletedRideId>(DomainObjectId.randomId(CompletedRideId::class.java))
