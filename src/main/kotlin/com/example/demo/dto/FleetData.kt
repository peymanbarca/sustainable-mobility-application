package com.example.demo.dto

import com.opencsv.bean.CsvBindByName
import com.opencsv.bean.CsvBindByPosition
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor


data class FleetData (
    @CsvBindByPosition(position = 0) var employeeId: String? = null,
    @CsvBindByPosition(position = 1) var vehicleType: String? = null,
    @CsvBindByPosition(position = 2) var averageWeeklyMileage: Double? = null,
)

