package com.example.demo.service

import com.example.demo.entity.Employee
import com.example.demo.repository.EmployeeRepository
import org.springframework.stereotype.Service

@Service
class EmployeeService(
    private val employeeRepository: EmployeeRepository,
    private val companyService: CompanyService
) {

    fun createEmployee(employeeId: String, averageWeeklyMileage: Double, companyId: Long): Employee {
        val company = companyService.getCompanyById(companyId)
        val employee = Employee(employeeId = employeeId, averageWeeklyMileage = averageWeeklyMileage, company = company)
        return employeeRepository.save(employee)
    }

    // Other employee-related methods...
}