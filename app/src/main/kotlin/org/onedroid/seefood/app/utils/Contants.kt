package org.onedroid.seefood.app.utils

const val SEARCH_TRIGGER_CHAR = 3
const val USER_AGENT = "SEEFood/1.0 (tawhidmonowar@gmail.com)"
const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/filter.php"

val categories : List<String> = listOf(
    "Chicken", "Dessert", "Lamb", "Miscellaneous",
    "Pasta", "Seafood", "Side", "Starter", "Vegan",
    "Vegetarian", "Breakfast", "Goat"
)

val randomCategory = categories.random()