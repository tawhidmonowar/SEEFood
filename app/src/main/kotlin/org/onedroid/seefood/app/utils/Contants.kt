package org.onedroid.seefood.app.utils
import androidx.compose.ui.unit.dp

const val SEARCH_TRIGGER_CHAR = 3
const val USER_AGENT = "SEEFood/1.0 (tawhidmonowar@gmail.com)"
const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/filter.php"

val categories : List<String> = listOf(
    "Chicken", "Dessert", "Lamb", "Miscellaneous",
    "Pasta", "Seafood", "Side", "Starter", "Vegan",
    "Vegetarian", "Breakfast"
)

val randomCategory = categories.random()
val AppBarCollapsedHeight = 100.dp
val AppBarExpendedHeight = 400.dp