package org.onedroid.seefood.domain

import org.onedroid.seefood.app.utils.DataError
import org.onedroid.seefood.app.utils.Result

interface MealRepository {
    suspend fun getMeals(): Result<List<Meal>, DataError.Remote>
    suspend fun getMealsByCategory(category: String): Result<List<Meal>, DataError.Remote>
    suspend fun getCategories(): Result<List<Category>, DataError.Remote>
    suspend fun searchMeals(query: String): Result<List<Meal>, DataError.Remote>
}