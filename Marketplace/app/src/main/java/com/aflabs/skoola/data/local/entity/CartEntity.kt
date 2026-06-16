package com.aflabs.skoola.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartEntity(
    @PrimaryKey
    val id: String,
    val productJson: String,
    val quantity: Int
)
