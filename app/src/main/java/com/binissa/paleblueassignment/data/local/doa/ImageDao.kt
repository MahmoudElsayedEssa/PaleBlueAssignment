package com.binissa.paleblueassignment.data.local.doa

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.binissa.paleblueassignment.data.local.entities.ImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {

    @Query("SELECT * FROM images WHERE searchQuery = :query ORDER BY id ASC")
    fun getImagesPaged(query: String): PagingSource<Int, ImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(images: List<ImageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<ImageEntity>)

    @Query("DELETE FROM images WHERE searchQuery = :query")
    suspend fun deleteImagesByQuery(query: String)

    @Query("SELECT COUNT(*) FROM images WHERE searchQuery = :query")
    suspend fun getImageCount(query: String): Int
}
