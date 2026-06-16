package com.aflabs.skoola.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val title: String,
    val description: String,
    val price: Long,
    val imageUrl: String,
    val images: List<String> = emptyList(),
    val category: String,
    val sellerId: String,
    val sellerName: String = "",
    val sellerImage: String = "",
    val sellerSchool: String = "",
    val stock: Int,
    val rating: Float,
    val reviewCount: Int = 0,
    val condition: String = "Baru",
    val location: String = "Sekolah",
    val sold: Int = 0,
    val isFeatured: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
