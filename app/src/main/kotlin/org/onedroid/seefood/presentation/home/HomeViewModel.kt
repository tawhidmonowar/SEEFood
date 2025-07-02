package org.onedroid.seefood.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.onedroid.seefood.app.utils.UiText
import org.onedroid.seefood.app.utils.onError
import org.onedroid.seefood.app.utils.onSuccess
import org.onedroid.seefood.data.mappers.toUiText
import org.onedroid.seefood.domain.Meal
import org.onedroid.seefood.domain.MealRepository

class HomeViewModel(
    private val mealRepository: MealRepository
) : ViewModel() {
    var isSearchActive by mutableStateOf(false)
        private set

    var searchQuery by mutableStateOf("")
        private set

    var isSearchLoading by mutableStateOf(false)
        private set

    fun toggleSearch() {
        isSearchActive = !isSearchActive
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }
    var errorMsg by mutableStateOf<UiText?>(null)
        private set

    init {
        getMeals()
    }

    var meals by mutableStateOf<List<Meal>>(emptyList())
        private set

    private fun getMeals() = viewModelScope.launch {
        isSearchLoading = true
        mealRepository.getMeals().onSuccess {
            meals = it
            isSearchLoading = false
        }.onError {error ->
            isSearchLoading = false
            meals = emptyList()
            errorMsg = error.toUiText()
        }
    }
}