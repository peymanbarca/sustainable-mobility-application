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



    // Other employee-related endpoints...
}
