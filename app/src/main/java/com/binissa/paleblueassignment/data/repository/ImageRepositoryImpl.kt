package com.binissa.paleblueassignment.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.binissa.paleblueassignment.data.local.database.ImageDatabase
import com.binissa.paleblueassignment.data.local.mappers.toDomain
import com.binissa.paleblueassignment.data.remote.api.PixabayApiService
import com.binissa.paleblueassignment.data.remote.paging.PixabayRemoteMediator
import com.binissa.paleblueassignment.domain.model.Image
import com.binissa.paleblueassignment.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val api: PixabayApiService,
    private val db: ImageDatabase
) : ImageRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getImagesPaginated(query: String): Flow<PagingData<Image>> {

        // Use query-specific paging source
        val pagingSourceFactory = { db.imageDao().getImagesPaged(query) }

        return Pager(
            config = PagingConfig(
                pageSize = 16,
                prefetchDistance = 4,
                enablePlaceholders = true,
                initialLoadSize = 20,
                maxSize = 200
            ),
            remoteMediator = PixabayRemoteMediator(query, api, db),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->

            pagingData.map {
                it.toDomain()
            }
        }
    }
}
