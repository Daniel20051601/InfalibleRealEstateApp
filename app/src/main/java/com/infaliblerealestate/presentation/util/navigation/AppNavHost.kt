package com.infaliblerealestate.presentation.util.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.infaliblerealestate.presentation.carrito.CarritoScreen
import com.infaliblerealestate.presentation.catalogo.CatalogoScreen
import com.infaliblerealestate.presentation.home.HomeScreen
import com.infaliblerealestate.presentation.login.LoginScreen
import com.infaliblerealestate.presentation.settings.SettingsScreen
import com.infaliblerealestate.presentation.upsertPropiedad.UpsertPropiedadScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ){
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.Settings.route,
            arguments = listOf(
                navArgument(Screen.Settings.ARG) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(Screen.Settings.ARG)
            SettingsScreen(
                navController = navController,
                usuarioId = id
            )
        }

        composable(
            route = Screen.Carrito.route,
            arguments = listOf(
                navArgument(Screen.Carrito.ARG) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(Screen.Carrito.ARG)
            CarritoScreen(usuarioId = id)
        }

        composable(
            route = Screen.Catalogo.route,
            arguments = listOf(
                navArgument(Screen.Catalogo.CATEGORIA_ARG) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val categoria = backStackEntry.arguments?.getString(Screen.Catalogo.CATEGORIA_ARG)
            CatalogoScreen(
                categoriaInicial = categoria,
                navController = navController,
            )
        }

        composable(
            route = Screen.UpsertPropiedad.route,
            arguments = listOf(
                navArgument(Screen.UpsertPropiedad.USUARIO_ID_ARG) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(Screen.UpsertPropiedad.PROPIEDAD_ID_ARG) {
                    type = NavType.IntType
                    defaultValue = -1 // Usar -1 como valor por defecto para indicar "no presente"
                }
            )
        ) { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getString(Screen.UpsertPropiedad.USUARIO_ID_ARG)
            val propiedadId = backStackEntry.arguments?.getInt(Screen.UpsertPropiedad.PROPIEDAD_ID_ARG)
            UpsertPropiedadScreen(
                usuarioId = usuarioId,
                propiedadId = if (propiedadId == -1) null else propiedadId
            )
        }



        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
    }
}