package com.binissa.paleblueassignment.data.local.mappers

import com.binissa.paleblueassignment.data.local.entities.ImageEntity
import com.binissa.paleblueassignment.domain.model.Image

fun ImageEntity.toDomain(): Image = Image(
    id = id, user = user, imageUrl = imageUrl,
    fullHdUrl = fullHdUrl,
    width = width,
    height = height,
    likes = likes,
    comments = comments,
    downloads = downloads,
    views = views,
)

fun List<ImageEntity>.toDomain(): List<Image> = map { it.toDomain() }
