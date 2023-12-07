package com.example.demo.controller


import com.example.demo.dto.ApiException
import com.example.demo.dto.LoginDto
import com.example.demo.dto.LoginResponseDto
import com.example.demo.dto.RegisterDto
import com.example.demo.entity.User
import com.example.demo.service.HashUtil
import com.example.demo.service.TokenService
import com.example.demo.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * This controller handles login and register requests.
 * Both routes are public as specified in SecurityConfig.
 */
@RestController
@RequestMapping("/api")
class AuthController(
    private val hashUtil: HashUtil,
    private val tokenService: TokenService,
    private val userService: UserService,
) {

    @Operation(summary = "Login for registered user")
    @PostMapping("/login")
    fun login(@RequestBody payload: LoginDto): LoginResponseDto {
        val user = userService.findByName(payload.name) ?: throw ApiException(400, "Login failed")

        if (!hashUtil.checkBcrypt(payload.password, user.password)) {
            throw ApiException(HttpStatus.BAD_REQUEST.value(), "Login failed")
        }

        return LoginResponseDto(
            token = tokenService.createToken(user),
        )
    }

    @Operation(summary = "Register new user")
    @PostMapping("/register")
    fun register(@RequestBody payload: RegisterDto): LoginResponseDto {
        if (userService.existsByName(payload.name)) {
            throw ApiException(HttpStatus.BAD_REQUEST.value(), "Name already exists")
        }

        val user = User (
            name = payload.name,
            password = hashUtil.hashBcrypt(payload.password),
            company = null
        )

        val savedUser = userService.save(user)

        return LoginResponseDto(
            token = tokenService.createToken(savedUser),
        )
    }
}