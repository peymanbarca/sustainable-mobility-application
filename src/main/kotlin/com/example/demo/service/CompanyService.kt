package com.example.demo.service

import com.example.demo.dto.ApiException
import com.example.demo.dto.CompanyEmissionResponseDto
import com.example.demo.dto.CompanyProfileResponseDto
import com.example.demo.dto.HighlyUsedEmployeeDto
import com.example.demo.entity.Company
import com.example.demo.entity.User
import com.example.demo.repository.CompanyRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class CompanyService(
    private val companyRepository: CompanyRepository,
    private val userService: UserService,
    private val employeeService: EmployeeService
    ) {


    fun myCompany(authentication: Authentication): CompanyProfileResponseDto {
        val currentUser = authentication.principal as User
        val currentCompany = currentUser.company

        return CompanyProfileResponseDto(companyId = currentCompany?.id,
                companyName = currentCompany?.name ,
            userName = currentUser.name)
    }

    fun createCompany(authentication: Authentication, name: String): CompanyProfileResponseDto {
        val currentUser = authentication.principal as User
        if (currentUser.company != null)
            throw ApiException(HttpStatus.BAD_REQUEST.value(),
                "You currently have a company, so can not create a new one.")
        val company = Company(name = name)
        companyRepository.save(company)

        currentUser.company = company
        userService.save(user = currentUser)

        return CompanyProfileResponseDto(companyId = company.id,
            companyName = company.name ,
            userName = currentUser.name)
    }

    fun retrieveCompanyEmission(currentCompany: Company): CompanyEmissionResponseDto {


        val values = employeeService.retrieveCompanyEmission(company = currentCompany)
        val companySize = employeeService.retrieveCompanySize(company = currentCompany)
        if (values.size == 1)
            return CompanyEmissionResponseDto(companyId=currentCompany.id!!, companyName = currentCompany.name,
                totalAverageWeeklyMileage = values[0][0], totalAverageWeeklyEmission = values[0][1],
                totalEmployees = companySize
            )
        throw ApiException(HttpStatus.BAD_REQUEST.value(),
            "There is not data for current company")
    }

    fun retrieveCompanyEmissionSuggestion(currentCompany: Company): List<HighlyUsedEmployeeDto> {

        return employeeService.retrieveCompanyHighlyUsedEmployees(company = currentCompany)

    }
}