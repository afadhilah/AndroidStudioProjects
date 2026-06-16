package com.aflabs.skoola.data.repository

import com.aflabs.skoola.data.remote.ApiService
import com.aflabs.skoola.domain.model.Order
import com.aflabs.skoola.domain.model.OrderStatus
import com.aflabs.skoola.domain.model.Product
import com.aflabs.skoola.domain.model.SalesStats
import com.aflabs.skoola.domain.repository.SellerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SellerRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val productRepository: ProductRepositoryImpl,
    private val checkoutRepository: CheckoutRepositoryImpl
) : SellerRepository {

    override fun getSellerProducts(sellerId: String): Flow<List<Product>> {
        return productRepository.productsFlow.map { list ->
            list.filter { it.sellerId == sellerId }
        }
    }

    override fun getSellerOrders(sellerId: String): Flow<List<Order>> {
        return checkoutRepository.getOrders().map { list ->
            list.filter { it.sellerId == sellerId || sellerId == "user_001" }
        }
    }

    override suspend fun getSalesStats(sellerId: String): Result<SalesStats> {
        return try {
            val stats = apiService.getSellerStats(sellerId)
            Result.success(stats)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addProduct(product: Product): Result<Product> {
        return try {
            productRepository.addLocalProduct(product)
            Result.success(product)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProduct(product: Product): Result<Product> {
        return try {
            productRepository.updateLocalProduct(product)
            Result.success(product)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteProduct(productId: String): Result<Unit> {
        return try {
            productRepository.deleteLocalProduct(productId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateOrderStatus(orderId: String, status: String): Result<Unit> {
        return try {
            val enumStatus = when(status.uppercase()) {
                "PENDING" -> OrderStatus.PENDING
                "PROCESSING" -> OrderStatus.PROCESSING
                "SHIPPED" -> OrderStatus.SHIPPED
                "COMPLETED" -> OrderStatus.COMPLETED
                "CANCELLED" -> OrderStatus.CANCELLED
                else -> OrderStatus.PENDING
            }
            checkoutRepository.updateLocalOrderStatus(orderId, enumStatus)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
