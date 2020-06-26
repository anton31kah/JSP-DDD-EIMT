package com.example.sharedkernel.domain.base

import java.io.Serializable

interface IdentifiableDomainObject<ID : Serializable> : DomainObject {
    val id: ID?
}
