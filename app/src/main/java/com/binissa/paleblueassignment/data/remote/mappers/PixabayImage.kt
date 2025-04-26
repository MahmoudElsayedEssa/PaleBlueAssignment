package com.binissa.paleblueassignment.data.remote.mappers

import com.binissa.paleblueassignment.data.remote.response.PixabayImageDto
import com.binissa.paleblueassignment.domain.model.Image

fun PixabayImageDto.toDomain(): Image = Image(
    id = id,
    user = user,
    imageUrl = imageUrl,
    fullHdUrl = fullHdUrl,
    width = width,
    height = height,
    likes = likes,
    comments = comments,
    downloads = downloads,
    views = views,
)


fun List<PixabayImageDto>.toDomain(): List<Image> = map { it.toDomain() }


