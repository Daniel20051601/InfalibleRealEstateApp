package com.infaliblerealestate.presentation.home

import com.infaliblerealestate.dominio.model.Propiedades

data class HomeUiState(
    val usuarioId: String? = null,
    val isLoading: Boolean = false,
    val userMessage: String? = null,
    val propiedad: Propiedades? = null,
    val propiedadId: Int? = null,
    val propiedades: List<Propiedades> = emptyList(),
    val showSheet: Boolean = false,
    val categoriaSeleccionada: String? = null,
    val categorias: List<String> = emptyList()
    )