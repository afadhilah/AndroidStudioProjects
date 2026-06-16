package com.aflabs.skoola.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val id: String,
    val product: Product,
    val quantity: Int
)
