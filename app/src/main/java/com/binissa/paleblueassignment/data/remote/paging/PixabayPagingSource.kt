package com.binissa.paleblueassignment.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.binissa.paleblueassignment.data.remote.api.PixabayApiService
import com.binissa.paleblueassignment.data.remote.mappers.toDomain
import com.binissa.paleblueassignment.domain.model.Image
import com.binissa.paleblueassignment.domain.utils.NetworkErrorHandler
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class PixabayPagingSource @Inject constructor(
    private val api: PixabayApiService,
    private val query: String
) : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        // Start with page 1 if null
        val page = params.key ?: 1
        val perPage = params.loadSize

        return try {
            val response = api.getImages(
                query = query,
                page = page,
                perPage = perPage
            )

            val images = response.hits.toDomain()


            LoadResult.Page(
                data = images,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (images.isEmpty()) null else page + 1
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            val errorMessage = NetworkErrorHandler.handleNetworkException(e)
            LoadResult.Error(Exception(errorMessage))
        }
    }
    override  fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}