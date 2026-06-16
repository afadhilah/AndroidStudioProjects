package com.aflabs.skoola.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aflabs.skoola.domain.model.CartItem
import com.aflabs.skoola.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    val cartItems: StateFlow<List<CartItem>> = cartRepository.getCartItems()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val subtotal: StateFlow<Long> = cartItems.map { list ->
        list.sumOf { it.product.price * it.quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)

    val shippingFee: StateFlow<Long> = cartItems.map { list ->
        if (list.isEmpty()) 0L else 15000L
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)

    val grandTotal: StateFlow<Long> = combine(subtotal, shippingFee) { sub, ship ->
        sub + ship
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)

    fun updateQuantity(cartItemId: String, quantity: Int) {
        viewModelScope.launch {
            cartRepository.updateQuantity(cartItemId, quantity)
        }
    }

    fun removeFromCart(cartItemId: String) {
        viewModelScope.launch {
            cartRepository.removeFromCart(cartItemId)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart()
        }
    }
}
