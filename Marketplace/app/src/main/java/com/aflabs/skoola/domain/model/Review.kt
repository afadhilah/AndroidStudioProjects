package com.aflabs.skoola.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Review(
    val id: String,
    val productId: String,
    val userId: String,
    val userName: String,
    val userImage: String = "",
    val rating: Float,
    val comment: String,
    val createdAt: Long = System.currentTimeMillis()
)
