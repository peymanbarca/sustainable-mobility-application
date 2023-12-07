package com.example.demo.controller

import com.example.demo.dto.ApiException
import com.example.demo.dto.CompanyEmissionResponseDto
import com.example.demo.dto.CompanyProfileResponseDto
import com.example.demo.entity.User
import com.example.demo.service.CompanyService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/company")
class CompanyController(private val companyService: CompanyService) {

    @Operation(summary = "Retrieve company profile of logged-in user", security = [SecurityRequirement(name = "bearerAuth")])
    @GetMapping("/my-company")
    fun someRequest(authentication: Authentication): CompanyProfileResponseDto {
        return companyService.myCompany(authentication)
    }

    @Operation(summary = "Create new company by logged-in user", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/create")
    fun createCompany(@RequestParam name: String, authentication: Authentication): CompanyProfileResponseDto {
        return companyService.createCompany(authentication, name)
    }

    @Operation(summary = "Retrieve emission data for whole current company",
        security = [SecurityRequirement(name = "bearerAuth")])
    @GetMapping("/emission")
    fun retrieveCompanyEmission(authentication: Authentication): CompanyEmissionResponseDto {
        val currentUser = authentication.principal as User
        val currentCompany = currentUser.company ?: throw ApiException(
            HttpStatus.BAD_REQUEST.value(), "You don't have any company yet.")
        return companyService.retrieveCompanyEmission(currentCompany)
    }

    // todo: consider implementation of API for register new user for a company
    //  (to support multi-user possible for user to company link)

}