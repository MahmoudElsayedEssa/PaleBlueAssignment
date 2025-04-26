package com.binissa.paleblueassignment.domain.usecase

import androidx.paging.PagingData
import com.binissa.paleblueassignment.domain.repository.ImageRepository
import com.binissa.paleblueassignment.domain.model.Image
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagedImagesUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    operator fun invoke(query: String): Flow<PagingData<Image>> {
        return repository.getImagesPaginated(query)
    }
}