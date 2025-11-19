package com.infaliblerealestate.presentation.validation

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null,
    val usuarioId: Int? = null
)