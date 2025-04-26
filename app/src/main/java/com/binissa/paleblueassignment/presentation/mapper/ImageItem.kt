package com.binissa.paleblueassignment.presentation.mapper

import com.binissa.paleblueassignment.domain.model.Image
import com.binissa.paleblueassignment.presentation.screens.main.ImageItem

fun Image.toUI(): ImageItem = ImageItem(
    id = id,
    imageUrl = imageUrl,
    author = user,
    fullHdUrl = fullHdUrl,
    width = width,
    height = height,
    likes = likes,
    comments = comments,
    downloads = downloads,
    views = views,
)


fun List<Image>.toUI(): List<ImageItem> = map { it.toUI() }
