package org.onedroid.seefood.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.onedroid.seefood.app.navigation.components.BottomNavigationBar
import org.onedroid.seefood.app.navigation.components.BottomNavigationItemsLists
import org.onedroid.seefood.home.HomeScreen

@Composable
fun HomeNavigation(
) {
    val homeNavController = rememberNavController()
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                items = BottomNavigationItemsLists,
                onItemClick = {
                    homeNavController.navigate(it.route) {
                        popUpTo("home_screen") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = homeNavController,
            startDestination = "home_screen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home_screen") {
                HomeScreen()
            }
            composable("profile") {
                HomeScreen()
            }
            composable("save") {
                HomeScreen()
            }
        }
    }
}