package com.binissa.paleblueassignment.data.remote.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.binissa.paleblueassignment.data.local.database.ImageDatabase
import com.binissa.paleblueassignment.data.local.entities.ImageEntity
import com.binissa.paleblueassignment.data.remote.api.PixabayApiService
import com.binissa.paleblueassignment.domain.utils.NetworkErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class PixabayRemoteMediator(
    private val query: String,
    private val api: PixabayApiService,
    private val database: ImageDatabase
) : RemoteMediator<Int, ImageEntity>() {

    private val imageDao = database.imageDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {

                    val itemsLoaded = state.pages.sumOf { it.data.size }
                    val currentPage = (itemsLoaded / state.config.pageSize) + 1

                    if (itemsLoaded % state.config.pageSize == 0) {
                        currentPage
                    } else {
                        return MediatorResult.Success(endOfPaginationReached = false)
                    }
                }
            }

            val response = api.getImages(query = query, page = page, perPage = state.config.pageSize)

            val imageEntities = response.hits.map {
                ImageEntity(
                    id = it.id,
                    user = it.user,
                    imageUrl = it.imageUrl,
                    searchQuery = query,
                    fullHdUrl = it.fullHdUrl,
                    width = it.width,
                    height = it.height,
                    likes = it.likes,
                    comments = it.comments,
                    downloads = it.downloads,
                    views = it.views,
                )
            }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    imageDao.deleteImagesByQuery(query)
                }
                imageDao.insertImages(imageEntities)
            }

            MediatorResult.Success(endOfPaginationReached = imageEntities.isEmpty())
        } catch (e: Exception) {
            val errorMessage = NetworkErrorHandler.handleNetworkException(e)
            MediatorResult.Error(Exception(errorMessage))
        }
    }
}
