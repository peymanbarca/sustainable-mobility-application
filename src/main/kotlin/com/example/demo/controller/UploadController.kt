package com.example.demo.controller

import com.example.demo.service.EmployeeService
import com.opencsv.CSVReader
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.InputStreamReader

@RestController
@RequestMapping("/api/upload-csv")
class UploadController(private val employeeService: EmployeeService) {

    @PostMapping("/upload")
    fun uploadCSV(@RequestBody file: MultipartFile, @RequestParam companyId: Long) {
        // Use a CSV parsing library to process the file and call the employeeService to save the data
        // Example using OpenCSV:
        val reader = CSVReader(InputStreamReader(file.inputStream))
        val lines = reader.readAll()
        lines.forEach { line ->
            val employeeId = line[0]
            val averageWeeklyMileage = line[1].toDouble()
            employeeService.createEmployee(employeeId, averageWeeklyMileage, companyId)
        }
    }
}