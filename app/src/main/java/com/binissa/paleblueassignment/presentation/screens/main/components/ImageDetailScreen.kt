package com.binissa.paleblueassignment.presentation.screens.main.components

import Comment
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.binissa.paleblueassignment.presentation.screens.main.ImageItem
import com.binissa.paleblueassignment.presentation.theme.icons.Download
import com.binissa.paleblueassignment.presentation.theme.icons.Visibility
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource

@OptIn(
    ExperimentalSharedTransitionApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalHazeApi::class
)
@Composable
fun ImageDetailScreen(
    imageItem: ImageItem,
    onBackClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) {
    val scrollState = rememberScrollState()
    val hazeState = remember { HazeState() }

    val scrollProgress by animateFloatAsState(
        targetValue = (scrollState.value / 600f).coerceIn(0f, 1f), label = "scrollProgress"
    )

    with(sharedTransitionScope) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Photo by ${imageItem.author}",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.alpha(scrollProgress)
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .padding(8.dp)
                                .background(Color.Transparent, CircleShape)
                                .size(40.dp)
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = scrollProgress * 0.95f)
                    ),
                )
            }, containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(bottom = 16.dp)
                    .sharedBounds(
                        rememberSharedContentState(key = "container-${imageItem.id}"),
                        animatedVisibilityScope,
                        clipInOverlayDuringTransition = OverlayClip(RoundedCornerShape(24.dp))
                    )
                    .hazeSource(hazeState)
            ) {
                // Hero image with parallex effect
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-scrollState.value * 0.2f).dp)
                ) {
                    // Background blur (decorative)
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageItem.fullHdUrl.ifEmpty { imageItem.imageUrl })
                            .crossfade(true).build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.5f)
                            .blur(20.dp)
                            .alpha(0.5f)
                    )

                    // Main image
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = paddingValues.calculateTopPadding(),
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 0.dp
                            )
                    ) {
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageItem.fullHdUrl.ifEmpty { imageItem.imageUrl })
                                .crossfade(true).diskCachePolicy(CachePolicy.ENABLED)
                                .memoryCachePolicy(CachePolicy.ENABLED).build(),
                            contentDescription = "Image by ${imageItem.author}",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(
                                    if (imageItem.width > 0 && imageItem.height > 0) {
                                        imageItem.width.toFloat() / imageItem.height
                                    } else 1.5f
                                )
                                .clip(RoundedCornerShape(24.dp))
                                .shadow(12.dp, RoundedCornerShape(24.dp))
                                .sharedElement(
                                    rememberSharedContentState(key = "image-${imageItem.id}"),
                                    animatedVisibilityScope
                                )
                        )
                    }
                }

                // Author attribution
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .offset(y = (-20).dp),
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(16.dp),
                    shadowElevation = 6.dp,
                    tonalElevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .sharedElement(
                                rememberSharedContentState(key = "author-${imageItem.id}"),
                                animatedVisibilityScope
                            )
                    ) {
                        Text(
                            text = imageItem.author,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Image ID: ${imageItem.id}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Stats section
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(24.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                    tonalElevation = 1.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatItemWithIcon(
                            value = formatNumber(imageItem.views),
                            label = "Views",
                            icon = Visibility,
                            tint = MaterialTheme.colorScheme.primary
                        )

                        StatItemWithIcon(
                            value = formatNumber(imageItem.likes),
                            label = "Likes",
                            icon = Icons.Default.ThumbUp,
                            tint = MaterialTheme.colorScheme.tertiary
                        )

                        StatItemWithIcon(
                            value = formatNumber(imageItem.downloads),
                            label = "Downloads",
                            icon = Download,
                            tint = MaterialTheme.colorScheme.secondary
                        )

                        StatItemWithIcon(
                            value = formatNumber(imageItem.comments),
                            label = "Comments",
                            icon = Comment,
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Resolution section
                Text(
                    text = "Image Details",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 1.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        DetailRow("Resolution", "${imageItem.width} × ${imageItem.height} px")

                        Spacer(modifier = Modifier.height(8.dp))

                        DetailRow(
                            "Aspect Ratio", if (imageItem.width > 0 && imageItem.height > 0) {
                                val gcd = gcd(imageItem.width, imageItem.height)
                                "${imageItem.width / gcd}:${imageItem.height / gcd}"
                            } else {
                                "Unknown"
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        DetailRow("Popularity Rank", calculatePopularityRank(imageItem))
                    }
                }
            }
        }
    }
}

@Composable
private fun StatItemWithIcon(
    value: String, label: String, icon: ImageVector, tint: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(tint.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = tint,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

private fun formatNumber(number: Int): String {
    return when {
        number >= 1_000_000 -> String.format("%.1fM", number / 1_000_000f)
        number >= 1_000 -> String.format("%.1fK", number / 1_000f)
        else -> number.toString()
    }
}

private fun calculatePopularityRank(imageItem: ImageItem): String {
    // Create a score based on likes, views, downloads
    val score = imageItem.likes * 2 + imageItem.views / 10 + imageItem.downloads * 3

    return when {
        score > 50000 -> "★★★★★"
        score > 10000 -> "★★★★☆"
        score > 5000 -> "★★★☆☆"
        score > 1000 -> "★★☆☆☆"
        else -> "★☆☆☆☆"
    }
}

// Greatest Common Divisor function for calculating aspect ratio
private fun gcd(a: Int, b: Int): Int {
    return if (b == 0) a else gcd(b, a % b)
}