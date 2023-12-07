package com.example.demo.service

import com.example.demo.dto.ApiException
import com.example.demo.entity.Company
import com.example.demo.entity.Employee
import com.example.demo.repository.EmployeeRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class EmployeeService(
    private val employeeRepository: EmployeeRepository,
    private val vehicleService: VehicleService) {


    fun deletePreviousFleeDataForCompany(currentCompany: Company) {
        employeeRepository.deleteAllByCompany(currentCompany)
    }

    fun createEmployee(employeeId: String, vehicleType: String, averageWeeklyMileage: Double
                , currentCompany: Company
    ): Employee {


        val vehicle = vehicleService.getVehicleByVehicleType(vehicleType) ?:
            throw ApiException(HttpStatus.BAD_REQUEST.value(),
                "The vehicle with type $vehicleType doesn't exist in our system.");

        val employee = Employee(employeeId = employeeId, averageWeeklyMileage = averageWeeklyMileage,
            company = currentCompany, vehicle = vehicle)
        return employeeRepository.save(employee)
    }

}