package com.example.demo.service

import com.example.demo.entity.User
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jose.crypto.MACVerifier
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
@Slf4j
class TokenService(
    private val userService: UserService,
    @Value("\${application.security.secret_key}") private val secretKey: String,
    @Value("\${application.security.token_expiration_days}") private val expirationTimeDays: Int
) {


    fun createToken(user: User): String {
        val jwsHeader = JWSHeader(JWSAlgorithm.HS256)
        val claims = JWTClaimsSet.Builder()
            .issueTime(Date(System.currentTimeMillis()))
            .expirationTime(Date(System.currentTimeMillis() + expirationTimeDays * 24 * 60 * 60 * 1000))
            .subject(user.name)
            .claim("userId", user.id)
            .build()
        val jwsObject = SignedJWT(jwsHeader, claims)
        val signer = MACSigner(secretKey)

        jwsObject.sign(signer)

        return jwsObject.serialize()

    }

    fun parseToken(jwt: String): User? {
        return try {
            val parsedJwt = SignedJWT.parse(jwt)
            val verifier = MACVerifier(secretKey)
            if (!parsedJwt.verify(verifier)) {
                throw IllegalArgumentException("JWT signature verification failed")
            }

            val userId = parsedJwt.jwtClaimsSet.claims["userId"] as Long
            userService.findById(userId)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}