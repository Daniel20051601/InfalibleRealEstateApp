package com.infaliblerealestate.presentation.util.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val screen: Screen,
    val title: String,
    val icon: ImageVector
) {
    data object Home : BottomNavItem(
        screen = Screen.Home,
        title = "Home",
        icon = Icons.Filled.Home
    )

    data object Settings : BottomNavItem(
        screen = Screen.Settings,
        title = "Settings",
        icon = Icons.Filled.Settings
    )

    data object Carrito : BottomNavItem(
        screen = Screen.Carrito,
        title = "Carrito",
        icon = Icons.Filled.ShoppingCart
    )

    data object Catalogo : BottomNavItem(
        screen = Screen.Catalogo,
        title = "Catalogo",
        icon = Icons.Filled.ViewList
    )

    companion object {
        val items = listOf(Home, Carrito, Catalogo, Settings)
    }
}
