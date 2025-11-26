package com.infaliblerealestate.presentation.home

interface HomeUiEvent {
    data object UserMessageShown : HomeUiEvent
    data object ShowSheet : HomeUiEvent
    data object HideSheet : HomeUiEvent
    data class CategoriaSeleccionada(val categoria: String): HomeUiEvent
    data class LoadPropiedad(val id: Int): HomeUiEvent
}