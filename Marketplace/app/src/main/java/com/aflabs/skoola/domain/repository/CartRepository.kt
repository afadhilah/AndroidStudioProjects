package com.aflabs.skoola.domain.repository

import com.aflabs.skoola.domain.model.CartItem
import com.aflabs.skoola.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartItem>>
    suspend fun addToCart(product: Product, quantity: Int = 1): Result<Unit>
    suspend fun removeFromCart(cartItemId: String): Result<Unit>
    suspend fun updateQuantity(cartItemId: String, quantity: Int): Result<Unit>
    suspend fun clearCart()
}
