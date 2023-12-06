package com.example.demo.entity

import jakarta.persistence.*
import java.io.Serializable

@Entity
data class Vehicle(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val vehicleType: String,

    val emissionsPerMile: Double
) : Serializable