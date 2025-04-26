package com.binissa.paleblueassignment.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.binissa.paleblueassignment.data.local.doa.ImageDao
import com.binissa.paleblueassignment.data.local.entities.ImageEntity

@Database(entities = [ImageEntity::class], version = 1, exportSchema = false)
abstract class ImageDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}

