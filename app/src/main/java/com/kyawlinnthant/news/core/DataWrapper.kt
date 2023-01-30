package com.kyawlinnthant.news.core

import retrofit2.Response

sealed class Result<out T>(
    open val data: T? = null
) {
    data class Error(val message: String) : Result<Nothing>()
    data class Success<out T>(override val data: T?) : Result<T>()
}

inline fun <reified T> safeApiCall(
    apiCall: () -> Response<T>
): Result<T> {
    try {
        val response = apiCall()
        // [200..300]
        if (response.isSuccessful) {
            val body = response.body() ?: return Result.Error(
                message = "EMPTY BODY",
            )
            return Result.Success(data = body)
        }
        //this will be [400..500]
        return Result.Error(
            message = response.message(),
        )
    } catch (e: Exception) {
        //this will be exception error.
        return Result.Error(
            message = e.localizedMessage ?: "Something's Wrong!",
        )
    }
}