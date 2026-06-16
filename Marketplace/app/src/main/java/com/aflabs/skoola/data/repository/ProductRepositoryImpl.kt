package com.aflabs.skoola.data.repository

import com.aflabs.skoola.data.remote.ApiService
import com.aflabs.skoola.data.remote.ReviewRequest
import com.aflabs.skoola.domain.model.Banner
import com.aflabs.skoola.domain.model.Category
import com.aflabs.skoola.domain.model.Product
import com.aflabs.skoola.domain.model.Review
import com.aflabs.skoola.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val productsFlow = _products.asStateFlow()

    override fun getProducts(): Flow<List<Product>> = productsFlow

    override fun getProductById(productId: String): Flow<Product?> {
        return productsFlow.map { list -> list.find { it.id == productId } }
    }

    override suspend fun getCategories(): List<Category> {
        return try {
            apiService.getCategories()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getBanners(): List<Banner> {
        return try {
            apiService.getBanners()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getProductReviews(productId: String): List<Review> {
        return try {
            apiService.getProductReviews(productId)
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun addReview(productId: String, rating: Float, comment: String): Result<Review> {
        return try {
            val review = apiService.addReview(productId, ReviewRequest(rating, comment))
            Result.success(review)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun refreshProducts(): Result<Unit> {
        return try {
            val list = apiService.getProducts()
            _products.value = list
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun addLocalProduct(product: Product) {
        _products.value = listOf(product) + _products.value
    }

    fun updateLocalProduct(product: Product) {
        _products.value = _products.value.map { if (it.id == product.id) product else it }
    }

    fun deleteLocalProduct(productId: String) {
        _products.value = _products.value.filter { it.id != productId }
    }
}
