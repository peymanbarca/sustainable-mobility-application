package com.example.demo

import com.example.demo.dto.CompanyProfileResponseDto
import com.example.demo.dto.LoginResponseDto
import com.example.demo.dto.RegisterDto
import com.example.demo.repository.EmployeeRepository
import com.example.demo.repository.UserRepository
import com.example.demo.service.CompanyService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CompanyControllerTests
    (
    @Autowired private val restTemplate: TestRestTemplate,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val employeeRepository: EmployeeRepository,
    @Autowired private val companyService: CompanyService,
    @Autowired private val authUtil: AuthUtil) {

    @Test
    fun testCreateCompany() {

        // register new user
        val randomString = generateRandomString(10)
        val registerDto = RegisterDto("testUser_$randomString", "testPassword")
        val responseEntity1: ResponseEntity<LoginResponseDto> = restTemplate.postForEntity(
            "/api/register",
            registerDto,
            LoginResponseDto::class.java
        )

        assertEquals(HttpStatus.OK, responseEntity1.statusCode)
        assertNotNull(responseEntity1.body)



        // login with created user, and create a new company
        val token = authUtil.login("testUser_$randomString", "testPassword")
        val requestHeaders = HttpHeaders()
        requestHeaders["Authorization"] = "Bearer $token"

        val requestBody = LinkedMultiValueMap<String, String>()
        requestBody.add("name", "companyTestName")

        val requestEntity = HttpEntity(requestBody, requestHeaders)
        val responseEntity2: ResponseEntity<CompanyProfileResponseDto> =
            restTemplate.exchange("/api/company/create",
                HttpMethod.POST,
                requestEntity,
                CompanyProfileResponseDto::class.java)

        assertEquals(HttpStatus.OK, responseEntity2.statusCode)
        assertNotNull(responseEntity2.body)

    }


    @Test
    fun testRetrieveCompanyEmission() {

        val user = userRepository.findByName("string")
        if (user?.company != null) {

            val emissionData = companyService.retrieveCompanyEmission(user.company!!)

            val employees = employeeRepository.findAllByCompany(user.company!!)
            var averageWeeklyEmissionTotalCalculated: Double = 0.0
            if (!employees.isEmpty()) {
                employees.forEach { e -> averageWeeklyEmissionTotalCalculated +=
                    e.averageWeeklyMileage * e.vehicle.emissionsPerMile
                }
            }

            assertEquals(averageWeeklyEmissionTotalCalculated, emissionData.averageWeeklyEmission)
        }
    }


    private fun generateRandomString(length: Int): String {
        val allowedChars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length).map { allowedChars.random() }
            .joinToString("")
    }
}