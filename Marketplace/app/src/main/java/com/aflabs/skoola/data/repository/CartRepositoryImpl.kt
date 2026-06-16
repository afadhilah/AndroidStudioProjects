package com.aflabs.skoola.data.repository

import com.aflabs.skoola.data.local.dao.CartDao
import com.aflabs.skoola.data.local.entity.CartEntity
import com.aflabs.skoola.domain.model.CartItem
import com.aflabs.skoola.domain.model.Product
import com.aflabs.skoola.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    private val json = Json { ignoreUnknownKeys = true }

    override fun getCartItems(): Flow<List<CartItem>> {
        return cartDao.getCartItems().map { entities ->
            entities.map { entity ->
                CartItem(
                    id = entity.id,
                    product = json.decodeFromString(entity.productJson),
                    quantity = entity.quantity
                )
            }
        }
    }

    override suspend fun addToCart(product: Product, quantity: Int): Result<Unit> {
        return try {
            val productJson = json.encodeToString(product)
            cartDao.insert(
                CartEntity(
                    id = product.id,
                    productJson = productJson,
                    quantity = quantity
                )
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeFromCart(cartItemId: String): Result<Unit> {
        return try {
            cartDao.deleteById(cartItemId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateQuantity(cartItemId: String, quantity: Int): Result<Unit> {
        return try {
            if (quantity <= 0) {
                cartDao.deleteById(cartItemId)
            } else {
                cartDao.updateQuantity(cartItemId, quantity)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }
}
