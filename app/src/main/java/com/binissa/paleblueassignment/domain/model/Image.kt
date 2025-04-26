package com.binissa.paleblueassignment.domain.model

data class Image(
    val id: Int,
    val user: String,
    val imageUrl: String,
    val fullHdUrl: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val likes: Int = 0,
    val comments: Int = 0,
    val downloads: Int = 0,
    val views: Int = 0,
)
