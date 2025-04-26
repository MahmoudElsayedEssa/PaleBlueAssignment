package com.binissa.paleblueassignment.domain.usecase

import com.binissa.paleblueassignment.domain.repository.ImageRepository
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val repository: ImageRepository
) {
//    suspend operator fun invoke(
//        query: String,
//        page: Int,
//        perPage: Int = 16
//    ): List<PixabayImage> {
//        return repository.getImages(query, page, perPage)
//    }
}
