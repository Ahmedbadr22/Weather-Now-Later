package com.ab.core.utils.base

import com.ab.core.utils.error.Failure


sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Fail(val failure: Failure) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}

inline fun <T> Resource<T>.handle(
    crossinline onLoading: (Boolean) -> Unit,
    onSuccess: (T) -> Unit,
    onError: (Failure) -> Unit,
) {
    when (this) {
        is Resource.Fail -> {
            onLoading(false)
            onError(failure)
        }
        is Resource.Loading -> onLoading(true)
        is Resource.Success -> {
            onLoading(false)
            onSuccess(data)
        }
    }
}