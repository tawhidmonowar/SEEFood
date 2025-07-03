package org.onedroid.seefood.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import org.onedroid.seefood.app.utils.BASE_URL
import org.onedroid.seefood.app.utils.DataError
import org.onedroid.seefood.app.utils.Result
import org.onedroid.seefood.app.utils.USER_AGENT
import org.onedroid.seefood.app.utils.safeCall
import org.onedroid.seefood.data.dto.MealsDto

class RemoteRecipeDataSourceImpl(
    private val httpClient: HttpClient,
) : RemoteRecipeDataSource {

    private val categories: List<String> = listOf(
        "Beef", "Chicken", "Dessert", "Lamb", "Miscellaneous",
        "Pasta", "Seafood", "Side", "Starter", "Vegan",
        "Vegetarian", "Breakfast", "Goat"
    )

    private val randomCategory = categories.random()

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
}