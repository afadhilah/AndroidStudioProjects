package com.aflabs.skoola.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val uid: String,
    val name: String,
    val email: String,
    val profileImage: String = "",
    val phone: String = "",
    val school: String = "",
    val address: String = "",
    val isVerified: Boolean = false,
    val rating: Float = 0f,
    val totalSales: Int = 0,
    val studentId: String = ""
)
