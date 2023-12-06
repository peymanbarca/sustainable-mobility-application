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

data class CompanyProfileModel(
    val companyId: Long?,
    val companyName: String?,
    val userName: String
)
