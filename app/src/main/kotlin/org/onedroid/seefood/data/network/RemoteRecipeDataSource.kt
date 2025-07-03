package org.onedroid.seefood.data.network

import org.onedroid.seefood.app.utils.DataError
import org.onedroid.seefood.app.utils.Result
import org.onedroid.seefood.data.dto.CategoriesDto
import org.onedroid.seefood.data.dto.MealsDto

interface RemoteRecipeDataSource {
    suspend fun fetchRandomMeals(): Result<MealsDto, DataError.Remote>
    suspend fun fetchMealsByCategory(category: String): Result<MealsDto, DataError.Remote>
    suspend fun fetchCategoryList(): Result<CategoriesDto, DataError.Remote>
    suspend fun searchMeals(query: String): Result<MealsDto, DataError.Remote>
}