package com.example.sharedkernel.domain.id

import com.example.sharedkernel.domain.base.DomainObjectId
import javax.persistence.Embeddable

@Embeddable
class RideId(id: String) : DomainObjectId(id)
