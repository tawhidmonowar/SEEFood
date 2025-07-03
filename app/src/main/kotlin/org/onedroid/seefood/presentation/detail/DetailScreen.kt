package org.onedroid.seefood.presentation.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.google.accompanist.insets.LocalWindowInsets
import org.koin.compose.viewmodel.koinViewModel
import org.onedroid.seefood.app.utils.AppBarCollapsedHeight
import org.onedroid.seefood.app.utils.AppBarExpendedHeight
import org.onedroid.seefood.data.mappers.toMeal
import org.onedroid.seefood.domain.MealDetail
import org.onedroid.seefood.presentation.favorite.FavoriteViewModel
import org.onedroid.seefood.presentation.home.components.ErrorMsgView
import kotlin.math.max
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    mealId: String? = null,
    userId: String? = null,
    rootNavController: NavController,
    viewModel: DetailViewModel = koinViewModel(),
    favoriteViewModel: FavoriteViewModel = koinViewModel()
) {

    val scrollState = rememberLazyListState()
    val imageHeight = AppBarExpendedHeight - AppBarCollapsedHeight
    val maxOffset =
        with(LocalDensity.current) { imageHeight.roundToPx() } - LocalWindowInsets.current.systemBars.layoutInsets.top
    val offset = min(scrollState.firstVisibleItemScrollOffset, maxOffset)
    val offsetProgress = max(0f, offset * 3f - 2f * maxOffset) / maxOffset

    val meal = viewModel.mealDetail
    val isFavorite = favoriteViewModel.isMealFavorite

    LaunchedEffect(mealId) {
        if (mealId != null && userId != null) {
            favoriteViewModel.checkIfFavorite(mealId, userId)
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = meal?.strMeal.orEmpty(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { rootNavController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (meal != null && userId != null) {
                                favoriteViewModel.toggleFavorite(meal.toMeal(), userId)
                            }
                        }
                    ) {
                        AnimatedContent(
                            targetState = isFavorite,
                            transitionSpec = {
                                fadeIn(tween(300)) + scaleIn(tween(300)) togetherWith
                                        fadeOut(tween(200)) + scaleOut(tween(200))
                            },
                            label = "FavoriteToggle"
                        ) { fav ->
                            Icon(
                                imageVector = if (fav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            state = scrollState
        ) {

            if (viewModel.error != null) {
                item {
                    ErrorMsgView(
                        onRetryClick = {
                            viewModel.fetchMealDetails(mealId!!)
                        },
                        errorMsg = viewModel.error!!
                    )
                }
            } else {

                item {
                    Box(
                        Modifier
                            .height(imageHeight)
                            .graphicsLayer { alpha = 1f - offsetProgress }
                    ) {
                        AsyncImage(
                            model = meal?.strMealThumb,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Transparent, White),
                                        startY = 0f,
                                        endY = Float.POSITIVE_INFINITY
                                    )
                                )
                        )

                        if (!meal?.strYoutube.isNullOrEmpty()) {
                            PlayButton(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(16.dp),
                                youtubeUrl = meal!!.strYoutube!!
                            )
                        }
                    }

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .height(AppBarCollapsedHeight),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = meal?.strMeal.orEmpty(),
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(horizontal = (16 + 28 * offsetProgress).dp)
                                .scale(1f - 0.25f * offsetProgress)
                        )
                    }
                }

                item {
                    InfoSection(meal)
                }

                item {
                    DescriptionSection(meal?.strInstructions.orEmpty())
                }

                if (!meal?.strYoutube.isNullOrEmpty()) {
                    item {
                        YoutubeButton(meal!!.strYoutube!!)
                    }
                }
            }
        }
    }
}

@Composable
fun InfoSection(meal: MealDetail?) {
    val ingredients = remember(meal) {
        (1..20).mapNotNull { index ->
            val ingredient = meal?.javaClass?.getDeclaredField("strIngredient$index")
                ?.apply { isAccessible = true }
                ?.get(meal) as? String

            val measure = meal?.javaClass?.getDeclaredField("strMeasure$index")
                ?.apply { isAccessible = true }
                ?.get(meal) as? String

            if (!ingredient.isNullOrBlank()) "$ingredient - ${measure.orEmpty()}" else null
        }
    }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text("Category: ${meal?.strCategory ?: "N/A"}", style = MaterialTheme.typography.bodyMedium)
        Text("Area: ${meal?.strArea ?: "N/A"}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(12.dp))

        Text("Ingredients:", fontWeight = FontWeight.Bold)
        ingredients.forEach {
            Text("• $it", style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun DescriptionSection(description: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Instructions", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(description, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun YoutubeButton(youtubeUrl: String) {
    val context = LocalContext.current
    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
            context.startActivity(intent)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Icon(Icons.Filled.PlayArrow, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Watch on YouTube")
    }
}

@Composable
fun PlayButton(
    modifier: Modifier = Modifier,
    youtubeUrl: String
) {
    val context = LocalContext.current
    IconButton(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
            context.startActivity(intent)
        },
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.8f), shape = CircleShape)
            .padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = "Play",
            modifier = Modifier.size(30.dp),
            tint = White
        )
    }
}