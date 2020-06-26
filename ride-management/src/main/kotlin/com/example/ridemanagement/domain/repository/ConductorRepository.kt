package com.example.ridemanagement.domain.repository

import com.example.ridemanagement.domain.model.Conductor
import com.example.sharedkernel.domain.id.ConductorId
import org.springframework.data.jpa.repository.JpaRepository

interface ConductorRepository : JpaRepository<Conductor, ConductorId>
