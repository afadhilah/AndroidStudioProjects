package com.aflabs.skoola.domain.repository

import com.aflabs.skoola.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getCurrentUser(): Flow<User?>
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(name: String, email: String, studentId: String, password: String): Result<User>
    suspend fun verifyOtp(email: String, otpCode: String): Result<User>
    suspend fun sendOtp(email: String): Result<Boolean>
    suspend fun forgotPassword(email: String): Result<Boolean>
    suspend fun logout()
    suspend fun updateProfile(name: String, phone: String, school: String, address: String): Result<User>
}
