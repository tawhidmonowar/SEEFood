package org.onedroid.seefood.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.onedroid.seefood.app.utils.onError
import org.onedroid.seefood.app.utils.onSuccess
import org.onedroid.seefood.domain.Meal
import org.onedroid.seefood.domain.MealDetail
import org.onedroid.seefood.domain.MealRepository

class DetailViewModel(
    private val mealRepository: MealRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object { const val MEAL_ID_KEY = "mealId"}
    var mealDetail by mutableStateOf<MealDetail?>(null)
        private set

    var isSearchLoading by mutableStateOf(false)
        private set

    init {
        val mealId = savedStateHandle.get<String>(MEAL_ID_KEY)
        if (mealId != null) {
            fetchMealDetails(mealId)
        } else {
            println("Meal ID not found.")
        }
    }

    private fun fetchMealDetails(mealId: String) = viewModelScope.launch {
        isSearchLoading = true
        mealRepository.getMealById(mealId).onSuccess {
            mealDetail = it
            isSearchLoading = false
        }.onError { error ->
            mealDetail = null
        }
    }
}