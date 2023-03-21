package com.example.commonModelUtil.extension

import com.example.commonModelUtil.ResultState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun <T> CoroutineScope.apiResultCoroutine(
    apiCall: suspend ()->T,
    result: (ResultState<T>) -> Unit
) {
    launch(
        CoroutineExceptionHandler { _, throwable ->
            result.invoke(ResultState.Error(throwable))
        }
    ) {
        result.invoke(ResultState.Success(apiCall.invoke()))
    }.invokeOnCompletion {
        if (it == null) {
            result.invoke(ResultState.Finish)
        }
    }
}

fun <T> ResultState<T>.onEach(
    loading: () -> Unit = {},
    success: (T) -> Unit = {},
    error: (Throwable?) -> Unit = {},
    finish: () -> Unit = {}
) {
    when(this) {
        is ResultState.Loading -> {
            loading.invoke()
        }
        is ResultState.Success<T> -> {
            success(this.data)
        }
        is ResultState.Error -> {
            error.invoke(this.error)
        }
        else -> {
            finish.invoke()
        }
    }
}