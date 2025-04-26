package com.binissa.paleblueassignment.data.remote.interceptors

import com.binissa.paleblueassignment.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val apiKey = BuildConfig.PIXABAY_API_KEY

        val newRequest = originalRequest.newBuilder()
            .url(originalRequest.url.newBuilder()
                .addQueryParameter("key", apiKey)
                .build())
            .build()

        return chain.proceed(newRequest)
    }
}