package com.example.demo

import com.example.demo.dto.EmployeeEmissionResponseDto
import com.example.demo.repository.EmployeeRepository
import com.example.demo.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerTests(@Autowired private val userRepository: UserRepository,
            @Autowired private val authUtil: AuthUtil,
            @Autowired private val employeeRepository: EmployeeRepository,
            @Autowired private val restTemplate: TestRestTemplate) {

    @Test
    fun testEmployeeEmissionInsideACompany() {

        val user = userRepository.findByName(AuthUtil.testUserName)
        if (user?.company != null) {
            val token = authUtil.login(user.name, AuthUtil.testUserPassword)
            val requestHeaders = HttpHeaders()
            requestHeaders["Authorization"] = "Bearer $token"


            val employees = employeeRepository.findAllByCompany(user.company!!)
            if (!employees.isEmpty()) {

                val targetEmployee = employees[0]
                val requestEntity = HttpEntity<Any>(requestHeaders)
                val responseEntity: ResponseEntity<EmployeeEmissionResponseDto> =
                restTemplate.exchange("/api/employee/emission/${targetEmployee.employeeId}",
                    HttpMethod.GET,
                    requestEntity,
                    EmployeeEmissionResponseDto::class.java)


                Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode)
                Assertions.assertEquals(responseEntity.body?.averageWeeklyEmission,
                        targetEmployee.averageWeeklyMileage * targetEmployee.vehicle.emissionsPerMile)
            }


        }



    }
}