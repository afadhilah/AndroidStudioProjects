package com.aflabs.skoola.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SalesStats(
    val totalRevenue: Long,
    val ordersCount: Int,
    val productsSold: Int,
    val productViews: Int
)
