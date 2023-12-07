package com.example.demo

import com.example.demo.dto.ApiException
import com.example.demo.service.util.HashUtil
import com.example.demo.service.TokenService
import com.example.demo.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class AuthUtil(private val hashUtil: HashUtil,
               private val tokenService: TokenService,
               private val userService: UserService ) {


    companion object {
        const val testUserName: String = "testUser"
        const val testUserPassword: String = "testPassword"
    }


    fun login(name: String, password: String) :String {
        val user = userService.findByName(name) ?: throw ApiException(400, "Login failed")

        if (!hashUtil.checkBcrypt(password, user.password)) {
            throw ApiException(HttpStatus.BAD_REQUEST.value(), "Login failed")
        }

        return tokenService.createToken(user)

    }

}