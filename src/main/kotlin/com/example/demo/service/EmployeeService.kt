package com.example.demo.service

import com.example.demo.dto.ApiException
import com.example.demo.dto.EmployeeEmissionResponseDto
import com.example.demo.dto.FleetData
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
                "The vehicle with type $vehicleType doesn't exist in our system.")

        val employee = Employee(employeeId = employeeId, averageWeeklyMileage = averageWeeklyMileage,
            company = currentCompany, vehicle = vehicle)
        return employeeRepository.save(employee)
    }

    fun createCompanyFleetData(fleetData: List<FleetData>, currentCompany: Company) {



        // delete previous fleet data of company
        deletePreviousFleeDataForCompany(currentCompany)
        employeeRepository.flush()

        validateFleetData(fleetData)
        fleetData.forEach{ entry ->
            createEmployee(entry.employeeId!!, entry.vehicleType!!, entry.averageWeeklyMileage!!, currentCompany)
        }

    }

    private fun validateFleetData(fleetData: List<FleetData>) {
        var num = 0
        fleetData.forEach{ entry ->
            num ++
            entry.employeeId ?: throw ApiException(400,"Error in employeeId in line $num")
            entry.vehicleType ?: throw ApiException(400,"Error in vehicleType in line $num")
            entry.averageWeeklyMileage ?:
                throw ApiException(400,"Error in averageWeeklyMileage in line $num")
        }

    }

    // todo: calculate total emission data based on a date range (by dividing the date range to weeks)
    fun retrieveEmployeeEmission(employeeId: String, currentCompany: Company): EmployeeEmissionResponseDto {
        val employee = employeeRepository.findOneByEmployeeIdAndCompany(employeeId, currentCompany) ?:
            throw ApiException(HttpStatus.BAD_REQUEST.value(),
                "The employee with Id $employeeId doesn't exist in your company.")

        return EmployeeEmissionResponseDto(companyId = currentCompany.id!!, companyName = currentCompany.name,
            averageWeeklyMileage = employee.averageWeeklyMileage, vehicleType = employee.vehicle.vehicleType,
            averageWeeklyEmission = employee.averageWeeklyMileage * employee.vehicle.emissionsPerMile)

    }

    fun retrieveCompanyEmission(company: Company): List<List<Double>> {
        return employeeRepository.retrieveCompanyEmissionByAllEmployees(company)
    }

}