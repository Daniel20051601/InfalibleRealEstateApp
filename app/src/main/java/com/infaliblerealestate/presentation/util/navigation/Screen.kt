package com.infaliblerealestate.presentation.util.navigation

sealed class Screen(val route: String){
    data object Home: Screen("home_screen/{id}"){
        const val ARG = "id"
        fun createRoute(id: String) = "home_screen/$id"
    }
    data object Settings: Screen("settings_screen/{id}"){
        const val ARG = "id"
        fun createRoute(id: String) = "settings_screen/$id"
    }

    data object Carrito: Screen("carrito_screen/{id}"){
        const val ARG = "id"
        fun createRoute(id: String) = "carrito_screen/$id"
    }

    data object Catalogo: Screen("catalogo_screen/{id}?categoria={categoria}"){
        const val ARG = "id"
        const val CATEGORIA_ARG = "categoria"
        fun createRoute(id: String, categoria: String? = null) =
            if (categoria != null) "catalogo_screen/$id?categoria=$categoria"
            else "catalogo_screen/$id"
    }


    data object Login: Screen("login_screen")
}