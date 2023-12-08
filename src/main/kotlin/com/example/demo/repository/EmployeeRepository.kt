package com.example.demo.repository

import com.example.demo.entity.Company
import com.example.demo.entity.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface EmployeeRepository : JpaRepository<Employee, Long> {

    fun findAllByCompany(company: Company): List<Employee>
    fun findOneByEmployeeIdAndCompany(employeeId: String, company: Company): Employee?
    fun deleteAllByCompany(company: Company)
    fun countAllByCompany(company: Company): Int

    @Query("select SUM(e.averageWeeklyMileage), " +
            "SUM(e.averageWeeklyMileage * e.vehicle.emissionsPerMile) from Employee e " +
            "WHERE e.company = ?1")
    fun retrieveCompanyEmissionByAllEmployees(company: Company): List<List<Double>>

    @Query("select AVG(e.averageWeeklyMileage * e.vehicle.emissionsPerMile) from Employee e " +
            "WHERE e.company = ?1")
    fun calculateCompanyAvgEmission(company: Company): Double

    @Query("select e.employeeId from Employee e  " +
            "WHERE e.company = ?1 " +
            "AND e.averageWeeklyMileage * e.vehicle.emissionsPerMile > ?2 ")
    fun retrieveCompanyHighlyUsedEmployees(company: Company, avgEmission: Double): List<String>

}