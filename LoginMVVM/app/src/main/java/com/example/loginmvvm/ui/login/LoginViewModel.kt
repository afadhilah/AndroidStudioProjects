package com.example.loginmvvm.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginmvvm.data.local.User
import com.example.loginmvvm.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface LoginUiState {
    object Idle : LoginUiState
    object Loading : LoginUiState
    data class Success(val message: String) : LoginUiState
    data class Error(val message: String) : LoginUiState
}

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onUsernameChange(value: String) {
        _username.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }

    fun clearState() {
        _uiState.value = LoginUiState.Idle
    }

    fun login() {
        val currentUsername = _username.value.trim()
        val currentPassword = _password.value.trim()

        if (currentUsername.isEmpty() || currentPassword.isEmpty()) {
            _uiState.value = LoginUiState.Error("Username dan password tidak boleh kosong!")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                val user = repository.getUserByUsername(currentUsername)
                if (user != null && user.password == currentPassword) {
                    _uiState.value = LoginUiState.Success("Login berhasil! Selamat datang, $currentUsername.")
                } else {
                    _uiState.value = LoginUiState.Error("Username atau password salah!")
                }
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error("Terjadi kesalahan: ${e.message}")
            }
        }
    }

    fun register() {
        val currentUsername = _username.value.trim()
        val currentPassword = _password.value.trim()

        if (currentUsername.isEmpty() || currentPassword.isEmpty()) {
            _uiState.value = LoginUiState.Error("Username dan password tidak boleh kosong!")
            return
        }
        
        if (currentPassword.length < 4) {
            _uiState.value = LoginUiState.Error("Password minimal harus 4 karakter!")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                val existingUser = repository.getUserByUsername(currentUsername)
                if (existingUser != null) {
                    _uiState.value = LoginUiState.Error("Username sudah terdaftar!")
                } else {
                    val newUser = User(username = currentUsername, password = currentPassword)
                    repository.insertUser(newUser)
                    _uiState.value = LoginUiState.Success("Registrasi berhasil! Silakan login.")
                }
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error("Gagal registrasi: ${e.message}")
            }
        }
    }
}
