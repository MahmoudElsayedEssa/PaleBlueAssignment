package com.binissa.paleblueassignment.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.binissa.paleblueassignment.data.remote.interceptors.OfflineCacheInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageLoaderModule {

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context, okHttpClient: OkHttpClient
    ): ImageLoader {
        return ImageLoader.Builder(context).crossfade(true).okHttpClient(okHttpClient).memoryCache {
            MemoryCache.Builder(context).maxSizePercent(0.30) // Use 30% of available memory
                .build()
        }.diskCache {
            DiskCache.Builder().directory(context.cacheDir.resolve("image_cache"))
                .maxSizeBytes(100L * 1024 * 1024) // 100MB cache
                .build()
        }
            .components {
                add(OfflineCacheInterceptor(context))
            }
            .respectCacheHeaders(false)
            .build()
    }
}
