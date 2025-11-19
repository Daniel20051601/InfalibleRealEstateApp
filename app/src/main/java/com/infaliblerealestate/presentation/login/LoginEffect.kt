package com.infaliblerealestate.presentation.login

sealed interface LoginEffect {
    data class NavigateHome(val usuarioId: Int) : LoginEffect
}