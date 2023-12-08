package com.example.demo.service.util

import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class HashUtil {

    fun checkBcrypt(input: String, hash: String): Boolean {
        return BCrypt.checkpw(input, hash)
    }

    fun hashBcrypt(input: String): String {
        return BCrypt.hashpw(input, BCrypt.gensalt(10))
    }
}