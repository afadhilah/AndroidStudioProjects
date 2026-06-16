package com.aflabs.skoola.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addresses")
data class AddressEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val recipientName: String,
    val phone: String,
    val detailAddress: String,
    val school: String,
    val isPrimary: Boolean
)
