package org.onedroid.seefood.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import org.onedroid.seefood.app.utils.BASE_URL
import org.onedroid.seefood.app.utils.DataError
import org.onedroid.seefood.app.utils.Result
import org.onedroid.seefood.app.utils.USER_AGENT
import org.onedroid.seefood.app.utils.randomCategory
import org.onedroid.seefood.app.utils.safeCall
import org.onedroid.seefood.data.dto.CategoriesDto
import org.onedroid.seefood.data.dto.MealDetailResDto
import org.onedroid.seefood.data.dto.MealsDto

class RemoteRecipeDataSourceImpl(
    private val httpClient: HttpClient,
) : RemoteRecipeDataSource {

    override suspend fun fetchRandomMeals(): Result<MealsDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = BASE_URL
            ) {
                header("User-Agent", USER_AGENT)
                parameter("c", randomCategory)
            }
        }
    }

    override suspend fun fetchMealsByCategory(category: String): Result<MealsDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = BASE_URL
            ) {
                header("User-Agent", USER_AGENT)
                parameter("c", category)
            }
        }
    }

    override suspend fun fetchCategoryList(): Result<CategoriesDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "https://www.themealdb.com/api/json/v1/1/categories.php"
            ) {
                header("User-Agent", USER_AGENT)
            }
        }
    }

    override suspend fun searchMeals(query: String): Result<MealsDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = BASE_URL
            ) {
                header("User-Agent", USER_AGENT)
                parameter("i", query)
            }
        }
    }

    override suspend fun fetchMealById(id: String): Result<MealDetailResDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "https://www.themealdb.com/api/json/v1/1/lookup.php"
            ) {
                header("User-Agent", USER_AGENT)
                parameter("i", id)
            }
        }
    }
}