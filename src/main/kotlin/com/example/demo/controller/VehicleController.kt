package com.example.demo.controller

import com.example.demo.service.VehicleService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/vehicle")
class VehicleController(private val vehicleService: VehicleService) {


    // todo: secure this API only for admin user
    @Operation(summary = "Initialize all vehicles data in database",
        description = "Should be executed in start of application for the first time," +
                " while can be replaced by fetch this information from external API on demand.")
    @PostMapping("/init-data")
    fun getEmissions() {
        vehicleService.initData()
    }

}