package com.binissa.paleblueassignment.domain.utils

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object NetworkErrorHandler {
    fun handleNetworkException(exception: Throwable): String {
        return when (exception) {
            is UnknownHostException -> "No internet connection. Please check your network."
            is SocketTimeoutException -> "Connection timed out. Please try again."
            is IOException -> "Network error: ${exception.localizedMessage ?: "Unknown error"}"
            is HttpException -> {
                when (exception.code()) {
                    401 -> "Unauthorized: API key may be invalid"
                    429 -> "Too many requests: Rate limit exceeded"
                    in 500..599 -> "Server error (${exception.code()}): Please try again later"
                    else -> "HTTP Error: ${exception.code()}"
                }
            }
            else -> "Unexpected error: ${exception.localizedMessage ?: "Unknown error"}"
        }
    }
}