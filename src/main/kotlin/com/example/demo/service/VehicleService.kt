package com.example.demo.service

import com.example.demo.repository.VehicleRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class VehicleService(private val vehicleRepository: VehicleRepository) {

    fun getEmissionsByVehicleType(vehicleType: String, averageWeeklyMileage: Double): Double {
        val vehicle = vehicleRepository.findByVehicleType(vehicleType)
            .orElseThrow { NoSuchElementException("Vehicle type not found: $vehicleType") }

        return vehicle.emissionsPerMile * averageWeeklyMileage
    }

    // Other vehicle-related methods...
}