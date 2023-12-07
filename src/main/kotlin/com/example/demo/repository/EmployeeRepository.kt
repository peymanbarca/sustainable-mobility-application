package com.example.demo.repository

import com.example.demo.entity.Company
import com.example.demo.entity.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository : JpaRepository<Employee, Long> {

    fun deleteAllByCompany(company: Company)
}