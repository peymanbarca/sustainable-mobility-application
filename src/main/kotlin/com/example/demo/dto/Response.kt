package com.example.demo.dto

import org.springframework.web.server.ResponseStatusException

/**
 * This file contains all outgoing DTOs.
 * [ApiException] is used to easily throw exceptions.
 */

class ApiException(code: Int, message: String) : ResponseStatusException(code, message, null)

data class LoginResponseDto(
    val token: String,
)

data class CompanyProfileResponseDto(
    val companyId: Long?,
    val companyName: String?,
    val userName: String
)

data class EmployeeEmissionResponseDto(
    val companyId: Long,
    val companyName: String,
    val averageWeeklyMileage: Double,
    val vehicleType: String,
    val averageWeeklyEmission: Double
)

data class CompanyEmissionResponseDto(
    val companyId: Long,
    val companyName: String,
    val totalAverageWeeklyMileage: Double,
    val totalAverageWeeklyEmission: Double,
    val totalEmployees: Int
)


data class HighlyUsedEmployeeDto(
    val employeeId: String,
    val averageWeeklyMileage: Double,
    val vehicleType: String,
    val vehicleEmissionPerMile: Double,
    val averageWeeklyEmission: Double
)
