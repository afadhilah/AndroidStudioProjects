package com.aflabs.skoola.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aflabs.skoola.presentation.ui.MainContainerScreen
import com.aflabs.skoola.presentation.ui.auth.*
import com.aflabs.skoola.presentation.ui.cart.CartScreen
import com.aflabs.skoola.presentation.ui.checkout.*
import com.aflabs.skoola.presentation.ui.home.*
import com.aflabs.skoola.presentation.ui.profile.ProfileScreen
import com.aflabs.skoola.presentation.ui.seller.SellerDashboardScreen
import com.aflabs.skoola.presentation.ui.seller.UploadProductScreen
import com.aflabs.skoola.presentation.viewmodel.*

@Composable
fun SkoolaNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Welcome.route,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onLoginClick = { navController.navigate(Screen.Login.route) },
                onRegisterClick = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Login.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            LoginScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onForgotPasswordClick = { navController.navigate(Screen.ForgotPassword.route) },
                onRegisterClick = { navController.navigate(Screen.Register.route) },
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Register.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            RegisterScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onLoginClick = { navController.navigate(Screen.Login.route) },
                onRegisterSuccess = { email ->
                    navController.navigate(Screen.Otp.createRoute(email))
                }
            )
        }

        composable(
            route = Screen.Otp.route,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val viewModel: AuthViewModel = hiltViewModel()
            OtpScreen(
                email = email,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onVerificationSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.ForgotPassword.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            ForgotPasswordScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Home.route) {
            MainContainerScreen(
                onProductClick = { productId -> navController.navigate(Screen.ProductDetail.createRoute(productId)) },
                onSearchClick = { navController.navigate(Screen.Search.route) },
                onCheckoutClick = { navController.navigate(Screen.Checkout.route) },
                onSellerDashboardClick = { navController.navigate(Screen.SellerDashboard.route) },
                onOrdersClick = { navController.navigate(Screen.MyOrders.route) },
                onAddressesClick = { navController.navigate(Screen.SavedAddress.route) },
                onPaymentsClick = { navController.navigate(Screen.PaymentMethods.route) },
                onLogoutSuccess = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Search.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            SearchScreen(
                viewModel = viewModel,
                onProductClick = { productId -> navController.navigate(Screen.ProductDetail.createRoute(productId)) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            val viewModel: ProductDetailViewModel = hiltViewModel()
            ProductDetailScreen(
                productId = productId,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onCartClick = { navController.navigate(Screen.Cart.route) }
            )
        }

        composable(Screen.Cart.route) {
            val viewModel: CartViewModel = hiltViewModel()
            CartScreen(
                viewModel = viewModel,
                onCheckoutClick = { navController.navigate(Screen.Checkout.route) }
            )
        }

        composable(Screen.Checkout.route) {
            val cartViewModel: CartViewModel = hiltViewModel()
            val checkoutViewModel: CheckoutViewModel = hiltViewModel()
            CheckoutScreen(
                cartViewModel = cartViewModel,
                checkoutViewModel = checkoutViewModel,
                onBackClick = { navController.popBackStack() },
                onManageAddressClick = { navController.navigate(Screen.SavedAddress.route) },
                onCheckoutSuccess = { orderId ->
                    navController.navigate(Screen.OrderSuccess.route) {
                        popUpTo(Screen.Checkout.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.OrderSuccess.route) {
            OrderSuccessScreen(
                onViewOrdersClick = {
                    navController.navigate(Screen.MyOrders.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                },
                onHomeClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.SavedAddress.route) {
            val viewModel: CheckoutViewModel = hiltViewModel()
            SavedAddressScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.PaymentMethods.route) {
            val viewModel: CheckoutViewModel = hiltViewModel()
            PaymentMethodsScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.MyOrders.route) {
            val viewModel: CheckoutViewModel = hiltViewModel()
            MyOrdersScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.SellerDashboard.route) {
            val viewModel: SellerViewModel = hiltViewModel()
            SellerDashboardScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onUploadProductClick = { productId ->
                    navController.navigate(Screen.UploadProduct.createRoute(productId))
                }
            )
        }

        composable(
            route = Screen.UploadProduct.route,
            arguments = listOf(navArgument("productId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            val viewModel: SellerViewModel = hiltViewModel()
            UploadProductScreen(
                productId = productId,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onUploadSuccess = { navController.popBackStack() }
            )
        }
    }
}
