package com.aflabs.skoola.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aflabs.skoola.domain.model.User
import com.aflabs.skoola.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val currentUser: StateFlow<User?> = authRepository.getCurrentUser()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _eventFlow = MutableSharedFlow<AuthEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            authRepository.login(email, password)
                .onSuccess { user ->
                    _eventFlow.emit(AuthEvent.LoginSuccess(user))
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Login gagal"
                }
            _isLoading.value = false
        }
    }

    fun register(name: String, email: String, studentId: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            authRepository.register(name, email, studentId, password)
                .onSuccess {
                    _eventFlow.emit(AuthEvent.RegisterSuccess(email))
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Registrasi gagal"
                }
            _isLoading.value = false
        }
    }

    fun verifyOtp(email: String, code: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            authRepository.verifyOtp(email, code)
                .onSuccess { user ->
                    _eventFlow.emit(AuthEvent.VerificationSuccess(user))
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Verifikasi gagal"
                }
            _isLoading.value = false
        }
    }

    fun forgotPassword(email: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            authRepository.forgotPassword(email)
                .onSuccess {
                    _eventFlow.emit(AuthEvent.ForgotPasswordSent)
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Gagal mengirim link reset sandi"
                }
            _isLoading.value = false
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    fun updateProfile(name: String, phone: String, school: String, address: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            authRepository.updateProfile(name, phone, school, address)
                .onSuccess {
                    _eventFlow.emit(AuthEvent.ProfileUpdated)
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Gagal memperbarui profil"
                }
            _isLoading.value = false
        }
    }

    fun clearError() {
        _error.value = null
    }

    sealed class AuthEvent {
        data class LoginSuccess(val user: User) : AuthEvent()
        data class RegisterSuccess(val email: String) : AuthEvent()
        data class VerificationSuccess(val user: User) : AuthEvent()
        object ForgotPasswordSent : AuthEvent()
        object ProfileUpdated : AuthEvent()
    }
}
