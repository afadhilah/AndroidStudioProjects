package com.aflabs.skoola.domain.repository

import com.aflabs.skoola.domain.model.Order
import com.aflabs.skoola.domain.model.Product
import com.aflabs.skoola.domain.model.SalesStats
import kotlinx.coroutines.flow.Flow

interface SellerRepository {
    fun getSellerProducts(sellerId: String): Flow<List<Product>>
    fun getSellerOrders(sellerId: String): Flow<List<Order>>
    suspend fun getSalesStats(sellerId: String): Result<SalesStats>
    suspend fun addProduct(product: Product): Result<Product>
    suspend fun updateProduct(product: Product): Result<Product>
    suspend fun deleteProduct(productId: String): Result<Unit>
    suspend fun updateOrderStatus(orderId: String, status: String): Result<Unit>
}
