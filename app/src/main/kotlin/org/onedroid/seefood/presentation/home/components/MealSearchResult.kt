package org.onedroid.seefood.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.onedroid.seefood.app.utils.UiText
import org.onedroid.seefood.domain.Meal

@Composable
fun MealSearchResult(
    isSearchLoading: Boolean,
    searchErrorMsg: UiText? = null,
    searchResult: List<Meal>,
    onMealClick: (Meal) -> Unit
) {
    val searchResultListState = rememberLazyListState()

    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (isSearchLoading) {
                    CircularProgressIndicator()
                } else {
                    when {
                        searchErrorMsg != null -> {
                            Text(
                                text = searchErrorMsg.asString(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        searchResult.isEmpty() -> {
                            Text(
                                text = "No search result found!",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        else -> {
                            MealList(
                                meals = searchResult,
                                onMealClick = { meal ->
                                    onMealClick(meal)
                                },
                                modifier = Modifier.fillMaxSize(),
                                scrollState = searchResultListState
                            )
                        }
                    }
                }
            }
        }
    }
}