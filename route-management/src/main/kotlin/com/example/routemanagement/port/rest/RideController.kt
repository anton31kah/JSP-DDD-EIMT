package com.example.routemanagement.port.rest

import com.example.routemanagement.application.RidesSchedule
import com.example.routemanagement.application.dto.RideDto
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"])
class RideController (
        val ridesSchedule: RidesSchedule
) {
    @GetMapping
    fun getNexWeekRides(): List<RideDto> {
        return ridesSchedule.nextWeekRides()
    }
}
