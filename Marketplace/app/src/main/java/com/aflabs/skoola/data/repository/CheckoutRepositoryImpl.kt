package com.aflabs.skoola.data.repository

import com.aflabs.skoola.data.local.dao.AddressDao
import com.aflabs.skoola.data.local.entity.AddressEntity
import com.aflabs.skoola.data.remote.ApiService
import com.aflabs.skoola.data.remote.OrderRequest
import com.aflabs.skoola.domain.model.Address
import com.aflabs.skoola.domain.model.CartItem
import com.aflabs.skoola.domain.model.Order
import com.aflabs.skoola.domain.model.OrderStatus
import com.aflabs.skoola.domain.repository.CheckoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckoutRepositoryImpl @Inject constructor(
    private val addressDao: AddressDao,
    private val apiService: ApiService
) : CheckoutRepository {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())

    init {
        _orders.value = listOf(
            Order(
                id = "order_101",
                buyerId = "user_001",
                buyerName = "Ahmad Fadhil",
                sellerId = "seller_002",
                items = emptyList(),
                totalPrice = 195000L,
                shippingFee = 15000L,
                shippingAddress = "Gang Kober No. 12, Margonda, Depok (Universitas Indonesia)",
                paymentMethod = "Transfer Bank BCA",
                status = OrderStatus.COMPLETED,
                createdAt = System.currentTimeMillis() - 86400000 * 2
            ),
            Order(
                id = "order_102",
                buyerId = "user_001",
                buyerName = "Ahmad Fadhil",
                sellerId = "seller_004",
                items = emptyList(),
                totalPrice = 85000L,
                shippingFee = 10000L,
                shippingAddress = "Gang Kober No. 12, Margonda, Depok (Universitas Indonesia)",
                paymentMethod = "QRIS",
                status = OrderStatus.SHIPPED,
                createdAt = System.currentTimeMillis() - 3600000 * 4
            )
        )
    }

    override fun getAddresses(): Flow<List<Address>> {
        return addressDao.getAddresses().map { entities ->
            if (entities.isEmpty()) {
                listOf(
                    Address("addr_1", "Kos Utama", "Ahmad Fadhil", "082145678901", "Gang Kober No. 12, Margonda, Depok", "Universitas Indonesia", true)
                )
            } else {
                entities.map { entity ->
                    Address(
                        id = entity.id,
                        name = entity.name,
                        recipientName = entity.recipientName,
                        phone = entity.phone,
                        detailAddress = entity.detailAddress,
                        school = entity.school,
                        isPrimary = entity.isPrimary
                    )
                }.sortedByDescending { it.isPrimary }
            }
        }
    }

    override suspend fun saveAddress(address: Address): Result<Unit> {
        return try {
            val entity = AddressEntity(
                id = address.id,
                name = address.name,
                recipientName = address.recipientName,
                phone = address.phone,
                detailAddress = address.detailAddress,
                school = address.school,
                isPrimary = address.isPrimary
            )
            addressDao.insertAndSetPrimary(entity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAddress(addressId: String): Result<Unit> {
        return try {
            addressDao.deleteById(addressId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getOrders(): Flow<List<Order>> = _orders.asStateFlow()

    override suspend fun placeOrder(
        items: List<CartItem>,
        address: Address,
        paymentMethod: String,
        shippingFee: Long,
        totalPrice: Long
    ): Result<Order> {
        return try {
            val sellerId = items.firstOrNull()?.product?.sellerId ?: "seller_002"
            val request = OrderRequest(
                buyerId = "user_001",
                buyerName = address.recipientName,
                sellerId = sellerId,
                items = items,
                totalPrice = totalPrice,
                shippingFee = shippingFee,
                shippingAddress = "${address.name}: ${address.detailAddress} (${address.school})",
                paymentMethod = paymentMethod
            )
            val apiOrder = apiService.placeOrder(request)
            val completedOrder = apiOrder.copy(
                items = items,
                totalPrice = totalPrice,
                shippingFee = shippingFee,
                shippingAddress = request.shippingAddress,
                paymentMethod = paymentMethod
            )
            _orders.value = listOf(completedOrder) + _orders.value
            Result.success(completedOrder)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun addLocalOrder(order: Order) {
        _orders.value = listOf(order) + _orders.value
    }

    fun updateLocalOrderStatus(orderId: String, status: OrderStatus) {
        _orders.value = _orders.value.map {
            if (it.id == orderId) it.copy(status = status) else it
        }
    }
}
