package com.infaliblerealestate.presentation.home

sealed interface HomeEffect {
    data object NavigateLogin : HomeEffect
}