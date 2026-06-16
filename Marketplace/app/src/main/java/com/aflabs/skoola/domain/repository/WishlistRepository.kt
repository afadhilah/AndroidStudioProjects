package com.aflabs.skoola.domain.repository

import com.aflabs.skoola.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface WishlistRepository {
    fun getWishlistItems(): Flow<List<Product>>
    fun isWishlisted(productId: String): Flow<Boolean>
    suspend fun toggleWishlist(product: Product): Result<Boolean>
}
