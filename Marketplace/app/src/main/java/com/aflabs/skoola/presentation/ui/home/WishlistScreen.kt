package com.aflabs.skoola.presentation.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aflabs.skoola.presentation.components.EmptyState
import com.aflabs.skoola.presentation.components.ProductCard
import com.aflabs.skoola.presentation.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistScreen(
    viewModel: HomeViewModel,
    onProductClick: (productId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val wishlistItems by viewModel.wishlistItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorit Saya") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (wishlistItems.isEmpty()) {
                EmptyState(
                    icon = Icons.Filled.Favorite,
                    title = "Belum Ada Favorit",
                    subtitle = "Sukai buku, alat tulis, seragam, les, dll. untuk menyimpannya di sini."
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(wishlistItems) { prod ->
                        ProductCard(
                            product = prod,
                            isWishlisted = true,
                            onProductClick = { onProductClick(prod.id) },
                            onWishlistClick = { viewModel.toggleWishlist(prod) },
                            onAddToCartClick = { viewModel.addToCart(prod) }
                        )
                    }
                }
            }
        }
    }
}
