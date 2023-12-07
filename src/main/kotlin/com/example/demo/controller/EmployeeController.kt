package com.example.demo.controller

import com.example.demo.dto.ApiException
import com.example.demo.dto.EmployeeEmissionResponseDto
import com.example.demo.entity.User
import com.example.demo.service.EmployeeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/employee")
class EmployeeController(private val employeeService: EmployeeService) {


    @Operation(summary = "Retrieve emission data for an employee in current company",
        security = [SecurityRequirement(name = "bearerAuth")])
    @GetMapping("/emission/{employeeId}")
    fun retrieveEmployeeEmission(@PathVariable(name = "employeeId") employeeId: String,
                                 authentication: Authentication): EmployeeEmissionResponseDto {
        val currentUser = authentication.principal as User
        val currentCompany = currentUser.company ?:
            throw ApiException(HttpStatus.BAD_REQUEST.value(),
                "You don't have any company yet.")
        return employeeService.retrieveEmployeeEmission(employeeId, currentCompany)
    }
}
