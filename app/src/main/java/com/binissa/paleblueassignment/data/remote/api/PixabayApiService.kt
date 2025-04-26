package com.binissa.paleblueassignment.data.remote.api

import com.binissa.paleblueassignment.data.remote.response.PixabayResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApiService {
    @GET("api/")
    suspend fun getImages(
        @Query("q") query: String,
        @Query("image_type") imageType: String = "photo",
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): PixabayResponseDto
}

