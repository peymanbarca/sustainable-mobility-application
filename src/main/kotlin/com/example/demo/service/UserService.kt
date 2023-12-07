package com.example.demo.service

import com.example.demo.entity.User
import com.example.demo.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun findById(id: Long): User? {
        return userRepository.findByIdOrNull(id)
    }

    fun findByName(name: String): User? {
        return userRepository.findByName(name)
    }

    fun existsByName(name: String): Boolean {
        return userRepository.existsByName(name)
    }

    fun save(user: User): User {
        return userRepository.save(user)
    }
}