package com.example.ridemanagement.port.rest

import com.example.ridemanagement.application.ConductorInfo
import com.example.ridemanagement.domain.model.Conductor
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"])
class ConductorController(
        private val conductorInfo: ConductorInfo
) {
    @GetMapping("/conductors")
    fun getConductors(): List<Conductor> {
        return conductorInfo.getAll()
    }
}
