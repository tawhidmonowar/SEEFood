package org.onedroid.seefood.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.onedroid.seefood.app.utils.UiText
import org.onedroid.seefood.app.utils.onError
import org.onedroid.seefood.app.utils.onSuccess
import org.onedroid.seefood.data.mappers.toUiText
import org.onedroid.seefood.domain.MealDetail
import org.onedroid.seefood.domain.MealRepository

class DetailViewModel(
    private val mealRepository: MealRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        const val MEAL_ID_KEY = "mealId"
    }

    var mealDetail by mutableStateOf<MealDetail?>(null)
        private set

    var isSearchLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<UiText?>(null)
        private set

    init {
        val mealId = savedStateHandle.get<String>(MEAL_ID_KEY)
        if (mealId != null) {
            fetchMealDetails(mealId)
        } else {
            error = UiText.DynamicString("Meal ID Not Found!")
        }
    }

    fun fetchMealDetails(mealId: String) = viewModelScope.launch {
        isSearchLoading = true
        mealRepository.getMealById(mealId).onSuccess {
            mealDetail = it
            isSearchLoading = false
            error = null
        }.onError {
            mealDetail = null
            error = it.toUiText()
        }
    }
}