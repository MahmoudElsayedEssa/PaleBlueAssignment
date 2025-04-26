package com.binissa.paleblueassignment.presentation.screens.main

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

/**
 * Image item for UI representation
 */
data class ImageItem(
    val id: Int,
    val imageUrl: String,
    val author: String,
    val fullHdUrl: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val likes: Int = 0,
    val comments: Int = 0,
    val downloads: Int = 0,
    val views: Int = 0,
)

data class MainState(
    val query: String = "",
    val imagesFlow: Flow<PagingData<ImageItem>>? = null,
    val selectedImage: ImageItem? = null
)

data class MainAction(
    val onQueryChange: (query: String) -> Unit = {},
    val onSearch: () -> Unit = {},
    val onSelectImage: (ImageItem) -> Unit = {},
    val onClearSelectedImage: () -> Unit = {}
)