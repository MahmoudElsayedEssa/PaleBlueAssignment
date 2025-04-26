package com.binissa.paleblueassignment.di

import android.content.Context
import androidx.room.Room
import com.binissa.paleblueassignment.data.local.database.ImageDatabase
import com.binissa.paleblueassignment.data.local.doa.ImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ImageDatabase {
        return Room.databaseBuilder(context, ImageDatabase::class.java, "image_db").build()
    }

    @Provides
    fun provideImageDao(db: ImageDatabase): ImageDao = db.imageDao()
}
