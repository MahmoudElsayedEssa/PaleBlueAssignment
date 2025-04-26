package com.binissa.paleblueassignment.presentation.screens.main.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.binissa.paleblueassignment.presentation.screens.main.ImageItem

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ImageCard(
    item: ImageItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null
) {
    // If we're not in a shared transition scope, render normally
    if (sharedTransitionScope == null || animatedVisibilityScope == null) {
        OptimizedImageCardContent(item, onClick, modifier)
        return
    }

    // If we are in a shared transition scope, add shared element modifiers
    with(sharedTransitionScope) {
        OptimizedImageCardContent(
            item = item,
            onClick = onClick,
            modifier = modifier,
            imageModifier = Modifier.sharedElement(
                rememberSharedContentState(key = "image-${item.id}"),
                animatedVisibilityScope
            ),
            overlayModifier = Modifier.sharedElement(
                rememberSharedContentState(key = "overlay-${item.id}"),
                animatedVisibilityScope
            ),
            authorModifier = Modifier.sharedElement(
                rememberSharedContentState(key = "author-${item.id}"),
                animatedVisibilityScope
            ),
            containerModifier = Modifier.sharedElement(
                rememberSharedContentState(key = "container-${item.id}"),
                animatedVisibilityScope
            )
        )
    }
}

@Composable
private fun OptimizedImageCardContent(
    item: ImageItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    overlayModifier: Modifier = Modifier,
    authorModifier: Modifier = Modifier,
    containerModifier: Modifier = Modifier
) {
    // Create image request with optimized parameters
    val context = LocalContext.current
    val imageRequest = remember(item.imageUrl) {
        ImageRequest.Builder(context)
            .data(item.imageUrl)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .size(400)  // Limit size for better performance
            .build()
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick)
            .then(containerModifier),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            SubcomposeAsyncImage(
                model = imageRequest,
                contentDescription = "Image by ${item.author}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .then(imageModifier),
                loading = {
                    ShimmerImageCardPlaceholder()
                },
                error = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Error loading image",
                            tint = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            )

            // Author attribution overlay at bottom - optimized with less transparency
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                        )
                    )
                    .padding(8.dp)
                    .then(overlayModifier)
            ) {
                Text(
                    text = item.author,
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = authorModifier
                )

                Text(
                    text = item.id.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}