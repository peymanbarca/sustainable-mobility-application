package com.example.demo.controller

import com.example.demo.entity.Employee
import com.example.demo.service.EmployeeService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/employee")
class EmployeeController(private val employeeService: EmployeeService) {

    @PostMapping("/create")
    fun createEmployee(
        @RequestParam employeeId: String,
        @RequestParam averageWeeklyMileage: Double,
        @RequestParam companyId: Long
    ): Employee {
        return employeeService.createEmployee(employeeId, averageWeeklyMileage, companyId)
    }

    // Other employee-related endpoints...
}
