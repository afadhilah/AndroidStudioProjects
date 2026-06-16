package com.aflabs.skoola.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aflabs.skoola.domain.model.Banner
import com.aflabs.skoola.domain.model.Category
import com.aflabs.skoola.domain.model.Product
import com.aflabs.skoola.domain.repository.CartRepository
import com.aflabs.skoola.domain.repository.ProductRepository
import com.aflabs.skoola.domain.repository.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val wishlistRepository: WishlistRepository
) : ViewModel() {

    private val _banners = MutableStateFlow<List<Banner>>(emptyList())
    val banners = _banners.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    val wishlistItems = wishlistRepository.getWishlistItems()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val products: StateFlow<List<Product>> = combine(
        productRepository.getProducts(),
        _selectedCategory,
        _searchQuery
    ) { list, category, query ->
        var result = list
        if (!category.isNullOrEmpty()) {
            result = result.filter { it.category == category }
        }
        if (query.isNotEmpty()) {
            result = result.filter {
                it.title.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true)
            }
        }
        result
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            _isLoading.value = true
            productRepository.refreshProducts()
            _banners.value = productRepository.getBanners()
            _categories.value = productRepository.getCategories()
            _isLoading.value = false
        }
    }

    fun selectCategory(categoryId: String?) {
        _selectedCategory.value = categoryId
    }

    fun searchProducts(query: String) {
        _searchQuery.value = query
    }

    fun toggleWishlist(product: Product) {
        viewModelScope.launch {
            wishlistRepository.toggleWishlist(product)
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            cartRepository.addToCart(product, 1)
        }
    }
}
