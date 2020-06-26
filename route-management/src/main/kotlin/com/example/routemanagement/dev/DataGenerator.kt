package com.example.routemanagement.dev

import com.example.routemanagement.domain.model.Ride
import com.example.routemanagement.domain.model.Route
import com.example.routemanagement.domain.model.Stop
import com.example.routemanagement.domain.repository.RideRepository
import com.example.routemanagement.domain.repository.StopRepository
import com.example.sharedkernel.domain.geo.Distance
import com.example.sharedkernel.domain.geo.MapPoint
import com.example.sharedkernel.domain.time.Day
import com.example.sharedkernel.domain.time.DayOfWeek
import com.example.sharedkernel.domain.time.Time
import com.example.sharedkernel.domain.time.TimePoint
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@Component
class DataGenerator(
        val stopRepository: StopRepository,
        val rideRepository: RideRepository
) {
    companion object {
        const val rekordStopName = "rekord"
        const val soborenHramStopName = "soboren hram"
        const val gradezenStopName = "gradezen"
        const val simpoStopName = "simpo"
        const val hotelKarposStopName = "hotel karpos"
        const val cityMallStopName = "city mall"
    }

    @PostConstruct
    @Transactional
    fun generateData() {
        generateStops()
        generateRides()
    }

    private fun generateStops() {
        if (stopRepository.count() == 0L) {
            stopRepository.saveAll(listOf(
                    Stop(rekordStopName, "1", MapPoint(41.994595, 21.430801)),
                    Stop(soborenHramStopName, "2", MapPoint(41.998203, 21.425519)),
                    Stop(gradezenStopName, "3", MapPoint(41.999550, 21.418667)),
                    Stop(simpoStopName, "4", MapPoint(42.000917, 21.411302)),
                    Stop(hotelKarposStopName, "5", MapPoint(42.001842, 21.404661)),
                    Stop(cityMallStopName, "6", MapPoint(42.003646, 21.391445))
            ))
        }
    }

    private fun generateRides() {
        if (rideRepository.count() == 0L) {
            rideRepository.saveAll(listOf(
                    Ride(Route("rekord - gradezen", "1", Distance(1350.0)),
                            listOf(TimePoint(Time(10, 30), Day(DayOfWeek.Monday)),
                                    TimePoint(Time(10, 30), Day(DayOfWeek.Wednesday)),
                                    TimePoint(Time(10, 30), Day(DayOfWeek.Friday)),
                                    TimePoint(Time(15, 0), Day(DayOfWeek.Saturday)),
                                    TimePoint(Time(15, 0), Day(DayOfWeek.Sunday))),
                            listOf(stopRepository.findByName(rekordStopName),
                                    stopRepository.findByName(soborenHramStopName),
                                    stopRepository.findByName(gradezenStopName))),

                    Ride(Route("city mall - rekord", "2", Distance(3620.0)),
                            listOf(TimePoint(Time(9, 0), Day(DayOfWeek.Monday)),
                                    TimePoint(Time(21, 0), Day(DayOfWeek.Thursday))),
                            listOf(stopRepository.findByName(cityMallStopName),
                                    stopRepository.findByName(hotelKarposStopName),
                                    stopRepository.findByName(simpoStopName),
                                    stopRepository.findByName(gradezenStopName),
                                    stopRepository.findByName(soborenHramStopName),
                                    stopRepository.findByName(rekordStopName))),

                    Ride(Route("finki - city mall", "3", Distance(1400.0)),
                            listOf(TimePoint(Time(14, 15), Day(DayOfWeek.Monday)),
                                    TimePoint(Time(15, 15), Day(DayOfWeek.Tuesday)),
                                    TimePoint(Time(16, 15), Day(DayOfWeek.Wednesday)),
                                    TimePoint(Time(17, 15), Day(DayOfWeek.Thursday)),
                                    TimePoint(Time(18, 15), Day(DayOfWeek.Friday))),
                            listOf(stopRepository.findByName(hotelKarposStopName),
                                    stopRepository.findByName(cityMallStopName)))
            ))
        }
    }
}
