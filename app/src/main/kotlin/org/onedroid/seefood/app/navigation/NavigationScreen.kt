package org.onedroid.seefood.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.onedroid.seefood.app.auth.AuthViewModel
import org.onedroid.seefood.app.auth.ForgotPasswordScreen
import org.onedroid.seefood.app.auth.LoginScreen
import org.onedroid.seefood.app.auth.RegisterScreen
import org.onedroid.seefood.presentation.detail.DetailScreen

@Composable
fun NavigationScreen() {
    val rootNavController = rememberNavController()
    val viewModel: AuthViewModel = viewModel()

    LaunchedEffect(viewModel.isAuthenticated) {
        if (viewModel.isAuthenticated) {
            rootNavController.navigate("home") {
                popUpTo(0)
                launchSingleTop = true
            }
        } else {
            rootNavController.navigate("login") {
                popUpTo(0)
                launchSingleTop = true
            }
        }
    }

    NavHost(
        navController = rootNavController,
        startDestination = if (viewModel.isAuthenticated) "home" else "login",
    ) {
        composable("login") {
            LoginScreen(
                onNavigateToRegister = {
                    rootNavController.navigate("register")
                },
                onNavigateToForgotPassword = {
                    rootNavController.navigate("forgot_password")
                },
                viewModel = viewModel
            )
        }

        composable("register") {
            RegisterScreen(
                onNavigateToLogin = {
                    rootNavController.navigateUp()
                },
                viewModel = viewModel
            )
        }

        composable("forgot_password") {
            ForgotPasswordScreen(
                onNavigateBack = {
                    rootNavController.navigateUp()
                },
                viewModel = viewModel
            )
        }
        composable("home") {
            HomeNavigation(
                rootNavController = rootNavController
            )
        }
        composable("detail_screen" + "/{mealId}") { navBackStack ->
            val mealId = navBackStack.arguments?.getString("mealId")
            DetailScreen(
                mealId = mealId,
                rootNavController = rootNavController
            )
        }
    }
}
