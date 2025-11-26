package com.infaliblerealestate.presentation.settings

import com.infaliblerealestate.dominio.model.Usuario

data class SettingsUiState(
    val nombre: String? = null,
    val apellido: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val isLoading: Boolean = false,
    val userMessage: String? = null,
    val usuario: Usuario? = null
)
