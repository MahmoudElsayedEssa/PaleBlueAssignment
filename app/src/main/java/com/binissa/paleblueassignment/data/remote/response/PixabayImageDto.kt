package com.binissa.paleblueassignment.data.remote.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PixabayImageDto(
    val id: Int,
    val user: String,
    @SerializedName("webformatURL") val imageUrl: String,
    @SerializedName("largeImageURL") val fullHdUrl: String,
    @SerializedName("imageWidth") val width: Int,
    @SerializedName("imageHeight") val height: Int,
    val likes: Int,
    val comments: Int,
    val downloads: Int,
    val views: Int,
    @SerializedName("userImageURL") val userImageUrl: String
)


