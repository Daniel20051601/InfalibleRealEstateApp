package com.infaliblerealestate.presentation.login

data class LoginUiState (
    val isLoading: Boolean = false,
    val usuarioId: Int? = null,
    val userName: String = "",
    val userNameError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isRegistering: Boolean = false,
    val userMessage: String = ""
)