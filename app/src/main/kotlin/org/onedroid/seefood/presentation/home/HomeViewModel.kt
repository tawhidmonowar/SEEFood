package org.onedroid.seefood.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.onedroid.seefood.app.utils.SEARCH_TRIGGER_CHAR
import org.onedroid.seefood.app.utils.UiText
import org.onedroid.seefood.app.utils.onError
import org.onedroid.seefood.app.utils.onSuccess
import org.onedroid.seefood.data.mappers.toUiText
import org.onedroid.seefood.domain.Category
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

    var isMealLoading by mutableStateOf(false)
        private set

    private val cachedMeals = emptyList<Meal>()
    private var searchJob: Job? = null

    fun toggleSearch() {
        isSearchActive = !isSearchActive
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

    var errorMsgMeal by mutableStateOf<UiText?>(null)
        private set

    var searchErrorMsg by mutableStateOf<UiText?>(null)
        private set

    var meals by mutableStateOf<List<Meal>>(emptyList())
        private set

    var searchResult by mutableStateOf<List<Meal>>(emptyList())
        private set

    var categories by mutableStateOf<List<Category>>(emptyList())
        private set

    var selectedCategory by mutableStateOf("Beef")
        private set

    var selectedCategoryMeals by mutableStateOf<List<Meal>>(emptyList())
        private set


    init {
        getMeals()
        getCategories()
        getMealsByCategory(selectedCategory)
        if (cachedMeals.isEmpty()) {
            observeSearchQuery()
        }
    }

    fun onCategorySelected(category: String) {
        if (selectedCategory != category) {
            selectedCategory = category
            getMealsByCategory(category)
        }
    }

    fun getMeals() = viewModelScope.launch {
        isMealLoading = true
        mealRepository.getMeals().onSuccess {
            meals = it
            isMealLoading = false
        }.onError { error ->
            isMealLoading = false
            meals = emptyList()
            errorMsgMeal = error.toUiText()
        }
    }

    private fun getCategories() = viewModelScope.launch {
        mealRepository.getCategories().onSuccess {
            categories = it
        }.onError { error ->
            categories = emptyList()
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            snapshotFlow { searchQuery }.distinctUntilChanged().debounce(500L).collect { query ->
                when {
                    query.isBlank() -> {
                        searchErrorMsg = null
                        searchResult = cachedMeals
                    }

                    query.length >= SEARCH_TRIGGER_CHAR -> {
                        searchJob?.cancel()
                        searchJob = searchMeals(query)
                    }
                }
            }
        }
    }

    private fun searchMeals(query: String) = viewModelScope.launch {
        isSearchLoading = true
        mealRepository.searchMeals(query).onSuccess {
            isSearchLoading = false
            searchErrorMsg = null
            searchResult = it
        }.onError { error ->
            searchResult = emptyList()
            isSearchLoading = false
            searchErrorMsg = error.toUiText()
        }
    }

    private fun getMealsByCategory(category: String) = viewModelScope.launch {
        mealRepository.getMealsByCategory(category).onSuccess {
            selectedCategoryMeals = it
        }.onError {
            selectedCategoryMeals = emptyList()
        }
    }
}