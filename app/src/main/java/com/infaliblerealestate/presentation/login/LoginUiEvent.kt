package com.infaliblerealestate.presentation.login

interface LoginUiEvent {
    data class userNameChanged(val userName: String) : LoginUiEvent
    data class passwordChanged(val password: String) : LoginUiEvent
    data object registerModeClicked: LoginUiEvent
    data object submitRegistration: LoginUiEvent
    data object submitLogin: LoginUiEvent
    data object loginModeClicked: LoginUiEvent
    data object userMessageShown: LoginUiEvent
}