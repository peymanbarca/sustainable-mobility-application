package com.example.demo.entity
import jakarta.persistence.*
import java.io.Serializable

@Entity
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val employeeId: String,

    val averageWeeklyMileage: Double,

    @ManyToOne(fetch = FetchType.LAZY)
    val company: Company
) : Serializable