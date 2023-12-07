package com.example.demo.repository

import com.example.demo.dto.CompanyEmissionCalculation
import com.example.demo.entity.Company
import com.example.demo.entity.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface EmployeeRepository : JpaRepository<Employee, Long> {

    fun findOneByEmployeeIdAndCompany(employee: String, company: Company): Employee?
    fun deleteAllByCompany(company: Company)

    @Query("select SUM(e.averageWeeklyMileage), " +
            "SUM(e.averageWeeklyMileage * e.vehicle.emissionsPerMile) from Employee e " +
            "WHERE e.company = ?1")
    fun retrieveCompanyEmissionByAllEmployees(company: Company): List<List<Double>>
}