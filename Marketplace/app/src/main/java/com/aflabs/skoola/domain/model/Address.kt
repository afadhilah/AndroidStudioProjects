package com.aflabs.skoola.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val id: String,
    val name: String,
    val recipientName: String,
    val phone: String,
    val detailAddress: String,
    val school: String = "",
    val isPrimary: Boolean = false
)
