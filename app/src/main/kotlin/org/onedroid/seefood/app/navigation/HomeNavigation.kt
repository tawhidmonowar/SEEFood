package org.onedroid.seefood.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.onedroid.seefood.app.navigation.components.BottomNavigationBar
import org.onedroid.seefood.app.navigation.components.BottomNavigationItemsLists
import org.onedroid.seefood.presentation.home.HomeScreen
import org.onedroid.seefood.presentation.home.HomeViewModel
import org.onedroid.seefood.presentation.home.components.HomeTopAppBar
import org.onedroid.seefood.presentation.home.components.MealSearchResult
import org.onedroid.seefood.presentation.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNavigation(
    viewModel: HomeViewModel = koinViewModel(),
    rootNavController: NavController
) {
    val homeNavController = rememberNavController()
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            HomeTopAppBar(
                rootNavController = homeNavController,
                searchQuery = viewModel.searchQuery,
                updateSearchQuery = viewModel::updateSearchQuery,
                isSearchActive = viewModel.isSearchActive,
                toggleSearch = viewModel::toggleSearch,
                onAboutClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Not implemented yet due to time constraints.")
                    }
                },
                onSettingsClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Not implemented yet due to time constraints.")
                    }
                },
                searchResultContent = {
                    MealSearchResult(
                        isSearchLoading = viewModel.isSearchLoading,
                        searchErrorMsg = viewModel.searchErrorMsg,
                        searchResult = viewModel.searchResult,
                        onMealClick = {
                            viewModel.toggleSearch()
                        }
                    )
                },
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
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = Color.White,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = homeNavController,
            startDestination = "home_screen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home_screen") {
                HomeScreen(
                    viewModel = viewModel,
                    rootNavController = rootNavController
                )
            }
            composable("profile") {
                ProfileScreen()
            }
            composable("save") {

            }
        }
    }
}