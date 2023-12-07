package com.example.demo.entity
import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["employeeId", "company_id"])])
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val employeeId: String,

    val averageWeeklyMileage: Double,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    val company: Company,

    @ManyToOne(fetch = FetchType.LAZY)
    val vehicle: Vehicle

) : Serializable