package com.example.demo.controller

import com.example.demo.dto.ApiException
import com.example.demo.dto.FleetData
import com.example.demo.entity.User
import com.example.demo.service.EmployeeService
import com.example.demo.service.UploadService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/upload")
class UploadController(private val employeeService: EmployeeService,
        private val uploadService: UploadService
    ) {

    @Operation(summary = "Upload company's fleet data (as CSV) by logged-in user",
        security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/fleet")
    fun uploadCompanyFleetData(@RequestPart("file") file: MultipartFile, authentication: Authentication)
            : ResponseEntity<List<FleetData>>
    {

        val currentUser = authentication.principal as User
        val currentCompany = currentUser.company ?: throw ApiException(
            HttpStatus.BAD_REQUEST.value(),
            "Currently you don't have a company in our system.")

        val importedEntries = uploadService.uploadCsvFile(file)

        // delete previous fleet data of company
        employeeService.deletePreviousFleeDataForCompany(currentCompany)

        var num = 0
        importedEntries.forEach{ entry ->
            num ++
            val employeeId = entry.employeeId ?: throw ApiException(400,"Error in employeeId in line $num")
            val vehicleType = entry.vehicleType ?: throw ApiException(400,"Error in vehicleType in line $num")
            val averageWeeklyMileage = entry.averageWeeklyMileage ?:
                throw ApiException(400,"Error in averageWeeklyMileage in line $num")
            employeeService.createEmployee(employeeId, vehicleType, averageWeeklyMileage, currentCompany)

        }


        return ResponseEntity.ok(importedEntries)

    }
}