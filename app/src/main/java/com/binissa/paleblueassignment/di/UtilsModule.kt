package com.binissa.paleblueassignment.di

import android.content.Context
import com.binissa.paleblueassignment.domain.utils.ConnectionMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Provides
    @Singleton
    fun provideConnectionMonitor(
        @ApplicationContext context: Context
    ): ConnectionMonitor {
        return ConnectionMonitor(context)
    }
}