package com.aflabs.skoola.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aflabs.skoola.domain.model.Order
import com.aflabs.skoola.domain.model.Product
import com.aflabs.skoola.domain.model.SalesStats
import com.aflabs.skoola.domain.repository.SellerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellerViewModel @Inject constructor(
    private val sellerRepository: SellerRepository
) : ViewModel() {

    private val _sellerId = MutableStateFlow("user_001")

    val products: StateFlow<List<Product>> = _sellerId
        .flatMapLatest { id -> sellerRepository.getSellerProducts(id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val orders: StateFlow<List<Order>> = _sellerId
        .flatMapLatest { id -> sellerRepository.getSellerOrders(id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _stats = MutableStateFlow<SalesStats?>(null)
    val stats = _stats.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _sellerActionSuccess = MutableSharedFlow<String>()
    val sellerActionSuccess = _sellerActionSuccess.asSharedFlow()

    init {
        loadSellerData()
    }

    fun loadSellerData() {
        viewModelScope.launch {
            _isLoading.value = true
            sellerRepository.getSalesStats(_sellerId.value)
                .onSuccess { salesStats ->
                    _stats.value = salesStats
                }
            _isLoading.value = false
        }
    }

    fun addOrUpdateProduct(
        id: String?,
        title: String,
        description: String,
        price: Long,
        imageUrl: String,
        category: String,
        stock: Int,
        condition: String,
        location: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            val product = Product(
                id = id ?: "p_${System.currentTimeMillis()}",
                title = title,
                description = description,
                price = price,
                imageUrl = imageUrl.ifEmpty { "https://picsum.photos/seed/${title.take(3)}/400/400" },
                images = listOf(imageUrl.ifEmpty { "https://picsum.photos/seed/${title.take(3)}/400/400" }),
                category = category,
                sellerId = _sellerId.value,
                sellerName = "Ahmad Fadhil",
                sellerImage = "https://picsum.photos/seed/user1/200/200",
                sellerSchool = "Universitas Indonesia",
                stock = stock,
                rating = 5.0f,
                reviewCount = 0,
                condition = condition,
                location = location
            )

            val result = if (id == null) {
                sellerRepository.addProduct(product)
            } else {
                sellerRepository.updateProduct(product)
            }

            result.onSuccess {
                _sellerActionSuccess.emit(if (id == null) "Produk berhasil ditambahkan!" else "Produk berhasil diperbarui!")
            }
            _isLoading.value = false
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            sellerRepository.deleteProduct(productId)
                .onSuccess {
                    _sellerActionSuccess.emit("Produk berhasil dihapus!")
                }
            _isLoading.value = false
        }
    }

    fun updateOrderStatus(orderId: String, status: String) {
        viewModelScope.launch {
            _isLoading.value = true
            sellerRepository.updateOrderStatus(orderId, status)
                .onSuccess {
                    _sellerActionSuccess.emit("Status pesanan diperbarui menjadi $status!")
                }
            _isLoading.value = false
        }
    }
}
