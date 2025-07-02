package org.onedroid.seefood.data.repository

import org.onedroid.seefood.app.utils.DataError
import org.onedroid.seefood.app.utils.Result
import org.onedroid.seefood.app.utils.map
import org.onedroid.seefood.data.mappers.toMealList
import org.onedroid.seefood.data.network.RemoteRecipeDataSource
import org.onedroid.seefood.domain.Category
import org.onedroid.seefood.domain.Meal
import org.onedroid.seefood.domain.MealRepository

class MealRepositoryImpl (
    private val remoteRecipeDataSource: RemoteRecipeDataSource
): MealRepository {
    override suspend fun getMeals(): Result<List<Meal>, DataError.Remote> {
        return remoteRecipeDataSource.fetchRandomMeals().map {
            it.meals.toMealList()
        }
    }

    override suspend fun getCategories(): Result<List<Category>, DataError.Remote> {
        TODO("Not yet implemented")
    }

}