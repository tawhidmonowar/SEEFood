package org.onedroid.seefood.data.network

import org.onedroid.seefood.app.utils.DataError
import org.onedroid.seefood.app.utils.Result
import org.onedroid.seefood.data.dto.MealsDto

interface RemoteRecipeDataSource {
    suspend fun fetchRandomMeals(): Result<MealsDto, DataError.Remote>
}