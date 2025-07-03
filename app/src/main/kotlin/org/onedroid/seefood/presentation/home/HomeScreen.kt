package org.onedroid.seefood.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.onedroid.seefood.presentation.home.components.CategoryItem
import org.onedroid.seefood.presentation.home.components.Feed
import org.onedroid.seefood.presentation.home.components.FeedTitle
import org.onedroid.seefood.presentation.home.components.MealGridItem
import org.onedroid.seefood.presentation.home.components.TrendingItemCard
import org.onedroid.seefood.presentation.home.components.single
import org.onedroid.seefood.presentation.home.components.title

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    rootNavController: NavController
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
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp),
                            onClick = {
                                rootNavController.navigate("detail_screen" + "/${viewModel.meals[index].idMeal}")
                            }
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
            LazyRow(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 10.dp),
            ) {
                items(viewModel.categories) { category ->
                    CategoryItem(
                        category = category,
                        isSelected = viewModel.selectedCategory == category.strCategory,
                        onCategorySelected = { selected ->
                            viewModel.onCategorySelected(selected)
                        }
                    )
                }
            }

        }

        if (viewModel.selectedCategoryMeals.isNotEmpty()) {
            items(
                count = viewModel.selectedCategoryMeals.size,
            ) { index ->
                MealGridItem(
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
                    meal = viewModel.selectedCategoryMeals[index],
                    onClick = {
                        rootNavController.navigate("detail_screen" + "/${viewModel.selectedCategoryMeals[index].idMeal}")
                    }
                )
            }
        }
    }
}
