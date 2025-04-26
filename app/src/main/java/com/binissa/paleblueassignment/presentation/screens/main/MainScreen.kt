package com.binissa.paleblueassignment.presentation.screens.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.binissa.paleblueassignment.presentation.screens.main.components.ImageCard
import com.binissa.paleblueassignment.presentation.screens.main.components.ImageDetailScreen
import com.binissa.paleblueassignment.presentation.screens.main.components.SearchBar
import com.binissa.paleblueassignment.presentation.theme.icons.PhotoLibrary
import com.binissa.paleblueassignment.presentation.theme.icons.SearchOff
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource


@OptIn(
    ExperimentalSharedTransitionApi::class,
    ExperimentalAnimationApi::class,
    ExperimentalCoilApi::class
)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    state: MainState,
    images: LazyPagingItems<ImageItem>,
    actions: MainAction
) {
    val hazeState = remember { HazeState() }
    val gridState = rememberLazyGridState()

    val isAtTop by remember {
        derivedStateOf {
            gridState.firstVisibleItemIndex == 0 && (gridState.firstVisibleItemScrollOffset < 10)
        }
    }

    val hasNoResults = remember(images.loadState.refresh, images.itemCount, state.query) {
        images.itemCount == 0 && state.query.isNotEmpty() && images.loadState.refresh !is LoadState.Loading
    }

    SharedTransitionLayout {
        AnimatedContent(
            targetState = state.selectedImage,
            transitionSpec = {
                fadeIn(tween(300)) togetherWith fadeOut(tween(300)) using SizeTransform { _, _ ->
                    androidx.compose.animation.core.spring(
                        stiffness = 300f, dampingRatio = 0.8f
                    )
                }
            },
            label = "MainContentTransition"
        ) { selectedImage ->

            if (selectedImage == null) {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    AnimatedVisibility(
                        visible = isAtTop,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .statusBarsPadding()
                                .padding(top = 16.dp, bottom = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Image Gallery",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Text(
                                text = "Discover beautiful imagery",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                            )
                        }
                    }

                    LazyVerticalGrid(
                        state = gridState,
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = if (isAtTop) 140.dp else 90.dp,
                            bottom = 24.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .hazeSource(hazeState)
                    ) {
                        items(count = images.itemCount) { index ->
                            images[index]?.let { image ->
                                ImageCard(
                                    item = image,
                                    onClick = { actions.onSelectImage(image) },
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this@AnimatedContent,
                                )
                            }
                        }

                        when {
                            images.loadState.refresh is LoadState.Loading -> {
                                item(span = { GridItemSpan(2) }) {
                                    LoadingState(
                                        message = "Loading amazing images...",
                                        modifier = Modifier.padding(top = 80.dp)
                                    )
                                }
                            }

                            images.loadState.append is LoadState.Loading -> {
                                item(span = { GridItemSpan(2) }) {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(16.dp)
                                            .align(Alignment.Center),
                                        color = MaterialTheme.colorScheme.primary,
                                        strokeWidth = 3.dp
                                    )
                                }
                            }

                            images.loadState.append is LoadState.Error -> {
                                item(span = { GridItemSpan(2) }) {
                                    Text(
                                        text = "Couldn't load more images",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.error,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                            }

                            hasNoResults -> {
                                item(span = { GridItemSpan(2) }) {
                                    EmptySearchResults(
                                        query = state.query,
                                        modifier = Modifier.padding(top = 60.dp)
                                    )
                                }
                            }

                            images.itemCount == 0 -> {
                                item(span = { GridItemSpan(2) }) {
                                    EmptyState(
                                        modifier = Modifier.padding(top = 80.dp)
                                    )
                                }
                            }
                        }
                    }

                    SearchBar(
                        query = state.query,
                        hazeState = hazeState,
                        onQueryChange = actions.onQueryChange,
                        onSearch = actions.onSearch,
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(top = if (isAtTop) 80.dp else 16.dp)
                            .align(Alignment.TopCenter)
                    )
                }
            } else {
                ImageDetailScreen(
                    imageItem = selectedImage,
                    onBackClick = { actions.onClearSelectedImage() },
                    animatedVisibilityScope = this,
                    sharedTransitionScope = this@SharedTransitionLayout
                )
            }
        }
    }
}

@Composable
private fun LoadingState(
    message: String, modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
        )
    }
}

@Composable
private fun EmptyState(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        Icon(
            imageVector = PhotoLibrary,
            contentDescription = "No Images",
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Gallery is Empty",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Search for images to fill your gallery with inspiration",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
        )
    }
}

@Composable
private fun EmptySearchResults(
    query: String, modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        Icon(
            imageVector = SearchOff,
            contentDescription = "No Results",
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No results found for \"$query\"",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Try different keywords or check your spelling",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
        )
    }
}