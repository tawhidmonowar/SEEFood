package org.onedroid.seefood.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.onedroid.seefood.home.HomeScreen

@Composable
fun HomeNavigation(
) {
    val homeNavController = rememberNavController()
    Scaffold(
        bottomBar = {
            Text("Hello") // This is your bottom bar content
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
        }
    }
}