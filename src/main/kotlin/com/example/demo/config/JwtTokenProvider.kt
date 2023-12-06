package com.example.demo.config

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider {

    private val secret = "my-secret-key"
    private val tokenExpirationTime = 3600000L // 1 hour

    fun generateToken(user: UserDetails): String {
        val claims = Jwts.claims()
            .setSubject(user.username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + tokenExpirationTime))
        return Jwts.builder()
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun extractUserIdFromToken(token: String): Long {
        val claims = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .body
        return claims["userId"] as Long
    }
}
