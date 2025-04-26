package com.binissa.paleblueassignment.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "images", indices = [Index("searchQuery")]
)
data class ImageEntity(
    @PrimaryKey val id: Int,
    val user: String,
    val imageUrl: String,
    val searchQuery: String,
    val timestamp: Long = System.currentTimeMillis(),
    val fullHdUrl: String,
    val width: Int,
    val height: Int,
    val likes: Int,
    val comments: Int,
    val downloads: Int,
    val views: Int,
)
