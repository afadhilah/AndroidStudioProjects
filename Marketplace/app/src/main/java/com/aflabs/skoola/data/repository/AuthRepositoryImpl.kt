package com.aflabs.skoola.data.repository

import com.aflabs.skoola.data.remote.ApiService
import com.aflabs.skoola.data.remote.ForgotPasswordRequest
import com.aflabs.skoola.data.remote.LoginRequest
import com.aflabs.skoola.data.remote.OtpRequest
import com.aflabs.skoola.data.remote.RegisterRequest
import com.aflabs.skoola.domain.model.User
import com.aflabs.skoola.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {

    private val _currentUser = MutableStateFlow<User?>(null)

    override fun getCurrentUser(): Flow<User?> = _currentUser.asStateFlow()

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                val user = response.body()!!.copy(email = email)
                _currentUser.value = user
                Result.success(user)
            } else {
                Result.failure(Exception("Email atau password salah!"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(name: String, email: String, studentId: String, password: String): Result<User> {
        return try {
            val response = apiService.register(RegisterRequest(name, email, studentId, password))
            if (response.isSuccessful && response.body() != null) {
                val user = response.body()!!.copy(name = name, email = email, studentId = studentId)
                Result.success(user)
            } else {
                Result.failure(Exception("Pendaftaran gagal!"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun verifyOtp(email: String, otpCode: String): Result<User> {
        return try {
            if (otpCode.length != 6) {
                return Result.failure(Exception("Kode OTP harus 6 digit!"))
            }
            val response = apiService.verifyOtp(OtpRequest(email, otpCode))
            if (response.isSuccessful && response.body() != null) {
                val user = response.body()!!.copy(email = email)
                _currentUser.value = user
                Result.success(user)
            } else {
                Result.failure(Exception("Kode OTP tidak valid!"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendOtp(email: String): Result<Boolean> {
        return Result.success(true)
    }

    override suspend fun forgotPassword(email: String): Result<Boolean> {
        return try {
            apiService.forgotPassword(ForgotPasswordRequest(email))
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        _currentUser.value = null
    }

    override suspend fun updateProfile(name: String, phone: String, school: String, address: String): Result<User> {
        val current = _currentUser.value ?: return Result.failure(Exception("User tidak masuk!"))
        val updated = current.copy(name = name, phone = phone, school = school, address = address)
        _currentUser.value = updated
        return Result.success(updated)
    }
}
