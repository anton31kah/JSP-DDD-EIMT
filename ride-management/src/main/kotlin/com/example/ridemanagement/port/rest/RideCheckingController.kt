package com.example.ridemanagement.port.rest

import com.example.ridemanagement.application.ConductorCheckingDevice
import com.example.ridemanagement.application.form.CheckRideForm
import com.example.ridemanagement.domain.model.CompletedRide
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"])
class RideCheckingController(
        private val conductorCheckingDevice: ConductorCheckingDevice
) {
    @PostMapping
    fun checkRide(@RequestBody checkRideForm: CheckRideForm): CompletedRide {
        return conductorCheckingDevice.checkRide(checkRideForm)
    }
}
