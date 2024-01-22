package com.example.myhome.data.models

sealed class ApiResult<T>(val data: T? = null, val error: String? = null) {
    class Success<T>(result: T) : ApiResult<T>(data = result)
    class Error<T>(error: String) : ApiResult<T>(error = error)
    class Loading<T> : ApiResult<T>()
}
