package org.onedroid.seefood.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    @SerialName("idCategory")
    val idCategory: String,
    @SerialName("strCategory")
    val strCategory: String,
    @SerialName("strCategoryThumb")
    val strCategoryThumb: String,
    @SerialName("strCategoryDescription")
    val strCategoryDescription: String
)