package com.example.sharedkernel.domain.base

import javax.persistence.EmbeddedId
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractEntity<ID : DomainObjectId>(@EmbeddedId override val id: ID) : IdentifiableDomainObject<ID>
