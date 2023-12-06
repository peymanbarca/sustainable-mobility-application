package com.example.demo.controller

import com.example.demo.dto.CompanyProfileModel
import com.example.demo.service.CompanyService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/company")
class CompanyController(private val companyService: CompanyService) {

    @Operation(summary = "retrieve company profile of logged-in user", security = [SecurityRequirement(name = "bearerAuth")])
    @GetMapping("/my-company")
    fun someRequest(authentication: Authentication): CompanyProfileModel {
        return companyService.myCompany(authentication)
    }

    @Operation(summary = "create new company by logged-in user", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/create")
    fun createCompany(@RequestParam name: String, authentication: Authentication): CompanyProfileModel {
        return companyService.createCompany(authentication, name)
    }
}