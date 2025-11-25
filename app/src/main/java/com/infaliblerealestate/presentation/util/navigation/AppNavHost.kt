package com.infaliblerealestate.presentation.util.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontVariation
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
            route =Screen.Home.route,
            arguments = listOf(
                navArgument(Screen.Home.ARG){type = NavType.IntType}
            )
        ) {backStackEntry ->
            val id = backStackEntry.arguments?.getInt(Screen.Home.ARG)
            HomeScreen(
                navController,
                usuarioId = id
            )
        }

        composable(
            route = Screen.Settings.route,
            arguments = listOf(
                navArgument(Screen.Settings.ARG){type = NavType.IntType}
            )
        ){
            backStackEntry ->
            val id = backStackEntry.arguments?.getInt(Screen.Settings.ARG)
            SettingsScreen(
                navController,
                usuarioId = id
            )
        }

        composable(
            route = Screen.Carrito.route,
            arguments = listOf(
                navArgument(Screen.Carrito.ARG){type = NavType.IntType}
            )
        ){
            backStackEntry ->
            val id = backStackEntry.arguments?.getInt(Screen.Carrito.ARG)
            CarritoScreen(
                 usuarioId = id
             )
        }

        composable(
            route = Screen.Catalogo.route,
            arguments = listOf(
                navArgument(Screen.Catalogo.ARG){type = NavType.IntType}
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(Screen.Catalogo.ARG)
            CatalogoScreen(
                usuarioId = id
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
    }

}