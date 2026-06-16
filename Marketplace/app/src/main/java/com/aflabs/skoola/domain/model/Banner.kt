package com.aflabs.skoola.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Banner(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageUrl: String,
    val backgroundColorHex: String
)
