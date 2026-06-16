package com.aflabs.skoola.domain.repository

import com.aflabs.skoola.domain.model.Address
import com.aflabs.skoola.domain.model.CartItem
import com.aflabs.skoola.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface CheckoutRepository {
    fun getAddresses(): Flow<List<Address>>
    suspend fun saveAddress(address: Address): Result<Unit>
    suspend fun deleteAddress(addressId: String): Result<Unit>
    fun getOrders(): Flow<List<Order>>
    suspend fun placeOrder(
        items: List<CartItem>,
        address: Address,
        paymentMethod: String,
        shippingFee: Long,
        totalPrice: Long
    ): Result<Order>
}
