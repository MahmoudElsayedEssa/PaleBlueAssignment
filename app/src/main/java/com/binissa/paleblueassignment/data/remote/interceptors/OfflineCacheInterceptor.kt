package com.binissa.paleblueassignment.data.remote.interceptors

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission
import coil.intercept.Interceptor
import coil.request.CachePolicy
import coil.request.ImageResult

class OfflineCacheInterceptor(private val context: Context) : Interceptor {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun isOffline(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities == null || !capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    // Intercept the request and handle the cache strategy based on network connectivity
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val request = chain.request

        // Check if the device is offline
        val newRequest = if (isOffline()) {
            // If offline, prefer cache (both memory and disk cache)
            request.newBuilder().memoryCachePolicy(CachePolicy.READ_ONLY)
                .diskCachePolicy(CachePolicy.READ_ONLY).build()
        } else {
            // If online, proceed with regular memory and disk cache policies
            request.newBuilder().memoryCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.ENABLED).build()
        }

        return try {
            // Proceed with the request using the updated cache policy
            chain.proceed(newRequest)
        } catch (e: Exception) {
            // If the request fails and we are offline, try to serve from the cache
            if (isOffline()) {
                val lastResortRequest =
                    request.newBuilder().memoryCachePolicy(CachePolicy.READ_ONLY)
                        .diskCachePolicy(CachePolicy.READ_ONLY).build()
                try {
                    // Try to serve the image from cache (even if expired)
                    chain.proceed(lastResortRequest)
                } catch (innerException: Exception) {
                    // If still failing, propagate the original error
                    throw innerException
                }
            } else {
                // If an error occurs and we are not offline, propagate the error
                throw e
            }
        }
    }
}
