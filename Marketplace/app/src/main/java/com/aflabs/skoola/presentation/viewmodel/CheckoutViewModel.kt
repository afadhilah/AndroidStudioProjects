package com.aflabs.skoola.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aflabs.skoola.domain.model.Address
import com.aflabs.skoola.domain.model.CartItem
import com.aflabs.skoola.domain.model.Order
import com.aflabs.skoola.domain.repository.CartRepository
import com.aflabs.skoola.domain.repository.CheckoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val checkoutRepository: CheckoutRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    val addresses: StateFlow<List<Address>> = checkoutRepository.getAddresses()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val selectedAddress: StateFlow<Address?> = addresses.map { list ->
        list.find { it.isPrimary } ?: list.firstOrNull()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val orders: StateFlow<List<Order>> = checkoutRepository.getOrders()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedPaymentMethod = MutableStateFlow("QRIS")
    val selectedPaymentMethod = _selectedPaymentMethod.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _checkoutSuccess = MutableSharedFlow<Order>()
    val checkoutSuccess = _checkoutSuccess.asSharedFlow()

    fun setPaymentMethod(method: String) {
        _selectedPaymentMethod.value = method
    }

    fun saveAddress(address: Address) {
        viewModelScope.launch {
            checkoutRepository.saveAddress(address)
        }
    }

    fun deleteAddress(addressId: String) {
        viewModelScope.launch {
            checkoutRepository.deleteAddress(addressId)
        }
    }

    fun placeOrder(items: List<CartItem>, shippingFee: Long, totalPrice: Long) {
        val address = selectedAddress.value ?: return
        viewModelScope.launch {
            _isLoading.value = true
            checkoutRepository.placeOrder(
                items = items,
                address = address,
                paymentMethod = _selectedPaymentMethod.value,
                shippingFee = shippingFee,
                totalPrice = totalPrice
            ).onSuccess { order ->
                cartRepository.clearCart()
                _checkoutSuccess.emit(order)
            }
            _isLoading.value = false
        }
    }
}
