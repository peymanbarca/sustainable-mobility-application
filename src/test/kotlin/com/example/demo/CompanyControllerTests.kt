package com.example.demo

import com.example.demo.dto.*
import com.example.demo.repository.EmployeeRepository
import com.example.demo.repository.UserRepository
import com.example.demo.service.CompanyService
import com.example.demo.service.EmployeeService
import com.example.demo.service.UploadService
import com.example.demo.service.VehicleService
import org.hibernate.internal.util.collections.CollectionHelper.listOf
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.mock.web.MockMultipartFile
import org.springframework.util.LinkedMultiValueMap

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class CompanyControllerTests
    (
    @Autowired private val restTemplate: TestRestTemplate,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val employeeRepository: EmployeeRepository,
    @Autowired private val companyService: CompanyService,
    @Autowired private val uploadService: UploadService,
    @Autowired private val employeeService: EmployeeService,
    @Autowired private val vehicleService: VehicleService,
    @Autowired private val authUtil: AuthUtil) {

    @Test
    @Order(1)
    fun testCreateCompany() {


        val user = userRepository.findByName(AuthUtil.testUserName)
        if (user != null) {
            // todo: delete previous records for the test user (such as its employees and company)
            userRepository.delete(user)
        }

        // register new test user
        val registerDto = RegisterDto(AuthUtil.testUserName, AuthUtil.testUserPassword)
        val responseEntity1: ResponseEntity<LoginResponseDto> = restTemplate.postForEntity(
            "/api/register",
            registerDto,
            LoginResponseDto::class.java
        )

        assertEquals(HttpStatus.OK, responseEntity1.statusCode)
        assertNotNull(responseEntity1.body)



        // login with created user, and create a new company
        val token = authUtil.login(AuthUtil.testUserName, AuthUtil.testUserPassword)
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
    @Order(2)
    fun testUploadCompanyFleetData() {

        // initialize all vehicle data in database
        vehicleService.initData()

        val fileContent = "employeeId,vehicleType,averageWeeklyMileage\nsdwx,BMW,100\ncwds,BENZ,150"
        val filePart = MockMultipartFile("file", "test.csv", "text/csv", fileContent.toByteArray())

        val expectedImportedEntries = listOf(
            FleetData(employeeId = "sdwx", vehicleType = "BMW", averageWeeklyMileage = 100.0),
            FleetData(employeeId = "cwds", vehicleType = "BENZ", averageWeeklyMileage = 150.0)
        )

        val user = userRepository.findByName(AuthUtil.testUserName)
        if (user?.company != null) {
            val fleetData = uploadService.uploadCsvFile(filePart)
            employeeService.createCompanyFleetData(fleetData =  fleetData, currentCompany = user.company!!)

            assertEquals(expectedImportedEntries, fleetData)
        }

    }

    @Test
    @Order(3)
    fun testRetrieveCompanyEmission() {

        val user = userRepository.findByName(AuthUtil.testUserName)
        if (user?.company != null) {

            val emissionData = companyService.retrieveCompanyEmission(user.company!!)

            val employees = employeeRepository.findAllByCompany(user.company!!)
            var averageWeeklyEmissionTotalCalculated = 0.0
            if (!employees.isEmpty()) {
                employees.forEach { e -> averageWeeklyEmissionTotalCalculated +=
                    e.averageWeeklyMileage * e.vehicle.emissionsPerMile
                }
            }

            assertEquals(averageWeeklyEmissionTotalCalculated, emissionData.totalAverageWeeklyEmission)
        }
    }

    @Test
    @Order(4)
    fun testRetrieveCompanyEmissionWithoutAuth() {

        val requestEntity = HttpEntity<Any>(HttpHeaders())
        val responseEntity: ResponseEntity<EmployeeEmissionResponseDto> =
            restTemplate.exchange("/api/company/emission",
                HttpMethod.GET,
                requestEntity,
                EmployeeEmissionResponseDto::class.java)


        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.statusCode)
    }

}