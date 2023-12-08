package com.example.demo.dto

import com.opencsv.bean.CsvBindByPosition


data class FleetData (
    @CsvBindByPosition(position = 0) var employeeId: String? = null,
    @CsvBindByPosition(position = 1) var vehicleType: String? = null,
    @CsvBindByPosition(position = 2) var averageWeeklyMileage: Double? = null,
)

