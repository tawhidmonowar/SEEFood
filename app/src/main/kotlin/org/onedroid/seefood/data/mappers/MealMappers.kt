package org.onedroid.seefood.data.mappers

import org.onedroid.seefood.data.dto.CategoriesDto
import org.onedroid.seefood.data.dto.CategoryDto
import org.onedroid.seefood.data.dto.MealDto
import org.onedroid.seefood.domain.Category
import org.onedroid.seefood.domain.Meal

fun MealDto.toMeal(): Meal {
    return Meal(
        idMeal = idMeal,
        strMeal = strMeal,
        strMealThumb = strMealThumb
    )
}

fun List<MealDto>.toMealList(): List<Meal> {
    return map { it.toMeal() }
}

fun CategoryDto.toCategory(): Category {
    return Category(
        idCategory = idCategory,
        strCategory = strCategory,
        strCategoryThumb = strCategoryThumb,
        strCategoryDescription = strCategoryDescription
    )
}

fun List<CategoryDto>.toCategoryList(): List<Category> {
    return map { it.toCategory() }
}