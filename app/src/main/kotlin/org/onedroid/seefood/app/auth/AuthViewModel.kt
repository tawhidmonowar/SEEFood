package org.onedroid.seefood.app.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AuthViewModel(
    val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var isAuthenticated by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isPasswordResetSent by mutableStateOf(false)
        private set

    init {
        isAuthenticated = authRepository.currentUser != null
        viewModelScope.launch {
            authRepository.getAuthState().collect {
                isAuthenticated = it
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            authRepository.signUp(email, password).onSuccess {
                isLoading = false
            }.onFailure { exception ->
                isLoading = false
                errorMessage = exception.message
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            authRepository.signIn(email, password).onSuccess {
                isLoading = false
            }.onFailure { exception ->
                isLoading = false
                errorMessage = exception.message
            }
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            authRepository.resetPassword(email).onSuccess {
                isLoading = false
                isPasswordResetSent = true
            }.onFailure { exception ->
                isLoading = false
                errorMessage = exception.message
            }
        }
    }

    fun signOut() {
        authRepository.signOut()
    }

    fun clearErrorMessage() {
        errorMessage = null
    }

    fun clearPasswordResetState() {
        isPasswordResetSent = false
    }

    fun deleteAccount() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            authRepository.deleteAccount().onSuccess {
                isLoading = false
            }.onFailure { exception ->
                isLoading = false
                errorMessage = exception.message
            }
        }
    }
}