package com.example.ridemanagement.application

import com.example.ridemanagement.domain.model.Conductor
import com.example.ridemanagement.domain.repository.ConductorRepository
import org.springframework.stereotype.Service

@Service
class ConductorInfo(
        private val conductorRepository: ConductorRepository
) {
    fun getAll(): List<Conductor> = conductorRepository.findAll()
}
