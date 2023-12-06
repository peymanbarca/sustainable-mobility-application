package com.example.demo.repository

import com.example.demo.entity.Vehicle
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface VehicleRepository : JpaRepository<Vehicle, Long> {
    fun findByVehicleType(type: String): Optional<Vehicle>
}