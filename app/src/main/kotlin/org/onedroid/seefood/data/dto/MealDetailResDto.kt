package org.onedroid.seefood.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealDetailResDto(
    @SerialName("meals")
    val meals: List<MealDetailDto>
)
