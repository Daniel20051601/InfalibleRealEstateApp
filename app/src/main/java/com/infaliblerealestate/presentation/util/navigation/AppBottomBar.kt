package com.infaliblerealestate.presentation.util.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AppBottomBar(
    navController: NavController,
    currentRoute: String?,
    usuarioId: Int
) {

    NavigationBar(
        modifier = Modifier
            .height(70.dp)
            .clip(RoundedCornerShape(50.dp)),
    ) {
        BottomNavItem.items.forEach { item ->
            val baseRoute = item.screen.route.substringBefore("/")
            val selected = currentRoute?.startsWith(baseRoute) == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    val route = when (item.screen) {
                        is Screen.Home -> Screen.Home.createRoute(usuarioId)
                        is Screen.Settings -> Screen.Settings.createRoute(usuarioId)
                        is Screen.Carrito -> Screen.Carrito.createRoute(usuarioId)
                        is Screen.Catalogo -> Screen.Catalogo.createRoute(usuarioId)
                        else -> return@NavigationBarItem
                    }
                    navController.navigate(route) {
                        popUpTo(Screen.Home.createRoute(usuarioId))
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(0.dp))
            )
        }
    }
}

@Preview
@Composable
fun AppBottomBarPreview() {
    val navController = NavController(LocalContext.current)
    AppBottomBar(
        navController = navController,
        currentRoute = "home_screen/1",
        usuarioId = 1
    )

}
