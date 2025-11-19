package com.infaliblerealestate.presentation.home

interface HomeUiEvent {
    data class LoadUser(val usuarioId: Int) : HomeUiEvent
    data class UserNameChanged(val userName: String) : HomeUiEvent
    data class PasswordChanged(val password: String) : HomeUiEvent
    data object Save : HomeUiEvent
    data object Logout : HomeUiEvent
    data object UserMessageShown : HomeUiEvent
    data object showDialogEdit: HomeUiEvent
    data object hideDialogEdit: HomeUiEvent
}