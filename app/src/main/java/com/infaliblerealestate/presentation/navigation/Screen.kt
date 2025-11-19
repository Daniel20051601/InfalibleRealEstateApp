package com.infaliblerealestate.presentation.navigation

sealed class Screen(val route: String){
    data object Home: Screen("home_screen/{id}"){
        const val ARG = "id"
        fun createRoute(id: Int) = "home_screen/$id"
    }
    data object Login: Screen("login_screen")
}