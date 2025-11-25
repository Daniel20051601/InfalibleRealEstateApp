package com.infaliblerealestate.presentation.util.navigation

sealed class Screen(val route: String){
    data object Home: Screen("home_screen/{id}"){
        const val ARG = "id"
        fun createRoute(id: Int) = "home_screen/$id"
    }
    data object Settings: Screen("settings_screen/{id}"){
        const val ARG = "id"
        fun createRoute(id: Int) = "settings_screen/$id"
    }

    data object Carrito: Screen("carrito_screen/{id}"){
        const val ARG = "id"
        fun createRoute(id: Int) = "carrito_screen/$id"
    }

    data object Catalogo: Screen("catalogo_screen/{id}"){
        const val ARG = "id"
        fun createRoute(id: Int) = "catalogo_screen/$id"
    }

    data object Login: Screen("login_screen")
}