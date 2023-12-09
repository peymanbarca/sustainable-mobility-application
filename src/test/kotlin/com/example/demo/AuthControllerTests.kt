package com.example.demo

import com.example.demo.dto.LoginDto
import com.example.demo.dto.LoginResponseDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTests (
    @Autowired private val restTemplate: TestRestTemplate)
    {


    @Test
    fun testLoginFailure() {


        val requestBody = LoginDto(name = UUID.randomUUID().toString(), password = UUID.randomUUID().toString())


        val requestEntity = HttpEntity(requestBody, HttpHeaders())
        val responseEntity: ResponseEntity<Any> =
            restTemplate.exchange("/api/login",
                HttpMethod.POST,
                requestEntity,
                Any::class.java)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)

    }
}