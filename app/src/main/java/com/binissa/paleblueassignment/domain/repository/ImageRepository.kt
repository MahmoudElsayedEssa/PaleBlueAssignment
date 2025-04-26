package com.binissa.paleblueassignment.domain.repository

import androidx.paging.PagingData
import com.binissa.paleblueassignment.domain.model.Image
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun getImagesPaginated(query: String): Flow<PagingData<Image>>

}