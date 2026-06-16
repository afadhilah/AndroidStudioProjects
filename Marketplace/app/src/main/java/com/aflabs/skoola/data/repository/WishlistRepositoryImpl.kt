package com.aflabs.skoola.data.repository

import com.aflabs.skoola.data.local.dao.WishlistDao
import com.aflabs.skoola.data.local.entity.WishlistEntity
import com.aflabs.skoola.domain.model.Product
import com.aflabs.skoola.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WishlistRepositoryImpl @Inject constructor(
    private val wishlistDao: WishlistDao
) : WishlistRepository {

    private val json = Json { ignoreUnknownKeys = true }

    override fun getWishlistItems(): Flow<List<Product>> {
        return wishlistDao.getWishlistItems().map { entities ->
            entities.map { entity ->
                json.decodeFromString<Product>(entity.productJson)
            }
        }
    }

    override fun isWishlisted(productId: String): Flow<Boolean> {
        return wishlistDao.isWishlisted(productId).map { count -> count > 0 }
    }

    override suspend fun toggleWishlist(product: Product): Result<Boolean> {
        return try {
            val alreadyWishlisted = isWishlisted(product.id).first()
            if (alreadyWishlisted) {
                wishlistDao.deleteById(product.id)
                Result.success(false)
            } else {
                val productJson = json.encodeToString(product)
                wishlistDao.insert(WishlistEntity(product.id, productJson))
                Result.success(true)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
