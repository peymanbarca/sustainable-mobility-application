package com.example.demo.controller

import com.example.demo.service.VehicleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/vehicle")
class VehicleController(private val vehicleService: VehicleService) {

    @GetMapping("/emissions")
    fun getEmissions(@RequestParam vehicleType: String, @RequestParam averageWeeklyMileage: Double): Double {
        return vehicleService.getEmissionsByVehicleType(vehicleType, averageWeeklyMileage)
    }

    // Other vehicle-related endpoints...
}