package org.onedroid.seefood.app.navigation.components

import org.onedroid.seefood.R

data class BottomNavigationItem(
    val icon: Int,
    val title: String,
    val route: String
)

val BottomNavigationItemsLists = listOf(
    BottomNavigationItem(
        icon = R.drawable.ic_home,
        title = "Home",
        route = "home_screen"
    ),
    BottomNavigationItem(
        icon = R.drawable.ic_favorite,
        title = "Favorite Meals",
        route = "favorite"
    ),
    BottomNavigationItem(
        icon = R.drawable.ic_person,
        title = "Profile",
        route = "profile"
    )
)