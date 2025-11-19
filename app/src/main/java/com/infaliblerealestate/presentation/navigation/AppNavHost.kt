package com.infaliblerealestate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.infaliblerealestate.presentation.home.HomeScreen
import com.infaliblerealestate.presentation.login.LoginScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
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
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
    }

}