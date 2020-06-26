package com.example.routemanagement.domain.repository

import com.example.routemanagement.domain.model.Stop
import com.example.routemanagement.domain.model.StopId
import org.springframework.data.jpa.repository.JpaRepository

interface StopRepository : JpaRepository<Stop, StopId> {
    fun findByName(name: String): Stop
}
