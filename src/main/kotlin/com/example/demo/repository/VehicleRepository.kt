package com.example.demo.repository

import com.example.demo.entity.Vehicle
import org.springframework.data.jpa.repository.JpaRepository

interface VehicleRepository : JpaRepository<Vehicle, Long> {
    fun findByVehicleType(type: String): Vehicle?
    fun findFirstByOrderByEmissionsPerMile(): Vehicle
}