package com.example.ridemanagement.domain.model

import com.example.sharedkernel.domain.base.AbstractEntity
import com.example.sharedkernel.domain.base.DomainObjectId
import com.example.sharedkernel.domain.id.ConductorId
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "conductors")
data class Conductor(
        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        val employeeSince: LocalDate
) : AbstractEntity<ConductorId>(DomainObjectId.randomId(ConductorId::class.java))
