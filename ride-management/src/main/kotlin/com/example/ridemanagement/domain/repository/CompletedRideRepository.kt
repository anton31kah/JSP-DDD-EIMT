package com.example.ridemanagement.domain.repository

import com.example.ridemanagement.domain.model.CompletedRide
import com.example.ridemanagement.domain.model.CompletedRideId
import org.springframework.data.jpa.repository.JpaRepository

interface CompletedRideRepository : JpaRepository<CompletedRide, CompletedRideId>

