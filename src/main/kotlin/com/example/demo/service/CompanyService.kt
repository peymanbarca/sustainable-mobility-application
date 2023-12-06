package com.example.demo.service

import com.example.demo.dto.ApiException
import com.example.demo.dto.CompanyProfileModel
import com.example.demo.entity.Company
import com.example.demo.entity.User
import com.example.demo.repository.CompanyRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class CompanyService(
    private val companyRepository: CompanyRepository,
    private val userService: UserService
    ) {


    fun myCompany(authentication: Authentication): CompanyProfileModel {
        val currentUser = authentication.principal as User
        val currentCompany = currentUser.company

        return CompanyProfileModel(companyId = currentCompany?.id,
                companyName = currentCompany?.name ,
            userName = currentUser.name)
    }

    fun createCompany(authentication: Authentication, name: String): CompanyProfileModel {
        val currentUser = authentication.principal as User
        if (currentUser.company != null)
            throw ApiException(HttpStatus.BAD_REQUEST.value(),
                "You currently have a company, so can not create a new one.")
        val company = Company(name = name)
        companyRepository.save(company)

        currentUser.company = company
        userService.save(user = currentUser)

        return CompanyProfileModel(companyId = company.id,
            companyName = company.name ,
            userName = currentUser.name)
    }

    fun getCompanyById(companyId: Long): Company {
        return companyRepository.findById(companyId)
            .orElseThrow { NoSuchElementException("Company not found with id: $companyId") }
    }

    // Other company-related methods...
}