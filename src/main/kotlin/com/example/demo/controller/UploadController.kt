package com.example.demo.controller

import com.example.demo.dto.ApiException
import com.example.demo.dto.FleetData
import com.example.demo.entity.User
import com.example.demo.service.EmployeeService
import com.example.demo.service.UploadService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
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
        description = "The format is 3 column: (Employee Id, Vehicle Type, Average Weekly Mileage), " +
                "which all columns should be filled",
        security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/fleet",consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadCompanyFleetData(@RequestPart("file") file: MultipartFile, authentication: Authentication)
            : ResponseEntity<List<FleetData>>
    {

        val currentUser = authentication.principal as User
        val currentCompany = currentUser.company ?: throw ApiException(
            HttpStatus.BAD_REQUEST.value(),
            "Currently you don't have a company in our system.")

        val importedEntries = uploadService.uploadCsvFile(file)

       employeeService.createCompanyFleetData(fleetData =  importedEntries, currentCompany = currentCompany)

        return ResponseEntity.ok(importedEntries)

    }
}