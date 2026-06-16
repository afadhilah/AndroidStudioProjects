package com.aflabs.skoola.domain.repository

import com.aflabs.skoola.domain.model.Banner
import com.aflabs.skoola.domain.model.Category
import com.aflabs.skoola.domain.model.Product
import com.aflabs.skoola.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<List<Product>>
    fun getProductById(productId: String): Flow<Product?>
    suspend fun getCategories(): List<Category>
    suspend fun getBanners(): List<Banner>
    suspend fun getProductReviews(productId: String): List<Review>
    suspend fun addReview(productId: String, rating: Float, comment: String): Result<Review>
    suspend fun refreshProducts(): Result<Unit>
}
