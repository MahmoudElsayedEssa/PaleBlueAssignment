package com.binissa.paleblueassignment.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class PixabayResponseDto(
    val hits: List<PixabayImageDto>
)