package org.onedroid.seefood.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import org.onedroid.seefood.app.navigation.components.BottomNavigationBar
import org.onedroid.seefood.app.navigation.components.BottomNavigationItemsLists
import org.onedroid.seefood.presentation.home.HomeScreen
import org.onedroid.seefood.presentation.home.HomeViewModel
import org.onedroid.seefood.presentation.home.components.HomeTopAppBar
import org.onedroid.seefood.presentation.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNavigation(
    viewModel: HomeViewModel = koinViewModel()
) {
    val homeNavController = rememberNavController()
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            HomeTopAppBar(
                rootNavController = homeNavController,
                searchQuery = viewModel.searchQuery,
                updateSearchQuery = viewModel::updateSearchQuery,
                isSearchActive = viewModel.isSearchActive,
                toggleSearch = viewModel::toggleSearch,
                onAboutClick = {},
                searchResultContent = {},
                scrollBehavior = scrollBehavior,
            )
        },
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
                ProfileScreen()
            }
            composable("save") {

            }
        }
    }
}