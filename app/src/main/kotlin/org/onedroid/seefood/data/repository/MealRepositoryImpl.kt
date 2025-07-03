package org.onedroid.seefood.data.repository

import org.onedroid.seefood.app.utils.DataError
import org.onedroid.seefood.app.utils.Result
import org.onedroid.seefood.app.utils.map
import org.onedroid.seefood.data.mappers.toCategoryList
import org.onedroid.seefood.data.mappers.toMailDetail
import org.onedroid.seefood.data.mappers.toMealList
import org.onedroid.seefood.data.network.RemoteRecipeDataSource
import org.onedroid.seefood.domain.Category
import org.onedroid.seefood.domain.Meal
import org.onedroid.seefood.domain.MealDetail
import org.onedroid.seefood.domain.MealRepository

class MealRepositoryImpl(
    private val remoteRecipeDataSource: RemoteRecipeDataSource
) : MealRepository {
    override suspend fun getMeals(): Result<List<Meal>, DataError.Remote> {
        return remoteRecipeDataSource.fetchRandomMeals().map {
            it.meals?.toMealList() ?: emptyList()
        }
    }

    override suspend fun getMealsByCategory(category: String): Result<List<Meal>, DataError.Remote> {
        return remoteRecipeDataSource.fetchMealsByCategory(category = category).map {
            it.meals?.toMealList() ?: emptyList()
        }
    }

    override suspend fun getCategories(): Result<List<Category>, DataError.Remote> {
        return remoteRecipeDataSource.fetchCategoryList().map {
            it.categories?.toCategoryList() ?: emptyList()
        }
    }

    override suspend fun searchMeals(query: String): Result<List<Meal>, DataError.Remote> {
        return remoteRecipeDataSource.searchMeals(query = query).map {
            it.meals?.toMealList() ?: emptyList()
        }
    }

    override suspend fun getMealById(id: String): Result<MealDetail, DataError.Remote> {
        return remoteRecipeDataSource.fetchMealById(id = id).map {
            it.meals[0].toMailDetail()
        }
    }
}