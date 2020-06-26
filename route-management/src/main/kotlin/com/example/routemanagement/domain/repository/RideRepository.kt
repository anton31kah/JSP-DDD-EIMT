package com.example.routemanagement.domain.repository

import com.example.routemanagement.domain.model.Ride
import com.example.sharedkernel.domain.id.RideId
import org.springframework.data.jpa.repository.JpaRepository

interface RideRepository : JpaRepository<Ride, RideId>

