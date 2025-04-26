package com.binissa.paleblueassignment.di

import com.binissa.paleblueassignment.data.local.database.ImageDatabase
import com.binissa.paleblueassignment.data.remote.api.PixabayApiService
import com.binissa.paleblueassignment.data.repository.ImageRepositoryImpl
import com.binissa.paleblueassignment.domain.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideImageRepository(
        api: PixabayApiService,
        db: ImageDatabase
    ): ImageRepository = ImageRepositoryImpl(
        api=api,
        db = db
    )
}
