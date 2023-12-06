package com.example.demo.config

import com.example.demo.dto.ApiException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ApiException::class)
    fun handleCustomException(ex: ApiException): ApiException {
        return ex
    }
}
