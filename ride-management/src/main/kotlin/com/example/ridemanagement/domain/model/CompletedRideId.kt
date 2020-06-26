package com.example.ridemanagement.domain.model

import com.example.sharedkernel.domain.base.DomainObjectId
import javax.persistence.Embeddable

@Embeddable
class CompletedRideId(id: String) : DomainObjectId(id)
