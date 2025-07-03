package org.onedroid.seefood.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.onedroid.seefood.presentation.home.components.CategoryItem
import org.onedroid.seefood.presentation.home.components.Feed
import org.onedroid.seefood.presentation.home.components.FeedTitle
import org.onedroid.seefood.presentation.home.components.MealGridItem
import org.onedroid.seefood.presentation.home.components.TrendingItemCard
import org.onedroid.seefood.presentation.home.components.single
import org.onedroid.seefood.presentation.home.components.title

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
) {
    val gridState = rememberLazyGridState()

    Feed(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Adaptive(150.dp),
        state = gridState,
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {

        title(contentType = "trending-title") {
            FeedTitle(
                modifier = Modifier.padding(horizontal = 8.dp),
                title = "Trending Now \uD83D\uDD25",
                btnText = "See all"
            )
        }

        single {
            LazyRow {
                if (viewModel.meals.isNotEmpty()) {
                    items(viewModel.meals.size) { index ->
                        TrendingItemCard(
                            meal = viewModel.meals[index],
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
                        )
                    }
                }
            }
        }

        title(contentType = "popular-title") {
            FeedTitle(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 2.dp),
                title = "Popular Category"
            )
        }

        single {
            val categories =
                listOf("Salad", "Breakfast", "Appetizer", "Noodle", "Lunch", "Dinner", "Dessert")
            var selectedCategory by remember { mutableStateOf("Salad") }

            LazyRow(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 10.dp),
            ) {
                items(categories.size) { index ->
                    CategoryItem(
                        category = categories[index],
                        isSelected = selectedCategory == categories[index],
                        onCategorySelected = { selectedCategory = it }
                    )
                }
            }
        }


        items(
            count = viewModel.meals.size,
            key = { viewModel.meals[it].idMeal }
        ) { index ->
            MealGridItem(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
                meal = viewModel.meals[index],
                onClick = {

                }
            )
        }
    }
}
