package com.aflabs.skoola.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.aflabs.skoola.presentation.ui.cart.CartScreen
import com.aflabs.skoola.presentation.ui.home.HomeScreen
import com.aflabs.skoola.presentation.ui.home.WishlistScreen
import com.aflabs.skoola.presentation.ui.profile.ProfileScreen
import com.aflabs.skoola.presentation.viewmodel.AuthViewModel
import com.aflabs.skoola.presentation.viewmodel.CartViewModel
import com.aflabs.skoola.presentation.viewmodel.HomeViewModel

@Composable
fun MainContainerScreen(
    onProductClick: (productId: String) -> Unit,
    onSearchClick: () -> Unit,
    onCheckoutClick: () -> Unit,
    onSellerDashboardClick: () -> Unit,
    onOrdersClick: () -> Unit,
    onAddressesClick: () -> Unit,
    onPaymentsClick: () -> Unit,
    onLogoutSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) }

    val homeViewModel: HomeViewModel = hiltViewModel()
    val cartViewModel: CartViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()

    Scaffold(
        bottomBar = {
            Column {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                    thickness = 1.dp
                )
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 0.dp
                ) {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        icon = { Icon(Icons.Filled.Home, contentDescription = "Beranda") },
                        label = { Text("Beranda") }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favorit") },
                        label = { Text("Favorit") }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Keranjang") },
                        label = { Text("Keranjang") }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 3,
                        onClick = { selectedTab = 3 },
                        icon = { Icon(Icons.Filled.Person, contentDescription = "Profil") },
                        label = { Text("Profil") }
                    )
                }
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                0 -> HomeScreen(
                    viewModel = homeViewModel,
                    onProductClick = onProductClick,
                    onCartClick = { selectedTab = 2 },
                    onSearchClick = onSearchClick,
                    onNotificationsClick = {}
                )
                1 -> WishlistScreen(
                    viewModel = homeViewModel,
                    onProductClick = onProductClick
                )
                2 -> CartScreen(
                    viewModel = cartViewModel,
                    onCheckoutClick = onCheckoutClick
                )
                3 -> ProfileScreen(
                    authViewModel = authViewModel,
                    onOrdersClick = onOrdersClick,
                    onAddressesClick = onAddressesClick,
                    onPaymentsClick = onPaymentsClick,
                    onSellerDashboardClick = onSellerDashboardClick,
                    onLogoutClick = onLogoutSuccess
                )
            }
        }
    }
}
