package com.example.routemanagement.domain.model

import com.example.sharedkernel.domain.base.DomainObjectId
import javax.persistence.Embeddable

@Embeddable
class StopId(id: String) : DomainObjectId(id)
