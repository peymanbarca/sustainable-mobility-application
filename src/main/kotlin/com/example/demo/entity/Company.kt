package com.example.demo.entity
import jakarta.persistence.*
import java.io.Serializable

@Entity
data class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,
) : Serializable