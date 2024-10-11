package com.yerayyas.cursofirebaselite.domain.auth

sealed class AuthState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data object Authenticated : AuthState()
    data object UnAuthenticated : AuthState()
    data class Error(val message: String) : AuthState()
}