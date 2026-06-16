package com.aflabs.skoola.data.remote

import com.aflabs.skoola.domain.model.*
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<User>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<User>

    @POST("auth/verify-otp")
    suspend fun verifyOtp(@Body request: OtpRequest): Response<User>

    @POST("auth/forgot-password")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<Unit>

    @GET("banners")
    suspend fun getBanners(): List<Banner>

    @GET("categories")
    suspend fun getCategories(): List<Category>

    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/{id}/reviews")
    suspend fun getProductReviews(@Path("id") id: String): List<Review>

    @POST("products/{id}/reviews")
    suspend fun addReview(@Path("id") id: String, @Body request: ReviewRequest): Review

    @POST("orders")
    suspend fun placeOrder(@Body request: OrderRequest): Order

    @GET("seller/{id}/stats")
    suspend fun getSellerStats(@Path("id") id: String): SalesStats
}

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val studentId: String,
    val password: String
)

@Serializable
data class OtpRequest(
    val email: String,
    val code: String
)

@Serializable
data class ForgotPasswordRequest(
    val email: String
)

@Serializable
data class ReviewRequest(
    val rating: Float,
    val comment: String
)

@Serializable
data class OrderRequest(
    val buyerId: String,
    val buyerName: String,
    val sellerId: String,
    val items: List<CartItem>,
    val totalPrice: Long,
    val shippingFee: Long,
    val shippingAddress: String,
    val paymentMethod: String
)
