package com.aflabs.skoola.presentation.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
    object Otp : Screen("otp/{email}") {
        fun createRoute(email: String) = "otp/$email"
    }
    object Home : Screen("home")
    object Search : Screen("search")
    object Cart : Screen("cart")
    object Checkout : Screen("checkout")
    object OrderSuccess : Screen("order_success")
    object Wishlist : Screen("wishlist")
    object Profile : Screen("profile")
    object SellerDashboard : Screen("seller_dashboard")
    object UploadProduct : Screen("upload_product?productId={productId}") {
        fun createRoute(productId: String?) = if (productId != null) "upload_product?productId=$productId" else "upload_product"
    }
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
    object SavedAddress : Screen("saved_address")
    object PaymentMethods : Screen("payment_methods")
    object MyOrders : Screen("my_orders")
}
