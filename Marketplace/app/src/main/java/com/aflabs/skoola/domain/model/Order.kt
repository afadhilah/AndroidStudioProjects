package com.aflabs.skoola.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class OrderStatus {
    PENDING,
    PROCESSING,
    SHIPPED,
    COMPLETED,
    CANCELLED
}

@Serializable
data class Order(
    val id: String,
    val buyerId: String,
    val buyerName: String,
    val sellerId: String,
    val items: List<CartItem>,
    val totalPrice: Long,
    val shippingFee: Long,
    val shippingAddress: String,
    val paymentMethod: String,
    val status: OrderStatus = OrderStatus.PENDING,
    val createdAt: Long = System.currentTimeMillis()
)
