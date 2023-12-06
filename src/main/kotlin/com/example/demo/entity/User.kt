package com.example.demo.entity

import jakarta.persistence.*
import lombok.Builder
import lombok.Data
import java.io.Serializable

@Entity
@Table(name = "user_co")
@Builder
@Data
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column
    var name: String = "",

    @Column
    var password: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    var company: Company?
) : Serializable