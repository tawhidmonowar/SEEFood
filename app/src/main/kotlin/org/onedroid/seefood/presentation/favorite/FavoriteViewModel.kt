package org.onedroid.seefood.presentation.favorite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.onedroid.seefood.data.repository.FavoriteRepository
import org.onedroid.seefood.domain.Meal

class FavoriteViewModel(
    private val repository: FavoriteRepository
) : ViewModel() {

    var favoriteMeals by mutableStateOf<List<Meal>>(emptyList())
        private set

    var isMealFavorite by mutableStateOf(false)
        private set

    var isMealFavoriteLoading by mutableStateOf(false)
        private set

    fun checkIfFavorite(mealId: String, userId: String) {
        repository.isMealFavorite(mealId, userId) { exists ->
            isMealFavorite = exists
        }
    }

    fun loadFavorites(userId: String) {
        isMealFavoriteLoading = true
        repository.getFavoriteMeals(userId) {
            favoriteMeals = it
            isMealFavoriteLoading = false
        }
    }

    fun toggleFavorite(meal: Meal, userId: String) {
        if (isMealFavorite) {
            meal.idMeal?.let {
                repository.removeMealFromFavorites(it, userId) { success ->
                    if (success) {
                        isMealFavorite = false
                        loadFavorites(userId)
                    }
                }
            }
        } else {
            repository.addMealToFavorites(meal, userId) { success ->
                if (success) {
                    isMealFavorite = true
                    loadFavorites(userId)
                }
            }
        }
    }
}