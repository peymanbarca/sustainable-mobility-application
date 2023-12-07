package com.example.demo.controller

import com.example.demo.service.VehicleService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/vehicle")
class VehicleController(private val vehicleService: VehicleService) {


    // todo: secure this API only for admin user
    @PostMapping("/init-data")
    fun getEmissions() {
        vehicleService.initData()
    }

}