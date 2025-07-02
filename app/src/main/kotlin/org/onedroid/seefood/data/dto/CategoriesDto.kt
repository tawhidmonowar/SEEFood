package org.onedroid.seefood.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesDto(
    @SerialName("categories")
    val categories: List<CategoryDto>
)