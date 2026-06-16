package com.aflabs.skoola.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aflabs.skoola.domain.model.Product
import com.aflabs.skoola.domain.model.Review
import com.aflabs.skoola.domain.repository.CartRepository
import com.aflabs.skoola.domain.repository.ProductRepository
import com.aflabs.skoola.domain.repository.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val wishlistRepository: WishlistRepository
) : ViewModel() {

    private val _productId = MutableStateFlow<String?>(null)

    val product: StateFlow<Product?> = _productId
        .filterNotNull()
        .flatMapLatest { id -> productRepository.getProductById(id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val isWishlisted: StateFlow<Boolean> = _productId
        .filterNotNull()
        .flatMapLatest { id -> wishlistRepository.isWishlisted(id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews = _reviews.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun loadProductDetail(productId: String) {
        _productId.value = productId
        viewModelScope.launch {
            _isLoading.value = true
            _reviews.value = productRepository.getProductReviews(productId)
            _isLoading.value = false
        }
    }

    fun toggleWishlist() {
        val prod = product.value ?: return
        viewModelScope.launch {
            wishlistRepository.toggleWishlist(prod)
        }
    }

    fun addToCart(quantity: Int = 1) {
        val prod = product.value ?: return
        viewModelScope.launch {
            cartRepository.addToCart(prod, quantity)
        }
    }

    fun addReview(rating: Float, comment: String) {
        val prodId = _productId.value ?: return
        viewModelScope.launch {
            productRepository.addReview(prodId, rating, comment)
                .onSuccess {
                    _reviews.value = productRepository.getProductReviews(prodId)
                }
        }
    }
}
