package com.example.demo.service

import com.example.demo.entity.Vehicle
import com.example.demo.repository.VehicleRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class VehicleService(private val vehicleRepository: VehicleRepository) {



    // todo: Retrieve init data for all type of supporting vehicles (for example from external sources or data services)
    fun initData() {
        if (vehicleRepository.count() == 0L) {
            vehicleRepository.save(Vehicle(vehicleType = "BMW", emissionsPerMile=3.5))
            vehicleRepository.save(Vehicle(vehicleType = "BENZ", emissionsPerMile=3.75))
            vehicleRepository.save(Vehicle(vehicleType = "AUDI", emissionsPerMile=3.1))
            vehicleRepository.save(Vehicle(vehicleType = "VW", emissionsPerMile=2.6))
            vehicleRepository.save(Vehicle(vehicleType = "SEAT", emissionsPerMile=2.9))
            vehicleRepository.save(Vehicle(vehicleType = "TESLA", emissionsPerMile=1.6))
            vehicleRepository.save(Vehicle(vehicleType = "ELECTRIC_BMW", emissionsPerMile=0.6))
            vehicleRepository.save(Vehicle(vehicleType = "ELECTRIC_TESLA", emissionsPerMile=0.2))
            vehicleRepository.save(Vehicle(vehicleType = "ELECTRIC_GM", emissionsPerMile=0.4))
        }

    }

    fun getVehicleByVehicleType(vehicleType: String): Vehicle? {
        return vehicleRepository.findByVehicleType(vehicleType).orElse(null)
    }

    fun getEmissionsByVehicleType(vehicleType: String, averageWeeklyMileage: Double): Double {
        val vehicle = vehicleRepository.findByVehicleType(vehicleType)
            .orElseThrow { NoSuchElementException("Vehicle type not found: $vehicleType") }

        return vehicle.emissionsPerMile * averageWeeklyMileage
    }

    // Other vehicle-related methods...
}