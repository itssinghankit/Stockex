package com.example.sharesphere.util

typealias RootError = Error

sealed interface ApiResult<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : ApiResult<D, E>
    data class Error<out D, out E : RootError>(val error: E) : ApiResult<D, E>
}