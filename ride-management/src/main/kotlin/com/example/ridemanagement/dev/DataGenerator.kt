package com.example.ridemanagement.dev

import com.example.ridemanagement.domain.model.Conductor
import com.example.ridemanagement.domain.repository.ConductorRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.Month
import javax.annotation.PostConstruct

@Component
class DataGenerator(
        val conductorRepository: ConductorRepository
) {
    @PostConstruct
    @Transactional
    fun generateData() {
        generateConductors()
    }

    private fun generateConductors() {
        if (conductorRepository.count() == 0L) {
            conductorRepository.saveAll(listOf(
                    Conductor("Benjamin", LocalDate.of(2015, Month.APRIL, 1)),
                    Conductor("Franklin", LocalDate.of(2019, Month.DECEMBER, 12)),
                    Conductor("Louis", LocalDate.of(2006, Month.AUGUST, 30))
            ))
        }
    }
}
