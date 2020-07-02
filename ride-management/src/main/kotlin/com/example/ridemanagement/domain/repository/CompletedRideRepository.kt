package com.example.ridemanagement.domain.repository

import com.example.ridemanagement.domain.model.CompletedRide
import com.example.ridemanagement.domain.model.CompletedRideId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CompletedRideRepository : JpaRepository<CompletedRide, CompletedRideId> {
    @Query("select count(cr) > 0 from completed_rides cr " +
            "where cr.ride_id = :rideId " +
            "and (extract(epoch from (:devNow\\:\\:timestamp - cr.completed_on)) / 60) < 60",
            nativeQuery = true)
    fun sameRidesToday(@Param("rideId") rideId: String, @Param("devNow") now: String): Boolean
}

